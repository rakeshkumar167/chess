/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess;

import java.awt.HeadlessException;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.engine.ChessEngine;
import com.inkkpot.chess.engine.ChessEngineAlphaBetaPruning;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.move.NormalMove;
import com.inkkpot.chess.pieces.ChessPiece;
import com.inkkpot.chess.ui.CreateBoard;
import com.inkkpot.chess.ui.Splash;

public class Game {
	public static final String BOARD_TITLE ="Chess Lite 1.0";
	public static final String SPLASH_IMAGE = "splash.png";
	public static final String ICON_IMAGE = "icon.png";
	public static final int LEVEL_DEPTH = 3;
	public static final boolean smallSize = false;
	public static ChessEngine engine = new ChessEngineAlphaBetaPruning();
	public static CreateBoard chessBoardUI = new CreateBoard(smallSize);
	
	
	public static void main(String[] args) throws HeadlessException, InterruptedException, IOException {
		ChessBoard board = new ChessBoard();
		new Splash(SPLASH_IMAGE, new JFrame(), 5000);
		board.initialize();
		board.setHumanPlayerColour(Colour.BLACK);
		board.setComputerPlayerColour(Colour.WHITE);
		ImageIcon icon = new ImageIcon(board.getClass().getClassLoader().getResource(ICON_IMAGE));
		Image temp = icon.getImage();
		board.displayBoard();
		chessBoardUI.setBoard(board, null);
		JFrame frame = chessBoardUI;
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setIconImage(temp);
		frame.setLocationRelativeTo(null);
		frame.setTitle(BOARD_TITLE);
		frame.setVisible(true);
		chessBoardUI.setComputerThinking(true);
		NormalMove move = engine.getNextMoveBoard(board, board.getComputerPlayerColour(), LEVEL_DEPTH);
		while (move != null) {
			ChessPiece piece = board.getPiecesMap().get(move.getFrom());
			if (board.getPiecesMap().containsKey(move.getTo())) {
				ChessPiece pieceKilled = board.getPiecesMap().get(move.getTo());
				System.out.print("\nKilled:" + pieceKilled.getColor() + " " + pieceKilled.getClass().getSimpleName());
			}
			System.out.println("\n" + piece.getColor() + " " + piece.getClass().getSimpleName() + ":" + move.getFrom() + "--> " + move.getTo());
			System.out.println("\nBoards created in computation: " + ChessBoard.count);
			board.makeMove(move);
			chessBoardUI.setBoard(board, move);
			frame = chessBoardUI;
			frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			board.displayBoard();
			if (board.isBoardOnCheckMate(board.getHumanPlayerColour())) {
				JOptionPane.showMessageDialog(null, "Game Over", "You lose", 1);
				break;
			}
			chessBoardUI.setComputerThinking(false);
			synchronized (board) {
				try {
					board.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			chessBoardUI.setComputerThinking(true);
			move = engine.getNextMoveBoard(board, board.getComputerPlayerColour(), LEVEL_DEPTH);
		}
		if (board.isBoardOnCheckMate(board.getComputerPlayerColour())) {
			JOptionPane.showMessageDialog(null, "You see..I am still learning..", "Congratulations..you won", 1);
		} else if (!board.isBoardOnCheckMate(board.getHumanPlayerColour())) {
			JOptionPane.showMessageDialog(null, "You couldnt even defeat me..", "Stalemate", 1);
		}
		chessBoardUI.setGameOver(true);

	}
}
