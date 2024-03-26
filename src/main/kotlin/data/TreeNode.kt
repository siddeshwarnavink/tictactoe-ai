package org.sidapps.tictactoeai.data

import java.io.File

data class TreeNode<T>(val value: T, var heuristicValue: Int = 0) {
    var parent:TreeNode<T>? = null
    val children:MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(node:TreeNode<T>){
        children.add(node)
        node.parent = this
    }

    override fun toString(): String {
        var s = "$value"
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }

    fun toDotString(): String {
        val stringBuilder = StringBuilder()
        toDotStringRecursive(stringBuilder)
        return stringBuilder.toString()
    }

    private fun toDotStringRecursive(stringBuilder: StringBuilder) {
        stringBuilder.append("${hashCode()} [label=\"${value.toString().replace("\"", "\\\"")}\nHeuristic: $heuristicValue\"];\n")
        for (child in children) {
            stringBuilder.append("${hashCode()} -> ${child.hashCode()};\n")
            child.toDotStringRecursive(stringBuilder)
        }
    }

    fun exportToDotFile(fileName: String) {
        val dotString = toDotString()
        File(fileName).writeText("digraph Tree {\n$dotString}")
    }
}