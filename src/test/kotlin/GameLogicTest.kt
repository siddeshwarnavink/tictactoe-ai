import kotlin.test.Test
import kotlin.test.assertEquals

import com.sidapps.tictactoeai.data.GameBoard
import com.sidapps.tictactoeai.data.CellState
import com.sidapps.tictactoeai.GameLogic
import com.sidapps.tictactoeai.data.TreeNode

class GameLogicTest {
    @Test
    fun testCountPotentialWins() {
        val gameBoard = GameBoard(arrayOf(
            arrayOf(CellState.X, CellState.EMPTY, CellState.O),
            arrayOf(CellState.EMPTY, CellState.X, CellState.EMPTY),
            arrayOf(CellState.O, CellState.EMPTY, CellState.O)
        ))

        assertEquals(1, GameLogic.countPotentialWins(gameBoard, CellState.X))
        assertEquals(3, GameLogic.countPotentialWins(gameBoard, CellState.O))

        // empty board test
        val emptyGameBoard = GameBoard()
        assertEquals(0, GameLogic.countPotentialWins(emptyGameBoard, CellState.X))
        assertEquals(0, GameLogic.countPotentialWins(emptyGameBoard, CellState.O))
    }

    @Test
    fun testCheckWinner() {
        // Test where either X or O is winning
        val xWinningGameBoard = GameBoard(arrayOf(
            arrayOf(CellState.X, CellState.X, CellState.X),
            arrayOf(CellState.EMPTY, CellState.EMPTY, CellState.EMPTY),
            arrayOf(CellState.EMPTY, CellState.EMPTY, CellState.EMPTY)
        ))
        val oWinningGameBoard = GameBoard(arrayOf(
            arrayOf(CellState.O, CellState.EMPTY, CellState.EMPTY),
            arrayOf(CellState.O, CellState.EMPTY, CellState.EMPTY),
            arrayOf(CellState.O, CellState.EMPTY, CellState.EMPTY)
        ))
        assertEquals(true, GameLogic.checkWinner(xWinningGameBoard, CellState.X))
        assertEquals(true, GameLogic.checkWinner(oWinningGameBoard, CellState.O))

        // Test where nobody is winning
        val nonWinningGameBoard = GameBoard(arrayOf(
            arrayOf(CellState.X, CellState.O, CellState.X),
            arrayOf(CellState.O, CellState.X, CellState.O),
            arrayOf(CellState.O, CellState.X, CellState.EMPTY)
        ))
        assertEquals(false, GameLogic.checkWinner(nonWinningGameBoard, CellState.X))
        assertEquals(false, GameLogic.checkWinner(nonWinningGameBoard, CellState.O))
    }

    @Test
    fun testMinimax() {
        val rootNode = TreeNode(GameBoard())
        val child1 = TreeNode(GameBoard()).apply { heuristicValue = 5 }
        val child2 = TreeNode(GameBoard()).apply { heuristicValue = 3 }
        val child3 = TreeNode(GameBoard()).apply { heuristicValue = 8 }
        rootNode.addChild(child1)
        rootNode.addChild(child2)
        rootNode.addChild(child3)

        val result = GameLogic.minimax(rootNode, 2, Int.MIN_VALUE, Int.MAX_VALUE, true)
        assertEquals(8, result)
    }

    @Test
    fun testAlphaBetaPruning() {
        val rootNode = TreeNode(GameBoard())
        val child1 = TreeNode(GameBoard()).apply { heuristicValue = 5 }
        val child2 = TreeNode(GameBoard()).apply { heuristicValue = 3 }
        val child3 = TreeNode(GameBoard()).apply { heuristicValue = 8 }
        rootNode.addChild(child1)
        rootNode.addChild(child2)
        rootNode.addChild(child3)

        val resultNode = GameLogic.alphaBetaPruning(rootNode, 2)
        assertEquals(8, resultNode.heuristicValue)
    }
}