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

public class Horse extends ChessPiece {
	
	public Horse(Colour color, boolean isFirst) {
		setName("Horse");
		setColor(color);
		setValue(PieceValue.HORSE.val);
		if (Colour.BLACK.equals(color)) {
			if (isFirst) {
				setPosition(Position.B8);
			} else {
				setPosition(Position.G8);
			}
		} else {
			if (isFirst) {
				setPosition(Position.B1);
			} else {
				setPosition(Position.G1);
			}
		}
	}
	
	//Allowing custom position only in the case of pawn reaching the end
	public Horse(Colour color, Position position){
		setName("Horse");
		setColor(color);
		setPosition(position);
	}

	@Override
	public List<Position> getAvailableMoves(ChessBoard chessBoard) {
		List<Position> availableMoves = new ArrayList<Position>();
		char positionChar = this.getPosition().name().charAt(0);
		int positionInt = Integer.valueOf(this.getPosition().name().substring(1));
		char c = (char)(positionChar-2);
		if(c<'A'){
			c='A';
		}
		for(;c<'I'&&c<=(positionChar+2);c++){
			if(c==positionChar){
				continue;
			}
			int i = positionInt-2;
			if(i<1){
				i=1;
			}
			for(;i<9&&i<=(positionInt+2);i++){
				if(i==positionInt){
					continue;
				}
				if(Math.abs(positionChar-c)==Math.abs(positionInt-i)){
					continue;
				}
				Position positionNew = Position.valueOf(""+c+i);
				if(MoveUtil.canPositionBeTaken(positionNew, chessBoard, this.getColor())){
					availableMoves.add(positionNew);
				}
			}
		}
		return availableMoves;
	}
}
