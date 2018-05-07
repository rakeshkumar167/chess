package com.inkkpot.chess.engine;

import java.awt.Cursor;
import java.util.List;

import com.inkkpot.chess.Game;
import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.move.NormalMove;
import com.inkkpot.chess.ui.CreateBoard;
import com.inkkpot.chess.util.CommonUtil;
import com.inkkpot.chess.util.CopyUtil;

public class ChessEngineAlphaBetaPruning implements ChessEngine{
	
	public NormalMove getNextMoveBoard(ChessBoard board, Colour colour, int level){
		List<NormalMove> moveList = board.getAvailableMoves(colour);
		int tempVal = Integer.MIN_VALUE;
		NormalMove move = null;
		int totMoves = moveList.size();
		int counter = 0;
		for(NormalMove mv: moveList){
			counter++;
			Game.chessBoardUI.setTitle("Evaluating move "+counter+" of "+totMoves+" available");
			Game.chessBoardUI.setCursor(new Cursor(CreateBoard.LOADING_CURSOR));
			ChessBoard newBoard = new ChessBoard();
			CopyUtil.copyBoard(board, newBoard);
			CommonUtil.makeMove(newBoard, mv.getFrom(), mv.getTo());
			int val = getNextMoveVal(newBoard, colour, colour.oppositeColour(), 
					level-1, Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println("\nmove:"+mv.getFrom()+"-->"+mv.getTo()+": "+val);
			if(tempVal<val){
				move = mv;
				tempVal = val;
			}
		}
		Game.chessBoardUI.setCursor(new Cursor(CreateBoard.HAND_CURSOR));
		Game.chessBoardUI.setTitle(Game.BOARD_TITLE+"   (Black's turn)");
		return move;
	}
	
	public int getNextMoveVal(ChessBoard board, Colour origMoveColour, 
			Colour nextMoveColor, int level, int alpha, int beta){
		if (level == 0) {
			return board.getWhiteValue()-board.getBlackValue();
		}
		List<NormalMove> moveList = board.getAvailableMoves(nextMoveColor);
		if(origMoveColour == nextMoveColor){
			if(moveList.size()==0){
				return -10000*level;
			}
			for (NormalMove move : moveList) {
				ChessBoard newBoard = new ChessBoard();
				CopyUtil.copyBoard(board, newBoard);
				CommonUtil.makeMove(newBoard, move.getFrom(), move.getTo());
				int tempVal = getNextMoveVal(newBoard, origMoveColour, 
						nextMoveColor.oppositeColour(), level - 1, alpha, beta);
				if (alpha < tempVal) {
					alpha = tempVal;
				}
				if (alpha >= beta) {
					return alpha;
				}
			}
			return alpha;
		} else {
			if(moveList.size()==0){
				return 10000*level;
			}
			for (NormalMove move : moveList) {
				ChessBoard newBoard = new ChessBoard();
				CopyUtil.copyBoard(board, newBoard);
				CommonUtil.makeMove(newBoard, move.getFrom(), move.getTo());
				int tempVal = getNextMoveVal(newBoard, origMoveColour, 
						nextMoveColor.oppositeColour(), level - 1, alpha, beta);
				if (tempVal < beta) {
					beta = tempVal;
				}
				if (alpha >= beta) {
					return beta;
				}
			}
			return beta;
		}
	}
}
