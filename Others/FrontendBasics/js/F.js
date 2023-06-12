'use strict';

const fetch = require('node-fetch');

const API_KEY = require('./key.json');

/**
 * @typedef {object} TripItem Город, который является частью маршрута.
 * @property {number} geoid Идентификатор города
 * @property {number} day Порядковое число дня маршрута
 */

class TripBuilder {
  constructor(geoids) {
    this.geoids = geoids;
    this.maxDays = null;
    this.plan = [];
  }

  /**
   * Метод, добавляющий условие наличия в маршруте
   * указанного количества солнечных дней
   * Согласно API Яндекс.Погоды, к солнечным дням
   * можно приравнять следующие значения `condition`:
   * * `clear`;
   * * `partly-cloudy`.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  sunny(daysCount) {
    this.plan = this.plan.concat(Array(daysCount).fill('sunny'));
    return this;
  }

  /**
   * Метод, добавляющий условие наличия в маршруте
   * указанного количества пасмурных дней
   * Согласно API Яндекс.Погоды, к солнечным дням
   * можно приравнять следующие значения `condition`:
   * * `cloudy`;
   * * `overcast`.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  cloudy(daysCount) {
    this.plan = this.plan.concat(Array(daysCount).fill('cloudy'));
    return this;
  }

  /**
   * Метод, добавляющий условие максимального количества дней.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  max(daysCount) {
    this.maxDays = daysCount;
    return this;
  }

  /**
   * Метод, возвращающий Promise с планируемым маршрутом.
   * @returns {Promise<TripItem[]>} Список городов маршрута
   */
  async build() {
    // host
    const apiHost = 'https://api.weather.yandex.ru';

    // get city weather forecast array duration long
    const getWeather = (geoid) => {
      return fetch(
        `${apiHost}/v2/forecast?geoid=${geoid}&hours=false&limit=7`,
        {
          headers: {
            'X-Yandex-API-Key': API_KEY.key,
          }
        })
        .then(response => response.json())
        .then(data => data['forecasts'].map(day => day['parts']['day_short']['condition']));
    };

    // duration of trip
    let duration = this.plan.length;
    // simple output
    if (!duration) {
      return [];
    }

    // geoid -> weathers array for all trip
    const weathers = new Map();
    for (const geoid of this.geoids) {
      weathers.set(geoid, await getWeather(geoid, duration));
    }

    // if api weather matches plan weather
    const dayMatches = (planWeather, weather) => {
      if (planWeather === 'sunny') {
        return weather === 'clear' || weather === 'partly-cloudy';
      } else {
        return weather === 'cloudy' || weather === 'overcast';
      }
    }

    // dayNum geoid -> if weather matches
    const matchMatrix = this.plan.map((planWeather, dayNum) =>
      new Map(this.geoids.map(geoid =>
        [geoid, dayMatches(planWeather, weathers.get(geoid)[dayNum])]
      ))
    );

    // get first valid path from current day
    const getPath = (cur, daysRow, geoid, availableIds) => {
      if (cur >= duration) {
        return [];
      }

      // stay
      if (daysRow < this.maxDays && matchMatrix[cur].get(geoid)) {
        const path = getPath(cur + 1, daysRow + 1, geoid, availableIds);
        if (path) {
          path.unshift(geoid);
          return path;
        }
      }

      // change city
      for (let i = 0; i < availableIds.length; i++) {
        const id = availableIds[i];
        if (matchMatrix[cur].get(id)) {
          availableIds.splice(i, 1)
          const path = getPath(cur + 1, 1, id, availableIds);
          if (path) {
            path.unshift(id);
            return path;
          }
          availableIds.push(id);
        }
      }

      return null;
    };

    const res = getPath(0, this.maxDays, -1, this.geoids);

    if (!res) {
      throw new Error('Не могу построить маршрут!');
    }

    // convert output
    return res.map((geoid, day) => {
      return {
        'day': day + 1,
        geoid,
      }
    });
  }
}

/**
 * Фабрика для получения планировщика маршрута.
 * Принимает на вход список идентификаторов городов, а
 * возвращает планировщик маршрута по данным городам.
 *
 * @param {number[]} geoIds Список идентификаторов городов
 * @returns {TripBuilder} Объект планировщика маршрута
 * @see https://yandex.ru/dev/xml/doc/dg/reference/regions-docpage/
 */
function planTrip(geoIds) {
  return new TripBuilder(geoIds);
}

module.exports = {
  planTrip
};

