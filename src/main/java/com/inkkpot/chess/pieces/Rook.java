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

public class Rook extends ChessPiece{
	
	public Rook(Colour color, boolean isFirst) {
		setName("Rook");
		setColor(color);
		setValue(PieceValue.ROOK.val);
		if (Colour.BLACK.equals(color)) {
			if (isFirst) {
				setPosition(Position.A8);
			} else {
				setPosition(Position.H8);
			}
		} else {
			if (isFirst) {
				setPosition(Position.A1);
			} else {
				setPosition(Position.H1);
			}
		}
	}
	
	//Allowing custom position only in the case of pawn reaching the end
	public Rook(Colour color, Position position){
		setName("Rook");
		setColor(color);
		setPosition(position);
	}

	@Override
	public List<Position> getAvailableMoves(ChessBoard chessBoard) {
		List<Position> availableMoves = new ArrayList<Position>();
		availableMoves.addAll(MoveUtil.getHorizontalMoves(chessBoard, this.getPosition(), this.getColor()));
		availableMoves.addAll(MoveUtil.getVerticalMoves(chessBoard, this.getPosition(), this.getColor()));
		return availableMoves;
	}

}
