package com.tsayun.web_crawler.traversableTree.impl

import com.tsayun.web_crawler.traversableTree.TraversableTree
import com.tsayun.web_crawler.traversableTree.TraverseOrder
import java.util.*
import kotlin.NoSuchElementException

typealias ChildrenProvider<T> = (T) -> List<T>
typealias Node<T> = LazyTraversableTree<T>.LazyTraversableTreeNode

class LazyTraversableTree<T>(rootValue: T, val childrenProvider: ChildrenProvider<T>) : TraversableTree<T> {

    inner class LazyTraversableTreeNode(
        override val value: T,
        val parent: Node<T>?,
        private val siblingsIndex: Int
    ) : TraversableTree.Node<T> {


        private lateinit var _children: List<Node<T>>

        override val children: List<Node<T>>
            get() {
                if (!::_children.isInitialized) {
                    val providedChildrenValues = childrenProvider.invoke(value)
                    _children = List(providedChildrenValues.size) { i ->
                        Node(providedChildrenValues[i], this, i)
                    }
                }
                return _children
            }

        fun getLeftSibling(): Node<T>? = getSiblingByOffset(-1)

        fun getRightSibling(): Node<T>? = getSiblingByOffset(1)

        fun getSiblingByOffset(offset: Int): Node<T>? {
            return parent?.children?.elementAtOrNull(siblingsIndex + offset)
        }
    }

    private var root: Node<T> = Node(rootValue, null, 0)

    override fun traversed(order: TraverseOrder): Iterable<T> {
        return object : Iterable<T> {
            override fun iterator(): Iterator<T> {
                return when (order) {
                    TraverseOrder.NLR -> getNLRIterator()
                    TraverseOrder.NRL -> getNRLIterator()
                    TraverseOrder.NLRBreadth -> getNLRBreadthIterator()
                    TraverseOrder.NRLBreadth -> getNRLBreadthIterator()
                }

            }

            private fun getNRLBreadthIterator() = object : AbstractIterator<T>() {

                private val computeQueue: Queue<Node<T>> = LinkedList()

                init {
                    computeQueue.add(root)
                }

                override fun computeNext() {
                    try {
                        val next = computeQueue.remove()
                        setNext(next.value)
                        val children = next.children
                        if (children.isNotEmpty()) {
                            //todo: restrict order
                            computeQueue.addAll(children.reversed())
                        }
                    } catch (e: NoSuchElementException) {
                        done()
                    }
                }
            }

            private fun getNLRBreadthIterator() = object : AbstractIterator<T>() {

                private val computeQueue: Queue<Node<T>> = LinkedList()

                init {
                    computeQueue.add(root)
                }

                override fun computeNext() {
                    try {
                        val next = computeQueue.remove()
                        setNext(next.value)
                        val children = next.children
                        if (children.isNotEmpty()) {
                            //todo: restrict order
                            computeQueue.addAll(children)
                        }
                    } catch (e: NoSuchElementException) {
                        done()
                    }
                }
            }

            private fun getNRLIterator() = object : AbstractIterator<T>() {

                private var current: Node<T>? = null

                override fun computeNext() {
                    val next = getNextNodeByNRL(current)
                    if (next != null) {
                        setNext(next.value)
                    } else {
                        done()
                    }
                    current = next
                }
            }

            private fun getNLRIterator() = object : AbstractIterator<T>() {

                private var current: Node<T>? = null

                override fun computeNext() {
                    val next = getNextNodeByNLR(current)
                    if (next != null) {
                        setNext(next.value)
                    } else {
                        done()
                    }
                    current = next
                }
            }

            private fun getNextNodeByNRL(current: Node<T>?): Node<T>? {
                return if (current == null) {
                    root
                } else {
                    val children = current.children
                    return if (children.isNotEmpty()) {
                        children.last()
                    } else {
                        getClosestNodeTowardsRoot(current) { it?.getLeftSibling() }
                    }
                }
            }

            private fun getNextNodeByNLR(current: Node<T>?): Node<T>? {
                return if (current == null) {
                    root
                } else {
                    val children = current.children
                    return if (children.isNotEmpty()) {
                        children.first()
                    } else {
                        getClosestNodeTowardsRoot(current) { it?.getRightSibling() }
                    }
                }
            }

            /**
             * searches the next node in direction of getSibling function while iterating towards root
             */
            private fun getClosestNodeTowardsRoot(current: Node<T>, getSibling: (Node<T>?) -> Node<T>?): Node<T>? {
                return getSibling(current) ?: run {
                    var parent = current.parent
                    var nodeToLeft = getSibling(parent)
                    while ((parent != null) && (nodeToLeft == null)) {
                        parent = parent.parent
                        nodeToLeft = getSibling(parent)
                    }
                    return nodeToLeft
                }
            }

        }
    }
}