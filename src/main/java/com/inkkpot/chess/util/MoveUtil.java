/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess.util;

import java.util.ArrayList;
import java.util.List;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.enums.Position;
import com.inkkpot.chess.pieces.Queen;

/**
 * @author Rakesh
 *
 */
public class MoveUtil {

	public static List<Position> getVerticalMoves(ChessBoard board, Position position,Colour color){
		List<Position> verticalMoves = new ArrayList<Position>();
		char positionChar = position.name().charAt(0);
		int positionInt = Integer.valueOf(position.name().substring(1));
		for(int i = positionInt+1;i<9;i++){
			Position positionNew = Position.valueOf(""+positionChar+i);
			if(!board.getPiecesMap().containsKey(positionNew)){
				verticalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					verticalMoves.add(positionNew);
				}
				break;
			}
		}
		for(int j = positionInt-1;j>0;j--){
			Position positionNew = Position.valueOf(""+positionChar+j);
			if(!board.getPiecesMap().containsKey(positionNew)){
				verticalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					verticalMoves.add(positionNew);
				}
				break;
			}
		}
		return verticalMoves;
	}
	
	public static List<Position> getHorizontalMoves(ChessBoard board, Position position,Colour color){
		List<Position> horizontalMoves = new ArrayList<Position>();
		char positionChar = position.name().charAt(0);
		int positionInt = Integer.valueOf(position.name().substring(1));
		for(char c = (char) (positionChar+1);c<'I';c++){
			Position positionNew = Position.valueOf(""+c+positionInt);
			if(!board.getPiecesMap().containsKey(positionNew)){
				horizontalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					horizontalMoves.add(positionNew);
				}
				break;
			}
		}
		for(char c = (char) (positionChar-1);c>='A';c--){
			Position positionNew = Position.valueOf(""+c+positionInt);
			if(!board.getPiecesMap().containsKey(positionNew)){
				horizontalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					horizontalMoves.add(positionNew);
				}
				break;
			}
		}
		return horizontalMoves;
	}
	
	public static List<Position> getDiagonalMoves(ChessBoard board, Position position,Colour color){
		List<Position> diagonalMoves = new ArrayList<Position>();
		char positionChar = position.name().charAt(0);
		int positionInt = Integer.valueOf(position.name().substring(1));
		int i = positionInt+1;
		for(char c = (char) (positionChar+1) ;c<'I' && i<9;c++, i++){
			Position positionNew = Position.valueOf(""+c+i);
			if(!board.getPiecesMap().containsKey(positionNew)){
				diagonalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					diagonalMoves.add(positionNew);
				}
				break;
			}
		}
		i = positionInt-1;
		for(char c = (char) (positionChar-1) ;c>='A' && i>0;c--, i--){
			Position positionNew = Position.valueOf(""+c+i);
			if(!board.getPiecesMap().containsKey(positionNew)){
				diagonalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					diagonalMoves.add(positionNew);
				}
				break;
			}
		}
		i = positionInt+1;
		for(char c = (char) (positionChar-1) ;c>='A' && i<9;c--, i++){
			Position positionNew = Position.valueOf(""+c+i);
			if(!board.getPiecesMap().containsKey(positionNew)){
				diagonalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					diagonalMoves.add(positionNew);
				}
				break;
			}
		}
		i = positionInt-1;
		for(char c = (char) (positionChar+1) ;c<'I' && i>0;c++, i--){
			Position positionNew = Position.valueOf(""+c+i);
			if(!board.getPiecesMap().containsKey(positionNew)){
				diagonalMoves.add(positionNew);
			} else {
				if(!board.getPiecesMap().get(positionNew).getColor().equals(color)){
					diagonalMoves.add(positionNew);
				}
				break;
			}
		}
		return diagonalMoves;
	}
	
	public static boolean canPositionBeTaken(Position position, ChessBoard chessBoard, Colour color){
		if(!chessBoard.getPiecesMap().containsKey(position)){
			return true;
		} else {
			if(!chessBoard.getPiecesMap().get(position).getColor().equals(color)){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args){
		ChessBoard board = new ChessBoard();
		Queen queen = new Queen(Colour.BLACK, Position.B2);
		board.getPiecesMap().put(queen.getPosition(), queen);
//		getVerticalMoves(board, Position.B4, Color.WHITE);
		
		getVerticalMoves(new ChessBoard(), Position.D5, Colour.WHITE);
	}
	
}