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

public class Queen extends ChessPiece{

	public Queen(Colour color){
		setName("Queen");
		setColor(color);
		setValue(PieceValue.QUEEN.val);
		if (Colour.BLACK.equals(color)) {
			setPosition(Position.D8);
		} else {
			setPosition(Position.D1);
		}
	}
	
	//Allowing custom position only in the case of pawn reaching the end
	public Queen(Colour color, Position position){
		setName("Queen");
		setColor(color);
		setPosition(position);
		setValue(PieceValue.QUEEN.val);
	}

	@Override
	public List<Position> getAvailableMoves(ChessBoard chessBoard) {
		List<Position> availableMoves = new ArrayList<Position>();
		availableMoves.addAll(MoveUtil.getHorizontalMoves(chessBoard, this.getPosition(), this.getColor()));
		availableMoves.addAll(MoveUtil.getVerticalMoves(chessBoard, this.getPosition(), this.getColor()));
		availableMoves.addAll(MoveUtil.getDiagonalMoves(chessBoard, this.getPosition(), this.getColor()));
		return availableMoves;
	}
}
