package com.tsayun.web_crawler.traversableTree.impl

import com.tsayun.web_crawler.traversableTree.TraversableTree
import com.tsayun.web_crawler.traversableTree.TraverseOrder
import org.junit.jupiter.api.Test

internal class LazyTraversableTreeTest {

    @Test
    fun traversed() {
        val tree: TraversableTree<Int> = LazyTraversableTree(1) { i ->
            when (i) {
//                1 -> listOf(2, 3, 4)
//                2 -> listOf()
//                3 -> listOf(5)
//                4 -> listOf()
//                5 -> listOf(6)
//                6 -> listOf(7)
//                7 -> listOf()
                1 -> listOf(2, 3, 4)
                2 -> listOf(5, 6, 7, 8)
                3 -> listOf(9, 10, 11)
                4 -> listOf(12, 13)
                5 -> listOf()
                6 -> listOf()
                7 -> listOf(14, 15)
                8 -> listOf()
                9 -> listOf()
                10 -> listOf(16)
                11 -> listOf()
                12 -> listOf(17, 18, 19)
                13 -> listOf()
                14 -> listOf()
                15 -> listOf()
                16 -> listOf()
                17 -> listOf()
                18 -> listOf()
                19 -> listOf()
                else -> listOf()
            }
        }
        val start = System.nanoTime()
//        val elapsed = measureTimeMillis {
        for (i in tree.traversed(TraverseOrder.NRLBreadth)) {
            println(i)
        }
//        }
//        println(elapsed)
        println(System.nanoTime() - start)
    }
}