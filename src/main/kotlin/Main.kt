package org.sidapps.tictactoeai

import org.sidapps.tictactoeai.data.CellState
import org.sidapps.tictactoeai.data.GameBoard
import org.sidapps.tictactoeai.data.Gamestate

fun main() {

    val gameBoard = GameBoard()
//    gameBoard.setCellState(1, 1, CellState.X)
    gameBoard.setCellState(0, 0, CellState.X)
    gameBoard.setCellState(1, 1, CellState.O)
    gameBoard.setCellState(0, 2, CellState.X)

//    println(gameBoard)

    val gameState = Gamestate(false, gameBoard)
    val predictions = GameLogic.makePredictions(gameState)

//    println(predictions)
    predictions.exportToDotFile("graph.dot")
}