package org.sidapps.tictactoeai

import org.sidapps.tictactoeai.data.TreeNode

fun main() {
    val n1 = TreeNode(10)
    val n2 = TreeNode(20)
    val n3 = TreeNode(30)

    n1.addChild(n2)
    n1.addChild(n3)

    println(n1)
}