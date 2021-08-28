package com.tsayun.web_crawler.traversableTree

interface TraversableTree<T> {
    interface Node<T>{
        val value: T
        val children: List<Node<T>>
    }

    fun traversed(order: TraverseOrder) : Iterable<T>;

}

enum class TraverseOrder{
    //todo: doc TraverseOrder items

    //current, left, right
    NLR,

    //left, right, current
    LRN,

    //left, current, right
    LNR,

    //right, current, left
    RNL
}