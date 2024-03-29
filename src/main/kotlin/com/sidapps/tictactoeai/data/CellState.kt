package com.sidapps.tictactoeai.data

enum class CellState {
    EMPTY {
        override fun toString(): String {
            return "   "
        }
    },
    X {
        override fun toString(): String {
            return "X"
        }
    },
    O {
        override fun toString(): String {
            return "O"
        }
    }
}
