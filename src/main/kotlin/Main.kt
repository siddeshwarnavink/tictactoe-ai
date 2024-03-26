package com.sidapps.tictactoeai

import com.sidapps.tictactoeai.data.CellState
import com.sidapps.tictactoeai.data.GameBoard
import com.sidapps.tictactoeai.data.Gamestate

fun main() {

    val gameBoard = GameBoard()
    gameBoard.setCellState(1, 1, CellState.X)
//    gameBoard.setCellState(0, 0, CellState.X)
//    gameBoard.setCellState(1, 1, CellState.O)
//    gameBoard.setCellState(0, 2, CellState.X)

//    println(gameBoard)

    val gameState = Gamestate(false, gameBoard)
    val predictions = GameLogic.makePredictions(gameState)

    val prunedPredictions = GameLogic.alphaBetaPruning(predictions, 3)

//    println(predictions)
//    predictions.exportToDotFile("graph.dot")
    prunedPredictions.exportToDotFile("graph.dot")
}