'use strict';

/**
 * Итератор по друзьям
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 */
function Iterator(friends, filter) {
    this._friends = this._getFriends(friends, filter);
    this._ind = 0;
}
Iterator.prototype.next = function () {
    if (this.done()) {
        return null;
    }
    return this._friends[this._ind++];
}
Iterator.prototype.done = function () {
    return this._ind === this._friends.length;
}
Iterator.prototype._getFriends = function (friends, filter, maxLevel) {
    const friendsMap = new Map(friends.map(fr => [fr.name, fr]));
    const res = [];
    const used = new Set();

    // all level
    let level = Array.from(new Set(friends.filter(fr => fr.best)));

    while (level.length > 0 && (maxLevel === undefined || maxLevel-- > 0)) {
        // add sorted level to res
        const processedLevel = level.sort((a, b) => a.name.localeCompare(b.name));
        res.push(...processedLevel);

        // add level to used because it should not repeat anymore
        level.forEach(el => used.add(el));

        // generate new level - not used friends of current
        const newLevel = new Set();
        level.forEach(fr =>
            fr.friends.forEach(name => {
                const newFriend = friendsMap.get(name);
                if (newFriend && !used.has(newFriend)) {
                    newLevel.add(newFriend);
                }
            }));
        level = Array.from(newLevel);
    }

    return filter.filter(res);
}

/**
 * Итератор по друзям с ограничением по кругу
 * @extends Iterator
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 * @param {Number} maxLevel – максимальный круг друзей
 */
function LimitedIterator(friends, filter, maxLevel) {
    this._friends = this._getFriends(friends, filter, maxLevel);
    this._ind = 0;
}
LimitedIterator.prototype = Object.create(Iterator.prototype);
LimitedIterator.prototype.constructor = LimitedIterator;

/**
 * Фильтр друзей
 * @constructor
 */
function Filter() {
    this.func = () => true;
}
Filter.prototype.filter = function (arr) {
    return arr.filter(this.func);
};

/**
 * Фильтр друзей
 * @extends Filter
 * @constructor
 */
function MaleFilter() {
    this.func = fr => fr.gender === 'male';
}
MaleFilter.prototype = Object.create(Filter.prototype);
MaleFilter.prototype.constructor = MaleFilter;

/**
 * Фильтр друзей-девушек
 * @extends Filter
 * @constructor
 */
function FemaleFilter() {
    this.func = fr => fr.gender === 'female';
}
FemaleFilter.prototype = Object.create(Filter.prototype);
FemaleFilter.prototype.constructor = FemaleFilter;

exports.Iterator = Iterator;
exports.LimitedIterator = LimitedIterator;

exports.Filter = Filter;
exports.MaleFilter = MaleFilter;
exports.FemaleFilter = FemaleFilter;