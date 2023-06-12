'use strict';

function isString(a) {
    return typeof a === 'string';
}

/**
 * Складывает два целых числа
 * @param {Number} a Первое целое
 * @param {Number} b Второе целое
 * @throws {TypeError} Когда в аргументы переданы не числа
 * @returns {Number} Сумма аргументов
 */
function abProblem(a, b) {
    if (!Number.isInteger(a) || !Number.isInteger(b)) {
        throw TypeError();
    }
    return a + b;
}

/**
 * Определяет век по году
 * @param {Number} year Год, целое положительное число
 * @throws {TypeError} Когда в качестве года передано не число
 * @throws {RangeError} Когда год – отрицательное значение
 * @returns {Number} Век, полученный из года
 */
function centuryByYearProblem(year) {
    if (!Number.isInteger(year)) {
        throw TypeError();
    }
    if (year < 0) {
        throw RangeError();
    }
    return Math.floor((year + 99) / 100);
}

/**
 * Переводит цвет из формата HEX в формат RGB
 * @param {String} hexColor Цвет в формате HEX, например, '#FFFFFF'
 * @throws {TypeError} Когда цвет передан не строкой
 * @throws {RangeError} Когда значения цвета выходят за пределы допустимых
 * @returns {String} Цвет в формате RGB, например, '(255, 255, 255)'
 */
function colorsProblem(hexColor) {
    if (!isString(hexColor)) {
        throw TypeError();
    }
    if (hexColor.match(/^#[\dabcdef]{6}$/i) === null) {
            throw RangeError();
    }
    const fst = parseInt(hexColor.slice(1, 3), 16);
    const snd = parseInt(hexColor.slice(3, 5), 16);
    const thd = parseInt(hexColor.slice(5, 7), 16);
    return `(${fst}, ${snd}, ${thd})`;
}

/**
 * Находит n-ое число Фибоначчи
 * @param {Number} n Положение числа в ряде Фибоначчи
 * @throws {TypeError} Когда в качестве положения в ряде передано не число
 * @throws {RangeError} Когда положение в ряде не является целым положительным числом
 * @returns {Number} Число Фибоначчи, находящееся на n-ой позиции
 */
function fibonacciProblem(n) {
    if (!Number.isInteger(n)) {
        throw TypeError();
    }
    if (n <= 0) {
        throw RangeError();
    }
    const fib = [1, 1];
    const getFib = a => {
        if (fib.length < a) {
            getFib(a - 1);
            fib.push(fib[a - 2] + fib[a - 3]);
        }
        return fib[a - 1];
    };
    return getFib(n);
}

/**
 * Транспонирует матрицу
 * @param {(Any[])[]} matrix Матрица размерности MxN
 * @throws {TypeError} Когда в функцию передаётся не двумерный массив
 * @returns {(Any[])[]} Транспонированная матрица размера NxM
 */
function matrixProblem(matrix) {
    if (!Array.isArray(matrix) || !Array.isArray(matrix[0])) {
        throw TypeError();
    }
    const newM = Array(matrix[0].length);
    for (let i = 0; i < newM.length; i++) {
        newM[i] = Array(matrix.length);
    }
    for (let i = 0; i < newM.length; i++) {
        for (let j = 0; j < newM[0].length; j++) {
            newM[i][j] = matrix[j][i];
        }
    }
    return newM;
}

/**
 * Переводит число в другую систему счисления
 * @param {Number} n Число для перевода в другую систему счисления
 * @param {Number} targetNs Система счисления, в которую нужно перевести (Число от 2 до 36)
 * @throws {TypeError} Когда переданы аргументы некорректного типа
 * @throws {RangeError} Когда система счисления выходит за пределы значений [2, 36]
 * @returns {String} Число n в системе счисления targetNs
 */
function numberSystemProblem(n, targetNs) {
    if (typeof n !== 'number' || !Number.isInteger(targetNs)) {
        throw TypeError();
    }
    if (targetNs < 2 || targetNs > 36) {
        throw RangeError();
    }
    return n.toString(targetNs);
}

/**
 * Проверяет соответствие телефонного номера формату
 * @param {String} phoneNumber Номер телефона в формате '8–800–xxx–xx–xx'
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Boolean} Если соответствует формату, то true, а иначе false
 */
function phoneProblem(phoneNumber) {
    if (!isString(phoneNumber)) {
        throw TypeError();
    }
    return phoneNumber.match(/^8-800-\d{3}-\d{2}-\d{2}$/) !== null;
}

/**
 * Определяет количество улыбающихся смайликов в строке
 * @param {String} text Строка в которой производится поиск
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Number} Количество улыбающихся смайликов в строке
 */
function smilesProblem(text) {
    if (!isString(text)) {
        throw TypeError();
    }
    return (text.match(/\(-:|:-\)/g) || []).length;
}

/**
 * Определяет победителя в игре "Крестики-нолики"
 * Тестами гарантируются корректные аргументы.
 * @param {(('x' | 'o')[])[]} field Игровое поле 3x3 завершённой игры
 * @returns {'x' | 'o' | 'draw'} Результат игры
 */
function ticTacToeProblem(field) {
    let cntF, cntS, winFst, winSnd;
    for (let i = 0; i < 3; i++) {
        cntF = 0;
        cntS = 0;
        for (let j = 0; j < 3; j++) {
            if (field[i][j] === 'x') {
                cntF++;
            }
            if (field[i][j] === 'o') {
                cntS++;
            }
        }
        winFst = winFst || (cntF === 3);
        winSnd = winSnd || (cntS === 3);

        cntF = 0;
        cntS = 0;
        for (let j = 0; j < 3; j++) {
            if (field[j][i] === 'x') {
                cntF++;
            }
            if (field[j][i] === 'o') {
                cntS++;
            }
        }
        winFst = winFst || (cntF === 3);
        winSnd = winSnd || (cntS === 3);
    }

    cntF = 0;
    cntS = 0;
    for (let i = 0; i < 3; i++) {
        if (field[i][i] === 'x') {
            cntF++;
        }
        if (field[i][i] === 'o') {
            cntS++;
        }
    }
    winFst = winFst || (cntF === 3);
    winSnd = winSnd || (cntS === 3);

    if (winFst === winSnd) {
        return 'draw';
    } else {
        if (winFst) {
            return 'x';
        } else {
            return 'o'
        }
    }
}

module.exports = {
    abProblem,
    centuryByYearProblem,
    colorsProblem,
    fibonacciProblem,
    matrixProblem,
    numberSystemProblem,
    phoneProblem,
    smilesProblem,
    ticTacToeProblem
};