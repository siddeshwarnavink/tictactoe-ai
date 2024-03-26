package org.sidapps.tictactoeai.data

enum class CellState {
    EMPTY,
    X,
    O
}

data class GameBoard(val board: Array<Array<CellState>> = Array(3) { Array(3) { CellState.EMPTY } })