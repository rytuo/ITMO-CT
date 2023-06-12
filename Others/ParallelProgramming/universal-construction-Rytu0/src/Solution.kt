/**
 * @author : Попов Александр
 */
class Solution : AtomicCounter {
    // объявите здесь нужные вам поля
    private val root: Node = Node(0)
    private val last = ThreadLocal.withInitial { root }

    override fun getAndAdd(x: Int): Int {
        var res: Int
        do {
            res = last.get().x
            val node = Node(res + x)
            last.set(last.get().next.decide(node))
        } while (last.get() != node)
        return res
    }

    // вам наверняка потребуется дополнительный класс
    private class Node(val x: Int) {
        val next = Consensus<Node>()
    }
}
