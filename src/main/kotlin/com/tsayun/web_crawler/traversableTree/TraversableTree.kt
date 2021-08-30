package com.tsayun.web_crawler.traversableTree

import java.util.*

interface TraversableTree<T> {
    interface Node<T>{
        val value: T
        val children: List<Node<T>>
    }

    fun traversed(order: TraverseOrder) : Iterable<T>

}

enum class TraverseOrder{
    //todo: doc TraverseOrder items

    //current, left -> right
    NLR,

    //current, right -> left
    NRL,

    NLRBreadth,

    NRLBreadth,


    //left -> right, current
//    LRN,

    //right -> left, current
//    RLN
    //todo: implement breadth-first traversal
}