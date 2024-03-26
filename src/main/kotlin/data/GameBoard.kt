package com.sidapps.tictactoeai.data

data class GameBoard(val board: Array<Array<CellState>> = Array(3) { Array(3) { CellState.EMPTY } }) {
    fun setCellState(x: Int, y: Int, state: CellState) {
        if (isValidCoordinate(x, y)) {
            board[x][y] = state
        } else {
            throw IllegalArgumentException("Invalid coordinates: ($x, $y)")
        }
    }

    fun getCellState(x: Int, y: Int): CellState {
        if (isValidCoordinate(x, y)) {
            return board[x][y]
        } else {
            throw IllegalArgumentException("Invalid coordinates: ($x, $y)")
        }
    }

    private fun isValidCoordinate(x: Int, y: Int): Boolean {
        return x in 0 until 3 && y in 0 until 3
    }

    fun copy(): GameBoard {
        val copiedBoard = Array(3) { Array(3) { CellState.EMPTY } }
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                copiedBoard[i][j] = board[i][j]
            }
        }
        return GameBoard(copiedBoard)
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (row in board) {
            for (cell in row) {
                stringBuilder.append("$cell ")
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}