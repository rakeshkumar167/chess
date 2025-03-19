package com.inkkpot.chess.enums;

public enum Colour {
	WHITE,BLACK;
	
	public Colour oppositeColour(){
		if(this.equals(Colour.WHITE)){
			return Colour.BLACK;
		}else {
			return Colour.WHITE;
		}
	}
}
