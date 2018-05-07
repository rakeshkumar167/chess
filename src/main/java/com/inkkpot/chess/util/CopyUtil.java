/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess.util;


import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.enums.Position;
import com.inkkpot.chess.pieces.Bishop;
import com.inkkpot.chess.pieces.ChessPiece;
import com.inkkpot.chess.pieces.Horse;
import com.inkkpot.chess.pieces.King;
import com.inkkpot.chess.pieces.Pawn;
import com.inkkpot.chess.pieces.Queen;
import com.inkkpot.chess.pieces.Rook;

/**
 * @author Rakesh
 *
 */
public class CopyUtil {

	public static void copyBoard(ChessBoard boardOrig, ChessBoard boardNew){
		for(ChessPiece piece:boardOrig.getPiecesMap().values()){
			ChessPiece pieceNew = copyPiece(piece);
			boardNew.getPiecesMap().put(pieceNew.getPosition(), pieceNew);
		}
	}
	
	public static ChessPiece copyPiece(ChessPiece pieceOrig){
		ChessPiece pieceNew = null;
		Position pos = pieceOrig.getPosition();
		Colour colr = pieceOrig.getColor();
		if(pieceOrig instanceof Pawn){
			pieceNew = new Pawn(colr, pos);
		} else if(pieceOrig instanceof King) {
			pieceNew = new King(colr, pos);
		} else if(pieceOrig instanceof Queen) {
			pieceNew = new Queen(colr, pos);
		} else if(pieceOrig instanceof Bishop) {
			pieceNew = new Bishop(colr, pos);
		} else if(pieceOrig instanceof Horse) {
			pieceNew = new Horse(colr, pos);
		} else if(pieceOrig instanceof Rook) {
			pieceNew = new Rook(colr, pos);
		} 
		pieceNew.setValue(pieceOrig.getValue());
		return pieceNew;
	}
}
