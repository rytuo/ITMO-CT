/**
 * В теле класса решения разрешено использовать только переменные делегированные в класс RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 *
 * @author : Попов Александр
 */
class Solution : MonotonicClock {
    private var c1 by RegularInt(0)
    private var c2 by RegularInt(0)

    private var v1 by RegularInt(0)
    private var v2 by RegularInt(0)
    private var v3 by RegularInt(0)

    override fun write(time: Time) {
        // write left-to-right
        v1 = time.d1
        v2 = time.d2
        v3 = time.d3

        // write right-to-left
        c2 = time.d2
        c1 = time.d1
    }

    override fun read(): Time {
        // read left-to-right
        val a1 = c1
        val a2 = c2

        // read right-to-left
        val b3 = v3
        val b2 = v2
        val b1 = v1

        if (a1 != b1) {
            return Time(b1, 0, 0)
        }

        if (a2 != b2) {
            return Time(b1, b2, 0)
        }

        return Time(b1, b2, b3)
    }
}