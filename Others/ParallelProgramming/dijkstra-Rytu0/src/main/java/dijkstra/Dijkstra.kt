package dijkstra

import kotlinx.atomicfu.atomic
import java.util.*
import java.util.concurrent.Phaser
import java.util.concurrent.locks.ReentrantLock
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.random.Random

private val NODE_DISTANCE_COMPARATOR = Comparator<Node> { o1, o2 -> Integer.compare(o1!!.distance, o2!!.distance) }

private val active = atomic(0)

// Returns `Integer.MAX_VALUE` if a path has not been found.
fun shortestPathParallel(start: Node) {
    val workers = Runtime.getRuntime().availableProcessors()
    // The distance to the start node is `0`
    start.distance = 0
    // Create a priority (by distance) queue and add the start node into it
    val q = MultiQueue(workers, NODE_DISTANCE_COMPARATOR)
    q.add(start)
    active.getAndIncrement()
    // Run worker threads and wait until the total work is done
    val onFinish = Phaser(workers + 1) // `arrive()` should be invoked at the end by each worker
    repeat(workers) {
        thread {
            while (active.value > 0) {
                val cur: Node = q.pop() ?: continue
                val curDist = cur.distance
                for (e in cur.outgoingEdges) {
                    while (true) {
                        val toDist = e.to.distance
                        if (toDist > curDist + e.weight) {
                            if (!e.to.casDistance(toDist, curDist + e.weight)) {
                                continue
                            }
                            q.add(e.to)
                            active.getAndIncrement()
                        }
                        break
                    }
                }
                active.getAndDecrement()
            }
            onFinish.arrive()
        }
    }
    onFinish.arriveAndAwaitAdvance()
}

class MultiQueue(private val n: Int, private val comp: Comparator<Node>) {
    private val q = ArrayList<PriorityQueue<Node>>()
    private val locks = ArrayList<ReentrantLock>()
    private val rand = Random(0)

    init {
        repeat(2 * n) {
            q.add(PriorityQueue(comp))
            locks.add(ReentrantLock())
        }
    }

    fun add(v: Node) {
        val i = findLock()
        q[i].add(v)
        locks[i].unlock()
    }

    fun pop() : Node? {
        val i = findLock()
        val j = findLock()

        fun getMin(i: Int, j: Int): Node? {
            val v1 = q[i].peek()
            val v2 = q[j].peek()

            if (v1 == null) {
                return q[j].poll()
            }
            if (v2 == null) {
                return q[i].poll()
            }
            return if (comp.compare(v1, v2) > 0) {
                q[i].poll()
            } else {
                q[j].poll()
            }
        }

        val v : Node? = getMin(i, j)

        locks[i].unlock()
        locks[j].unlock()

        return v
    }

    private fun findLock() : Int {
        var i = rand.nextInt(2 * n)

        while (!locks[i].tryLock()) {
            i = rand.nextInt(2 * n)
        }

        return i
    }
}