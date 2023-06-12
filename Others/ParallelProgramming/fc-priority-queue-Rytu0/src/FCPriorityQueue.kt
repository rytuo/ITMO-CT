import java.util.*
import kotlinx.atomicfu.*

class FCPriorityQueue<E : Comparable<E>> {
    private val q = PriorityQueue<E>()

    private val locked = atomic(false)
    private fun tryLock() = locked.compareAndSet(expect = false, update = true)
    private fun unlock() { locked.getAndSet(false) }

    private val size = 10
    private val index = atomic(0)
    private val fcArray = atomicArrayOfNulls<func<E>>(size)

    /**
     * Retrieves the element with the highest priority
     * and returns it as the result of this function;
     * returns `null` if the queue is empty.
     */
    fun poll(): E? {
        return combine(q::poll)
    }

    /**
     * Returns the element with the highest priority
     * or `null` if the queue is empty.
     */
    fun peek(): E? {
        return combine(q::peek)
    }

    /**
     * Adds the specified element to the queue.
     */
    fun add(element: E) {
        val op = fun(): E? {
            q.add(element)
            return null
        }
        combine(op)
    }

    private fun combine(op: func<E>): E? {
        var done = false
        var res: E? = null

        val f = fun(): E? {
            res = op.invoke()
            done = true
            return res
        }

        var ind: Int
        while (true) {
            ind = index.getAndIncrement()
            if (fcArray[ind].compareAndSet(null, f))
                break
        }

        while (true) {
            if (tryLock()) {
                if (!done) {
                    res = fcArray[ind].getAndSet(null)?.invoke()
                }

                for (i in 0 until size) {
                    fcArray[i].getAndSet(null)?.invoke()
                }

                index.getAndSet(0)

                unlock()
                return res
            }

            if (done) {
                return res
            }
        }
    }
}

typealias func<E> = () -> E?