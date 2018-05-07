/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.enums.PieceValue;
import com.inkkpot.chess.enums.Position;
import com.inkkpot.chess.util.MoveUtil;

public class King extends ChessPiece {
	
	public King(Colour color) {
		setName("King");
		setColor(color);
		setValue(PieceValue.KING.val);
		if (Colour.BLACK.equals(color)) {
			setPosition(Position.E8);
		} else {
			setPosition(Position.E1);
		}
	}
	
	public King(Colour color, Position position){
		setName("King");
		setPosition(position);
		setColor(color);
	}

	@Override
	public List<Position> getAvailableMoves(ChessBoard chessBoard) {
		List<Position> availableMoves = new ArrayList<Position>();
		char positionChar = this.getPosition().name().charAt(0);
		int positionInt = Integer.valueOf(this.getPosition().name().substring(1));
		char c;int i;
		if(positionChar == 'A'){
			c = positionChar;
		} else {
			c = (char)(positionChar -1);
		}

		for(;c<'I' && c<(positionChar+2);c++){
			if(positionInt == 1){
				i = positionInt;
			} else {
				i = positionInt-1;
			}
			for(;i<9&&i<(positionInt+2);i++){
				Position positionNew = Position.valueOf(""+c+i);
				if(!positionNew.equals(this.getPosition())){
					if(MoveUtil.canPositionBeTaken(positionNew, chessBoard, this.getColor())){
						availableMoves.add(positionNew);
					}
				}
			}
		}
		return availableMoves;
	}
	
	public static void main(String[] args){
		King king = new King(Colour.WHITE);
		ChessBoard board = new ChessBoard();
		board.getPiecesMap().put(king.getPosition(), king);
		System.out.println(king.getPosition());
		System.out.println(king.getAvailableMoves(board));
	}
}
