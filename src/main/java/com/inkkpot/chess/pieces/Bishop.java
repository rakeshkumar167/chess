/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess.pieces;

import java.util.List;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.enums.PieceValue;
import com.inkkpot.chess.enums.Position;
import com.inkkpot.chess.util.MoveUtil;

public class Bishop extends ChessPiece{
	public Bishop(Colour color, boolean isFirst) {
		setName("Bishop");
		setColor(color);
		setValue(PieceValue.BISHOP.val);
		if (Colour.BLACK.equals(color)) {
			if (isFirst) {
				setPosition(Position.C8);
			} else {
				setPosition(Position.F8);
			}
		} else {
			if (isFirst) {
				setPosition(Position.C1);
			} else {
				setPosition(Position.F1);
			}
		}
	}
	
	//Allowing custom position only in the case of pawn reaching the end
	public Bishop(Colour color, Position position){
		setName("Bishop");
		setColor(color);
		setPosition(position);
	}

	@Override
	public List<Position> getAvailableMoves(ChessBoard chessBoard) {
		List<Position> availableMoves = MoveUtil.getDiagonalMoves(chessBoard, this.getPosition(), this.getColor());
		return availableMoves;
	}
}
