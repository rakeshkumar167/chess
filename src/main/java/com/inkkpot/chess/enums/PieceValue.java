package com.inkkpot.chess.enums;

public enum PieceValue {
	PAWN(100),
	ROOK(500),
	HORSE(300),
	BISHOP(300),
	QUEEN(900),
	KING(0);
	public final int val;
	
	private PieceValue(int val) {
		this.val = val;
	}
}
