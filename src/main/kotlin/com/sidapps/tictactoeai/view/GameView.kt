package com.sidapps.tictactoeai.view

import com.sidapps.tictactoeai.data.CellState
import com.sidapps.tictactoeai.data.GameBoard
import com.sidapps.tictactoeai.data.Gamestate
import com.sidapps.tictactoeai.logic.GameLogic
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import tornadofx.View

class GameView : View("Tic-Tac-Toe") {
    private val playerLabel: Label by fxid("playerLabel")
    private val gameGrid: GridPane by fxid("gameGrid")

    private val gameState = Gamestate(true, GameBoard())

    override val root: GridPane by fxml("/GameView.fxml")

    init {
        updatePlayerLabel()
        initializeGameButtons()
    }

    private fun updatePlayerLabel() {
        playerLabel.text = if (gameState.isPlayer) "X to play" else "O to play"
    }

    private fun initializeGameButtons() {
        for (rowIndex in 1..3) {
            for (colIndex in 0..2) {
                val button = Button()
                button.text = "  "
                button.font = Font.font(24.0)
                button.setOnAction {
                    handleButtonClick(rowIndex - 1, colIndex, button)
                }
                gameGrid.add(button, colIndex, rowIndex)
            }
        }
    }

    private fun handleButtonClick(row: Int, col: Int, button: Button) {
        if (!gameState.isPlayer || gameState.gameBoard.getCellState(row, col) != CellState.EMPTY)
            return

        gameState.gameBoard.setCellState(row, col, CellState.X)
        button.text = "X"

        gameState.isPlayer = !gameState.isPlayer

        val prediction = GameLogic.makePredictions(gameState)
        val prunedPrediction = GameLogic.alphaBetaPruning(prediction)

        gameState.gameBoard = prunedPrediction.value
        gameState.isPlayer = !gameState.isPlayer

        updatePlayerLabel()
        updateGameBoardButtons()
    }

    private fun updateGameBoardButtons() {
        for (rowIndex in 1..3) {
            for (colIndex in 0..2) {
                val button = gameGrid.children.firstOrNull {
                    GridPane.getRowIndex(it) == rowIndex && GridPane.getColumnIndex(it) == colIndex
                } as? Button

                if (gameState.gameBoard.getCellState(rowIndex - 1, colIndex) != CellState.EMPTY)
                    button?.text = gameState.gameBoard.getCellState(rowIndex - 1, colIndex).toString()
            }
        }
    }
}