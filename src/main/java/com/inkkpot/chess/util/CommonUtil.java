/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess.util;

import java.util.Collection;
import java.util.List;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Position;
import com.inkkpot.chess.move.NormalMove;
import com.inkkpot.chess.pieces.ChessPiece;
import com.inkkpot.chess.pieces.King;
import com.inkkpot.chess.pieces.Pawn;
import com.inkkpot.chess.pieces.Queen;

public class CommonUtil {
	
	public static NormalMove returnRandomMove(List<NormalMove> moves, ChessBoard board){
		if(moves!=null&&moves.size()>0){
			long index = Math.round(Math.random()*moves.size());
			if(index!=0){
				index = index -1;
			}
			if(moves.size()!=1&&board.getPiecesMap().get(moves.get((int)index)) instanceof King){
				return returnRandomMove(moves, board);//limiting king movement if other piece can be moved to produce same effect
			}
			return moves.get((int) index);
		}else{
			return null;
		}
	}
	
	public static void makeMove(ChessBoard board, Position oldPos, Position newPos) {
		if(board.getPiecesMap().containsKey(newPos)){
			board.getPiecesMap().remove(newPos);
		}
			ChessPiece piece = board.getPiecesMap().remove(oldPos);
			if(canPieceBePromoted(piece)){
				Queen queen = new Queen(piece.getColor(), piece.getPosition());
				piece=queen;
			}
			piece.setPosition(newPos);
			board.getPiecesMap().put(piece.getPosition(), piece);
	}
	
	public static boolean canPieceBePromoted(ChessPiece piece){
		if(piece instanceof Pawn){
			switch(piece.getColor()){
			case WHITE:
				switch(piece.getPosition()){
				case A7:return true;
				case B7:return true;
				case C7:return true;
				case D7:return true;
				case E7:return true;
				case F7:return true;
				case G7:return true;
				case H7:return true;
				}
				break;
			case BLACK:
				switch(piece.getPosition()){
				case A2:return true;
				case B2:return true;
				case C2:return true;
				case D2:return true;
				case E2:return true;
				case F2:return true;
				case G2:return true;
				case H2:return true;
				}
				break;
			}
		}
		return false;
	}
	
	public static boolean isNotEmpty(Collection c){
		if(c!=null&&c.size()>0){
			return true;
		}
		return false;
	}
}
