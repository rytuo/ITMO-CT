'use strict';

/**
 * Храним значения
 * string -> {
 *     phone -> string[],
 *     mail -> string[],
 * }
 */
const phoneBook = new Map();
let clientList = [];

/**
 * Вызывайте эту функцию, если есть синтаксическая ошибка в запросе
 * @param {number} lineNumber – номер строки с ошибкой
 * @param {number} charNumber – номер символа, с которого запрос стал ошибочным
 */
function syntaxError(lineNumber, charNumber) {
    throw new Error(`SyntaxError: Unexpected token at ${lineNumber}:${charNumber}`);
}

const addContact = name => {
    if (!phoneBook.has(name)) {
        phoneBook.set(name, {
            phones: [],
            emails: [],
        });
        clientList.push(name);
    }
};

const removeContact = name => {
    if (phoneBook.has(name)) {
        phoneBook.delete(name);
        clientList.splice(clientList.indexOf(name), 1);
    }
};

const addQuery = (name, phones, emails) => {
    const data = phoneBook.get(name);
    if (data) {
        phones.forEach(phone => {
            if (!data.phones.includes(phone)) {
                data.phones.push(phone);
            }
        });
        emails.forEach(email => {
            if (!data.emails.includes(email)) {
                data.emails.push(email);
            }
        });
    }
};

const removeQuery = (name, phones, emails) => {
    const data = phoneBook.get(name);
    if (data) {
        data.phones = data.phones.filter(phone => !phones.includes(phone));
        data.emails = data.emails.filter(email => !emails.includes(email));
    }
};

const findQuery = query => {
    if (!query) {
        return [];
    }
    const includes = s => s.includes(query);
    return clientList.filter(name => {
        const info = phoneBook.get(name);
        return includes(name) || info.emails.some(includes) || info.phones.some(includes);
    });
}

const removeContacts = query => {
    findQuery(query).forEach(removeContact);
};

const show = (fields, query) => {
    return findQuery(query).map(name => {
        const info = phoneBook.get(name);
        return fields.map(field => {
            switch (field) {
                case 'имя':
                    return name;
                case 'почты':
                    return info.emails.join(',');
                case 'телефоны':
                    return info.phones.map(phone => {
                        const match = /(\d\d\d)(\d\d\d)(\d\d)(\d\d)/.exec(phone);
                        return `+7 (${match[1]}) ${match[2]}-${match[3]}-${match[4]}`;
                    }).join(',');
            }
        }).join(';');
    });
};

/**
 * Выполнение запроса на языке pbQL
 * @param {string} query
 * @returns {string[]} - строки с результатами запроса
 */
function run(query) {
    phoneBook.clear();
    clientList = [];
    const commands = query.split(';');
    const res = [];

    commands.forEach((command, ind) => {
        if (!command && ind === commands.length - 1) {
            return;
        }
        const words = command.split(' ');
        let i;
        const getPos = () => words.slice(0, i).join(' ').length + 2; // word[i] start
        const getWord = (i) => {
            if (words.length <= i) {
                syntaxError(ind + 1, getPos() - 1);
            }
            return words[i];
        }
        const parseQuery = callback => {
            const phones = [], emails = [];
            while (getWord(i).match(/^(телефон|почту)$/)) {
                if (words[i++] === 'телефон') {
                    if (!getWord(i).match(/^\d{10}$/)) {
                        syntaxError(ind + 1, getPos());
                    }
                    phones.push(words[i++]);
                } else {
                    emails.push(getWord(i++));
                }
                if (getWord(i) === 'и') {
                    i++;
                }
            }

            if (getWord(i) !== 'для') {
                syntaxError(ind + 1, getPos());
            }
            if (getWord(++i) !== 'контакта') {
                syntaxError(ind + 1, getPos());
            }

            getWord(++i);
            return callback(words.slice(i).join(' '), phones, emails);
        }
        i = 1;
        switch (words[0]) {
            case 'Создай':
                if (getWord(i) === 'контакт') {
                    getWord(++i);
                    addContact(words.slice(2).join(' '));
                } else {
                    syntaxError(ind + 1, getPos());
                }
                break;
            case 'Удали':
                if (getWord(i) === 'контакт') {
                    getWord(++i);
                    removeContact(words.slice(2).join(' '));
                } else if (words[i] === 'контакты,') {
                    if (getWord(++i) !== 'где') {
                        syntaxError(ind + 1, getPos());
                    }
                    if (getWord(++i) !== 'есть') {
                        syntaxError(ind + 1, getPos());
                    }
                    getWord(++i);
                    removeContacts(words.slice(i).join(' '));
                } else {
                    parseQuery(removeQuery);
                }
                break;
            case 'Добавь':
                parseQuery(addQuery);
                break;
            case 'Покажи':
                const fields = [];

                while (getWord(i).match(/^(имя|телефоны|почты)$/)) {
                    fields.push(words[i++]);
                    if (getWord(i) === 'и') {
                        i++;
                    }
                }

                if (getWord(i) !== 'для') {
                    syntaxError(ind + 1, getPos());
                }
                if (getWord(++i) !== 'контактов,') {
                    syntaxError(ind + 1, getPos());
                }
                if (getWord(++i) !== 'где') {
                    syntaxError(ind + 1, getPos());
                }
                if (getWord(++i) !== 'есть') {
                    syntaxError(ind + 1, getPos());
                }

                getWord(++i);
                res.push(...show(fields, words.slice(i).join(' ')));
                break;
            default:
                syntaxError(ind + 1, 1);
        }
        if (ind === commands.length - 1) {
            syntaxError(ind + 1, command.length + 1);
        }
    });
    return res;
}

module.exports = { phoneBook, run };