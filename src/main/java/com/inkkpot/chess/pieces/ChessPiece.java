package com.inkkpot.chess.pieces;

import java.util.List;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.enums.Position;

public abstract class ChessPiece {
	private Colour color;
	private Position position;
	private int value;
	private String name;
	
	public abstract List<Position> getAvailableMoves(ChessBoard chessBoard);

	@Override
	public boolean equals(Object obj) {
		if(!this.getClass().equals(obj.getClass())){
			return false;
		}
		ChessPiece piece = (ChessPiece)obj;
		if(!this.getColor().equals(piece.getColor())){
			return false;
		}
		return true;
	}
	
	public Colour getColor() {
		return color;
	}

	public void setColor(Colour color) {
		this.color = color;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
