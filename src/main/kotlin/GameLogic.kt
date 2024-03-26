package org.sidapps.tictactoeai

import org.sidapps.tictactoeai.data.CellState
import org.sidapps.tictactoeai.data.GameBoard
import org.sidapps.tictactoeai.data.Gamestate
import org.sidapps.tictactoeai.data.TreeNode

object GameLogic {
    private fun countPotentialWins(gameBoard: GameBoard, player: CellState): Int {
        var potentialWins = 0

        // Rows
        for (row in 0 until 3) {
            if (gameBoard.board[row][0] == player && gameBoard.board[row][1] == player && gameBoard.board[row][2] == CellState.EMPTY)
                potentialWins++

            if (gameBoard.board[row][0] == player && gameBoard.board[row][2] == player && gameBoard.board[row][1] == CellState.EMPTY)
                potentialWins++

            if (gameBoard.board[row][1] == player && gameBoard.board[row][2] == player && gameBoard.board[row][0] == CellState.EMPTY)
                potentialWins++

        }

        // Columns
        for (col in 0 until 3) {
            if (gameBoard.board[0][col] == player && gameBoard.board[1][col] == player && gameBoard.board[2][col] == CellState.EMPTY)
                potentialWins++

            if (gameBoard.board[0][col] == player && gameBoard.board[2][col] == player && gameBoard.board[1][col] == CellState.EMPTY)
                potentialWins++

            if (gameBoard.board[1][col] == player && gameBoard.board[2][col] == player && gameBoard.board[0][col] == CellState.EMPTY)
                potentialWins++
        }

        // Diagonals
        if (gameBoard.board[0][0] == player && gameBoard.board[1][1] == player && gameBoard.board[2][2] == CellState.EMPTY)
            potentialWins++

        if (gameBoard.board[0][0] == player && gameBoard.board[2][2] == player && gameBoard.board[1][1] == CellState.EMPTY)
            potentialWins++

        if (gameBoard.board[1][1] == player && gameBoard.board[2][2] == player && gameBoard.board[0][0] == CellState.EMPTY)
            potentialWins++

        if (gameBoard.board[0][2] == player && gameBoard.board[1][1] == player && gameBoard.board[2][0] == CellState.EMPTY)
            potentialWins++

        if (gameBoard.board[0][2] == player && gameBoard.board[2][0] == player && gameBoard.board[1][1] == CellState.EMPTY)
            potentialWins++

        if (gameBoard.board[1][1] == player && gameBoard.board[2][0] == player && gameBoard.board[0][2] == CellState.EMPTY)
            potentialWins++

        return potentialWins
    }

    private fun checkWinner(gameBoard: GameBoard, player: CellState): Boolean {
        return countPotentialWins(gameBoard, player) >= 3
    }

    private fun calculateHeuristicValue(gameBoard: GameBoard): Int {
        if (checkWinner(gameBoard, CellState.O))
            return 100

        if (checkWinner(gameBoard, CellState.X))
            return -100

        val oPotentialWins = countPotentialWins(gameBoard, CellState.O)
        val xPotentialWins = countPotentialWins(gameBoard, CellState.X)

        return oPotentialWins - xPotentialWins
    }

    fun makePredictions(gameState: Gamestate, depth: Int = 0, maxDepth: Int = 3): TreeNode<GameBoard> {
        val rootNode = TreeNode(gameState.gameBoard)

        if (depth < maxDepth) {
            val currentPlayer = if (gameState.isPlayer) CellState.X else CellState.O
            val nextStateList = mutableListOf<GameBoard>()

            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (gameState.gameBoard.getCellState(i, j) == CellState.EMPTY) {
                        val nextBoard = gameState.gameBoard.copy()
                        nextBoard.setCellState(i, j, currentPlayer)
                        nextStateList.add(nextBoard)
                    }
                }
            }

            for (nextBoard in nextStateList) {
                val nextState = Gamestate(!gameState.isPlayer, nextBoard)
                val childNode = makePredictions(nextState, depth + 1, maxDepth)

                val heuristicValue = calculateHeuristicValue(nextBoard)
                childNode.heuristicValue = heuristicValue

                rootNode.addChild(childNode)
            }
        }

        return rootNode
    }
}