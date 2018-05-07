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

public class Pawn extends ChessPiece{

	public Pawn(Colour color, int number) {
		setName("Pawn");
		setColor(color);
		setValue(PieceValue.PAWN.val);
		if (Colour.WHITE.equals(color)) {
			switch(number){
				case 1		:setPosition(Position.A2);break;
				case 2		:setPosition(Position.B2);break;
				case 3		:setPosition(Position.C2);break;
				case 4		:setPosition(Position.D2);break;
				case 5		:setPosition(Position.E2);break;
				case 6		:setPosition(Position.F2);break;
				case 7		:setPosition(Position.G2);break;
				case 8		:setPosition(Position.H2);break;
			}
		} else {
			switch(number){
				case 1		:setPosition(Position.A7);break;
				case 2		:setPosition(Position.B7);break;
				case 3		:setPosition(Position.C7);break;
				case 4		:setPosition(Position.D7);break;
				case 5		:setPosition(Position.E7);break;
				case 6		:setPosition(Position.F7);break;
				case 7		:setPosition(Position.G7);break;
				case 8		:setPosition(Position.H7);break;
		}
		}
	}
	
	public Pawn(Colour color, Position position){
		setName("Pawn");
		setColor(color);
		setPosition(position);
		setValue(PieceValue.PAWN.val);
	}

	@Override
	public List<Position> getAvailableMoves(ChessBoard chessBoard) {
		List<Position> availableMoves = new ArrayList<Position>();
		char positionChar = this.getPosition().name().charAt(0);
		int positionInt = Integer.valueOf(this.getPosition().name().substring(1));
		if(this.getColor().equals(Colour.WHITE)){
			if(positionInt<8){
				if(positionChar>'A'){
				 Position positionLeftDiagonal = Position.valueOf(""+(char)(positionChar-1)+(positionInt+1));
				 if(chessBoard.getPiecesMap().containsKey(positionLeftDiagonal)&&
						 !chessBoard.getPiecesMap().get(positionLeftDiagonal).getColor().equals(this.getColor())){
					 availableMoves.add(positionLeftDiagonal);
				 }
				}
				if(positionChar<'H'){
				 Position positionRightDiagonal = Position.valueOf(""+(char)(positionChar+1)+(positionInt+1));
				 if(chessBoard.getPiecesMap().containsKey(positionRightDiagonal)&&
						 !chessBoard.getPiecesMap().get(positionRightDiagonal).getColor().equals(this.getColor())){
					 availableMoves.add(positionRightDiagonal);
				 }
				}
				 Position positionStraightOneStep = Position.valueOf(""+(char)(positionChar)+(positionInt+1));
				 if(!chessBoard.getPiecesMap().containsKey(positionStraightOneStep)){
					 availableMoves.add(positionStraightOneStep);
				 }
				 
				 if(positionInt == 2){
					 Position positionStraightTwoStep = Position.valueOf(""+(char)(positionChar)+(positionInt+2));
					 if(!chessBoard.getPiecesMap().containsKey(positionStraightTwoStep)&&
							 !chessBoard.getPiecesMap().containsKey(positionStraightOneStep)){
						 availableMoves.add(positionStraightTwoStep);
					 }
				 }
			}
		} else{
			if(positionInt>1){
				if(positionChar>'A'){
				 Position positionLeftDiagonal = Position.valueOf(""+(char)(positionChar-1)+(positionInt-1));
				 if(chessBoard.getPiecesMap().containsKey(positionLeftDiagonal)&&
						 !chessBoard.getPiecesMap().get(positionLeftDiagonal).getColor().equals(this.getColor())){
					 availableMoves.add(positionLeftDiagonal);
				 }
				}
				if(positionChar<'H'){
				 Position positionRightDiagonal = Position.valueOf(""+(char)(positionChar+1)+(positionInt-1));
				 if(chessBoard.getPiecesMap().containsKey(positionRightDiagonal)&&
						 !chessBoard.getPiecesMap().get(positionRightDiagonal).getColor().equals(this.getColor())){
					 availableMoves.add(positionRightDiagonal);
				 }
				}
				 Position positionStraightOneStep = Position.valueOf(""+(char)(positionChar)+(positionInt-1));
				 if(!chessBoard.getPiecesMap().containsKey(positionStraightOneStep)){
					 availableMoves.add(positionStraightOneStep);
				 }
				 
				 if(positionInt == 7){
					 Position positionStraightTwoStep = Position.valueOf(""+(char)(positionChar)+(positionInt-2));
					 if(!chessBoard.getPiecesMap().containsKey(positionStraightTwoStep)&&
							 !chessBoard.getPiecesMap().containsKey(positionStraightOneStep)){
						 availableMoves.add(positionStraightTwoStep);
					 }
				 }
			}
		}
		return availableMoves;
	}
	
	public static void main(String[] args){
		ChessBoard board = new ChessBoard();
		ChessPiece piece0 = new Pawn(Colour.WHITE, Position.C3);
		ChessPiece piece = new Pawn(Colour.WHITE, Position.B2);
		board.getPiecesMap().put(Position.B2,piece);
		board.getPiecesMap().put(Position.C3, piece0);
		System.out.println(piece.getAvailableMoves(board));
	}

}
