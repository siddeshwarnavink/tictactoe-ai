package com.sidapps.tictactoeai.view

import com.sidapps.tictactoeai.data.CellState
import com.sidapps.tictactoeai.data.GameBoard
import com.sidapps.tictactoeai.data.Gamestate
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import tornadofx.View

class GameView : View("Tic-Tac-Toe") {
    private val playerLabel: Label by fxid("playerLabel")
    private val gameGrid: GridPane by fxid("gameGrid")

    private val gameBoard = GameBoard()
    private val gameState = Gamestate(true, gameBoard)

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
        println("Button click works!")

        if (!gameState.isPlayer || gameBoard.getCellState(row, col) != CellState.EMPTY)
            return

        gameBoard.setCellState(row, col, CellState.X)
        button.text = "X"
        updatePlayerLabel()
    }

}