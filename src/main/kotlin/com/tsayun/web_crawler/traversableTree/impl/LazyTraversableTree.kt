package com.tsayun.web_crawler.traversableTree.impl

import com.tsayun.web_crawler.traversableTree.TraversableTree
import com.tsayun.web_crawler.traversableTree.TraverseOrder
import java.lang.IllegalStateException

class LazyTraversableTree<T>(rootValue: T, val childrenProvider: (T) -> List<T>) : TraversableTree<T> {

    private class Node<T>(override val value: T, val parent: Node<T>?) :
        TraversableTree.Node<T> {

        private var childrenImpl: List<TraversableTree.Node<T>> = emptyList()

        val isChildrenInitialized: Boolean
            get() = isChildrenInitializedImpl
        private var isChildrenInitializedImpl = false

        override val children: List<TraversableTree.Node<T>>
            get() {
                if (isChildrenInitializedImpl){
                    return childrenImpl
                } else {
                    //todo: throw IllegalStateException on initializeChildren called on already initialized node
                    throw IllegalStateException("Lazy node is not yet initialized")
                }
            }

        fun initializeChildren(childrenProvider: (T) -> List<T>) {
            if (!isChildrenInitialized) {
                childrenImpl = childrenProvider.invoke(value).map { Node(it, this) }
                isChildrenInitializedImpl = true
            } else {
                //todo: throw IllegalStateException on initializeChildren called on already initialized node
            }
        }
    }

    private var current: Node<T> = Node(rootValue, null)

    override fun traversed(order: TraverseOrder): Iterable<T> {
        return object : Iterable<T> {
            override fun iterator(): Iterator<T> {
                return object : AbstractIterator<T>() {
                    override fun computeNext() {
                        if (!current.isChildrenInitialized){
                            current.initializeChildren(childrenProvider)
                        }
                        getNextNode(current, order)
                    }
                }
            }

        }
    }

    private fun getNextNode(node: Node<T>, order: TraverseOrder): Node<T> {

    }
}