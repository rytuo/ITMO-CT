/**
 * Возвращает новый emitter
 * @returns {Object}
 */
function getEmitter() {
    const eventsMap = {
        val: [], // values
        next: {}, // event -> [{f: bind handler, context, frequency, times}]
    };

    function getEventList(event) { // creates new levels if needed
        let level = eventsMap;
        for (const newLevel of event.split('.')) {
            if (!(newLevel in level.next)) {
                level.next[newLevel] = {
                    val: [],
                    next: {},
                }
            }
            level = level.next[newLevel];
        }
        return level.val;
    }

    return {

        /**
         * Подписаться на событие
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         */
        on: function (event, context, handler) {
            if (!event) {
                return this;
            }
            getEventList(event).push([context, {
                f: handler.bind(context),
            }]);

            return this;
        },

        /**
         * Отписаться от события
         * @param {String} event
         * @param {Object} context
         */
        off: function (event, context) {
            if (!event) {
                return;
            }
            let current = eventsMap;
            for (const newLevel of event.split('.')) {
                if (!(newLevel in current.next)) {
                    return this;
                }
                current = current.next[newLevel];
            }

            const remove = cur => {
                cur.val = cur.val.filter(val => val[0] !== context);
                for (const level in cur.next) {
                    remove(cur.next[level]);
                }
            }

            remove(current);

            return this;
        },

        /**
         * Уведомить о событии
         * @param {String} event
         */
        emit: function (event) {
            let current = eventsMap;
            const levels = event.split('.');
            const emitLevel = (curLevel, i) => {
                if (i < levels.length && levels[i] in curLevel.next) {
                    emitLevel(curLevel.next[levels[i]], i + 1);
                }

                const updated = [];
                curLevel.val.forEach(val => {
                    const func = val[1];
                    let update = true;

                    if (func.frequency) {
                        func.times--;
                        if (!func.times) {
                            func.f();
                            func.times = func.frequency;
                        }
                    } else {
                        func.f();
                        if (func.times) {
                            func.times--;
                            if (!func.times) {
                                update = false;
                            }
                        }
                    }

                    if (update) {
                        updated.push(val);
                    }
                });

                curLevel.val = updated;
            };

            emitLevel(current, 0);

            return this;
        },

        /**
         * Подписаться на событие с ограничением по количеству полученных уведомлений
         * @star
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         * @param {Number} times – сколько раз получить уведомление
         */
        several: function (event, context, handler, times) {
            if (!event) {
                return this;
            }
            if (times < 1) {
                return this.on(event, context, handler);
            }

            getEventList(event).push([context, {
                f: handler.bind(context),
                times,
            }]);

            return this;
        },

        /**
         * Подписаться на событие с ограничением по частоте получения уведомлений
         * @star
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         * @param {Number} frequency – как часто уведомлять
         */
        through: function (event, context, handler, frequency) {
            if (!event) {
                return this;
            }
            if (frequency < 2) {
                return this.on(event, context, handler);
            }

            getEventList(event).push([context, {
                f: handler.bind(context),
                frequency,
                times: 1,
            }]);

            return this;
        }
    };
}

module.exports = {
    getEmitter
};