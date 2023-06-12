'use strict';

/**
 * @param {Object} schedule Расписание Банды
 * @param {number} duration Время на ограбление в минутах
 * @param {Object} workingHours Время работы банка
 * @param {string} workingHours.from Время открытия, например, "10:00+5"
 * @param {string} workingHours.to Время закрытия, например, "18:00+5"
 * @returns {Object}
 */
function getAppropriateMoment(schedule, duration, workingHours) {
    const dayTime = 1440;
    const days = ['ПН', 'ВТ', 'СР', 'ЧТ', 'ПТ', 'СБ', 'ВС'];

    // Возвращает количество минут относительно "ПН 00:00+0"
    function convertTime(time) {
        let n = 0;
        const day = time.substr(0, 2);
        for (let i = 0; i < 7; i++) {
            if (day === days[i]) {
                n += i * dayTime;
                break;
            }
        }

        n += parseInt(time.substr(3, 5)) * 60;
        n += parseInt(time.substr(6, 8));
        n -= parseInt(time.substr(9)) * 60;

        return n;
    }

    // Возвращает массив адекватных интервалов: [[[int, int]]]
    // Переводит все даты в числа
    function convertSchedule(schedule) {
        return Object.values(schedule).map(lst =>
            lst.map(interval =>
                [convertTime(interval.from), convertTime(interval.to)]
            )
        );
    }

    // можно начинать когда заканчивается интервал
    function validStart(time, schedule) {
        return schedule.every(person =>
            person.every(interval =>
                time < interval[0] || interval[1] <= time
            )
        );
    }

    // нет пересечений с интервалами
    function validWork(time, schedule) {
        return schedule.every(person =>
            person.every(interval =>
                time < interval[0] || interval[1] < time
            )
        );
    }

    let times = null;

    // Валидные начала ограблений
    function getTimes() {
        if (times != null) {
            return times;
        }

        times = [];
        const convertedSchedule = convertSchedule(schedule);

        for (let j = 0; j < 3; j++) {
            const day = days[j];
            const begin = convertTime(day + ' ' + workingHours.from);
            const end = convertTime(day + ' ' + workingHours.to);

            let row = 0, start = false;
            for (let i = begin; i <= end; i++) {
                if (start) {
                    row++;
                    if (row >= duration) {
                        times.push(i - duration);
                    }
                    if (!validWork(i, convertedSchedule)) {
                        start = false;
                    }
                }
                if (start === false && validStart(i, convertedSchedule)) {
                    row = 0;
                    start = true;
                }
            }
        }

        return times;
    }

    // Время: [dd, hh, mm]
    function timeFormat(time) {
        time += 60 * parseInt(workingHours.from.substr(6));
        const day = days[Math.floor(time / 1440)];
        time %= 1440;
        let hours = Math.floor(time / 60).toString();
        if (hours.length === 1) {
            hours = '0' + hours;
        }
        let minutes = (time % 60).toString();
        if (minutes.length === 1) {
            minutes = '0' + minutes;
        }
        return [day, hours, minutes];
    }

    let index = 0;

    return {
        /**
         * Найдено ли время
         * @returns {boolean}
         */
        exists() {
            return getTimes().length !== 0;
        },

        /**
         * Возвращает отформатированную строку с часами
         * для ограбления во временной зоне банка
         *
         * @param {string} template
         * @returns {string}
         *
         * @example
         * ```js
         * getAppropriateMoment(...).format('Начинаем в %HH:%MM (%DD)') // => Начинаем в 14:59 (СР)
         * ```
         */
        format(template) {
            if (!this.exists()) {
                return '';
            }

            const [d, h, m] = timeFormat(times[index]);
            return template.replace(/%DD/, d)
                .replace(/%HH/, h)
                .replace(/%MM/, m);
        },

        /**
         * Попробовать найти часы для ограбления позже [*]
         * @note Не забудь при реализации выставить флаг `isExtraTaskSolved`
         * @returns {boolean}
         */
        tryLater() {
            if (!this.exists()) {
                return false;
            }

            for (let newIndex = index + 1; newIndex < times.length; newIndex++) {
                if (times[newIndex] >= times[index] + 30) {
                    index = newIndex;
                    return true;
                }
            }
            return false;
        }
    };
}

const isExtraTaskSolved = true;

module.exports = {
    getAppropriateMoment,
    isExtraTaskSolved
};