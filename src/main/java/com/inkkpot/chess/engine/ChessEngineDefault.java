/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess.engine;

import java.util.ArrayList;
import java.util.List;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.*;
import com.inkkpot.chess.move.NormalMove;
import com.inkkpot.chess.pieces.ChessPiece;
import com.inkkpot.chess.util.*;

public class ChessEngineDefault implements ChessEngine{
	public static final int CHECKMATE_VAL = 5000;
	
	public NormalMove getNextMoveBoard(ChessBoard board,  
			Colour nextMoveColor, int level) {
		if (level == 0) {
			NormalMove move = new NormalMove(null,null,0);
			return move;
		} else {
			int val = Integer.MIN_VALUE;
			List<NormalMove> moves = new ArrayList<NormalMove>();
			// handling check condition separately
			if (board.isOnCheck(nextMoveColor)) {
				List<NormalMove> checkRemovingMoves = board.getCheckRemovingMoves(nextMoveColor);
				if(checkRemovingMoves==null||checkRemovingMoves.size()==0){
					return null;
				}
				for(NormalMove move:checkRemovingMoves){
					ChessBoard boardOnNextMove = new ChessBoard();
					CopyUtil.copyBoard(board, boardOnNextMove);// copying board and
																										// creating a new one
					int newVal = 0;
					if (board.getPiecesMap().containsKey(move.getTo())) {
						ChessPiece pieceKilled = board.getPiecesMap().get(move.getTo());
						newVal = pieceKilled.getValue();
					}
					CommonUtil.makeMove(boardOnNextMove, move.getFrom(), move.getTo());
					int nextVal = 0;
					if(level>0){
						NormalMove opponentMove = getNextMoveBoard(boardOnNextMove, 
							nextMoveColor.oppositeColour(), level - 1);
						if(opponentMove==null){
							nextVal = Integer.MIN_VALUE;
						}else {
							nextVal = opponentMove.getVal();
						}
					}
					newVal = newVal - nextVal;
					if (val <= newVal) {
						if (val < newVal) {
							moves.clear();
						}
						val = newVal;
						move.setVal(val);
						moves.add(move);
					}
				}
			} else {//if board is not on check
				for (ChessPiece piece : board.getPiecesMap().values()) {
					if (piece.getColor().equals(nextMoveColor)) {
						Position moveFrom = piece.getPosition();
						for (Position moveTo : piece.getAvailableMoves(board)) {
							if(!board.isMoveAllowed(moveFrom, moveTo)){
								continue;
							}
							 //handling checkmatemove giving very high weigtage
							if (board.doesMoveMakeCheckMate(moveFrom, moveTo)) {
								NormalMove checkMateMove = new NormalMove(moveFrom, moveTo,CHECKMATE_VAL);
								return checkMateMove;
							}
							 //handling other moves

							ChessBoard boardOnNextMove = new ChessBoard();
							CopyUtil.copyBoard(board, boardOnNextMove);// copying board and creating a new one
							int newVal = 0;
							if (board.getPiecesMap().containsKey(moveTo)) {
								ChessPiece pieceKilled = board.getPiecesMap().get(moveTo);
								newVal = pieceKilled.getValue();
							}
							CommonUtil.makeMove(boardOnNextMove, moveFrom, moveTo);
							
							int nextVal = 0;
							if(level>0){
								NormalMove opponentMove = getNextMoveBoard(boardOnNextMove, 
									 nextMoveColor.oppositeColour(), level - 1);
								if(opponentMove==null){
									nextVal = Integer.MIN_VALUE;
								}else {
									nextVal = opponentMove.getVal();
								}
							}
							newVal = newVal - nextVal;
							if (val <= newVal) {
								if (val < newVal) {
									moves.clear();
								}
								val = newVal;
								NormalMove move = new NormalMove(moveFrom, moveTo, val);
								moves.add(move);
							}
						}
					}
				}
			}
			return CommonUtil.returnRandomMove(moves, board);
		}
	}
	
}
