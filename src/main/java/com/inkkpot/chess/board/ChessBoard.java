package com.inkkpot.chess.board;

import java.util.*;

import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.enums.Position;
import com.inkkpot.chess.move.NormalMove;
import com.inkkpot.chess.pieces.*;
import com.inkkpot.chess.util.CommonUtil;
import com.inkkpot.chess.util.CopyUtil;

public class ChessBoard implements Cloneable{
	 private Colour humanPlayerColour;
	 private Colour computerPlayerColour;
	 private Map<Position,ChessPiece> piecesMap = new HashMap<Position, ChessPiece>();
	 private boolean whiteTop = true;
	 public static int count =0;
	 
	 public ChessBoard(){
		 count++;
	 }
	 
	 public boolean isOnCheck(Colour colour){
		 for(ChessPiece piece:getPiecesMap().values()){
			 if(!piece.getColor().equals(colour)){
				 for(Position position:piece.getAvailableMoves(this)){
					if(getPiecesMap().containsKey(position)){
						ChessPiece pieceToKill = getPiecesMap().get(position);
						if(pieceToKill.getColor().equals(colour)&&pieceToKill.getName().equals("King")){
							return true;
						}
					}
				 }
			 }
		 }
		 return false;
	 }
	 
	 public boolean isMoveAllowed(Position from, Position to){
		 NormalMove move = new NormalMove(from, to);
		 ChessBoard board = new ChessBoard();
		 CopyUtil.copyBoard(this, board);
		 Colour colour = board.getPiecesMap().get(move.getFrom()).getColor();
		 CommonUtil.makeMove(board, move.getFrom(), move.getTo());
		 if(board.isOnCheck(colour)){
			 return false;
		 }
		 return true;
	 }
	 
	 public List<NormalMove> getCheckRemovingMoves(Colour colour){
		 List<NormalMove> moves = new ArrayList<NormalMove>();
		 for(ChessPiece piece:this.getPiecesMap().values()){
			 if(piece.getColor().equals(colour)){
				 Position fromPosition = piece.getPosition();
				 for(Position toPosition:piece.getAvailableMoves(this)){
					 ChessBoard board = new ChessBoard();
					 CopyUtil.copyBoard(this, board);
					 CommonUtil.makeMove(board, fromPosition, toPosition);
					 if(!board.isOnCheck(colour)){
						 NormalMove move = new NormalMove(fromPosition, toPosition);//adding all chess clearing moves
						 moves.add(move);
					 }
				 }
			 }
		 }
		 
		 return moves;
	 }
	 
	 public boolean doesMoveMakeCheckMate(Position from, Position to){
		 boolean result =true;
		 ChessBoard board = new ChessBoard();
		 CopyUtil.copyBoard(this, board);
		 Colour colour = this.getPiecesMap().get(from).getColor();
		 Colour colourToEvaluate = colour.equals(Colour.WHITE)?Colour.BLACK:Colour.WHITE;
		 CommonUtil.makeMove(board, from, to);
		 for(ChessPiece piece:board.getPiecesMap().values()){
			 if(piece.getColor().equals(colourToEvaluate)){
				 for(Position position:piece.getAvailableMoves(board)){
					 ChessBoard boardOnNextMove = new ChessBoard();
					 CopyUtil.copyBoard(board, boardOnNextMove);
					 CommonUtil.makeMove(boardOnNextMove, piece.getPosition(), position);
					 if(!boardOnNextMove.isOnCheck(colourToEvaluate)){
						 return false;
					 }
				 }
			 }
		 }
		 return result;
	 }
	 
	 public boolean isBoardOnCheckMate(Colour colour){
		 for(ChessPiece piece:this.getPiecesMap().values()){
			 if(piece.getColor().equals(colour)){
				 for(Position position:piece.getAvailableMoves(this)){
					 ChessBoard boardOnNextMove = new ChessBoard();
					 CopyUtil.copyBoard(this, boardOnNextMove);
					 CommonUtil.makeMove(boardOnNextMove, piece.getPosition(), position);
					 if(!boardOnNextMove.isOnCheck(colour)){
						 return false;
					 }
				 }
			 }
		 }
		 if(isOnCheck(colour)){
			 return true;
		 }

		 return false;
	 }
	 
	public List<NormalMove> getAvailableMoves(Colour colour){
		List<NormalMove> moves = new ArrayList<NormalMove>();
		if (isOnCheck(colour)) {
			moves = getCheckRemovingMoves(colour);
		} else {
			for (ChessPiece piece : getPiecesMap().values()) {
				if (piece.getColor().equals(colour)) {
					Position moveFrom = piece.getPosition();
					for (Position moveTo : piece.getAvailableMoves(this)) {
						if(!isMoveAllowed(moveFrom, moveTo)){
							continue;
						} else {
							NormalMove move = new NormalMove(moveFrom, moveTo);
							moves.add(move);
						}
					}
				}
			}
		}
		return moves;
	}
	
	public int getAvailableMovesCount(Colour colour){
		int count = 0;
		if (isOnCheck(colour)) {
			count = getCheckRemovingMoves(colour).size();
		} else {
			for (ChessPiece piece : getPiecesMap().values()) {
				if (piece.getColor().equals(colour)) {
					Position moveFrom = piece.getPosition();
					for (Position moveTo : piece.getAvailableMoves(this)) {
						if(!isMoveAllowed(moveFrom, moveTo)){
							continue;
						} else {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	public int getWhiteValue() {
		int whiteVal = 0;
		for(ChessPiece piece:piecesMap.values()){
			if(Colour.WHITE.equals(piece.getColor())){
				whiteVal+= piece.getValue();
			}
		}
		return whiteVal;
	}

	public int getBlackValue() {
		int blackVal = 0;
		for(ChessPiece piece:piecesMap.values()){
			if(Colour.BLACK.equals(piece.getColor())){
				blackVal+= piece.getValue();
			}
		}
		return blackVal;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ChessBoard)){
			return false;
		}
		ChessBoard chessBoard = (ChessBoard)obj;
		if(this.piecesMap.size() != chessBoard.getPiecesMap().size()){
			return false;
		}
		for(Position position : piecesMap.keySet()){
			if(!chessBoard.getPiecesMap().containsKey(position)){
				return false;
			}
			if(!chessBoard.getPiecesMap().get(position).equals(piecesMap.get(position))){
				return false;
			}
		}
		return true;
	}

	public Map<Position, ChessPiece> getPiecesMap() {
		return piecesMap;
	}

	public void setPiecesMap(Map<Position, ChessPiece> piecesMap) {
		this.piecesMap = piecesMap;
	}
	
	public void displayBoard(){
		int newLineCount=1;
		for(Position pos:Position.values()){
			if(pos.getVal()==newLineCount){
				System.out.println();
				newLineCount++;
			}
			if(this.getPiecesMap().containsKey(pos)){
				ChessPiece piece = this.getPiecesMap().get(pos);
				char name = piece.getClass().getName().substring(piece.getClass().getName().lastIndexOf(".")).charAt(1);
				if(piece.getColor().equals(Colour.BLACK)){
					System.out.print("|B"+name+"|");
				} else {
					System.out.print("|w"+name+"|");
				}
			} else {
				System.out.print("|  |");
			}
		}
	}
	
	public void initialize(){
		ChessPiece piece = null;
		for(Colour colr:Colour.values()){
			piece = new Queen(colr);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Bishop(colr,true);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Bishop(colr,false);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Horse(colr,true);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Horse(colr,false);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Rook(colr,true);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Rook(colr,false);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,1);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,2);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,3);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,4);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,5);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,6);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,7);
			piecesMap.put(piece.getPosition(), piece);
			piece = new Pawn(colr,8);
			piecesMap.put(piece.getPosition(), piece);
			piece = new King(colr);
			piecesMap.put(piece.getPosition(), piece);
		}
	}
	
	public void initializeMin(){
		ChessPiece piece = null;
		Colour colr = Colour.WHITE;
		piece = new King(colr);
		piecesMap.put(piece.getPosition(), piece);
		piece = new Pawn(colr,Position.A7);
		piecesMap.put(piece.getPosition(), piece);
		
		colr = Colour.BLACK;
		piece = new King(colr);
		piecesMap.put(piece.getPosition(), piece);
		
	}
	
	public void makeMove(NormalMove move){
		ChessPiece piece = getPiecesMap().remove(move.getFrom());
		if(CommonUtil.canPieceBePromoted(piece)){
			Queen queen = new Queen(piece.getColor(), piece.getPosition());
			piece=queen;
		}
		piece.setPosition(move.getTo());
		getPiecesMap().put(piece.getPosition(), piece);
	}
	
	public boolean isWhiteTop() {
		return whiteTop;
	}

	public void setWhiteTop(boolean whiteTop) {
		this.whiteTop = whiteTop;
	}

	public Colour getHumanPlayerColour() {
		return humanPlayerColour;
	}

	public void setHumanPlayerColour(Colour humanPlayerColour) {
		this.humanPlayerColour = humanPlayerColour;
	}

	public Colour getComputerPlayerColour() {
		return computerPlayerColour;
	}

	public void setComputerPlayerColour(Colour computerPlayerColour) {
		this.computerPlayerColour = computerPlayerColour;
	}

	public int getPositionalValue(Colour colour) {
		int value = 0;
		for (Map.Entry<Position, ChessPiece> entry : piecesMap.entrySet()) {
			ChessPiece piece = entry.getValue();
			if (piece.getColor() == colour) {
				// Center control bonus
				Position pos = entry.getKey();
				int row = pos.getRow();
				int col = pos.getColumn();
				if (row >= 3 && row <= 6 && col >= 3 && col <= 6) {
					value += 10;
				}
				// Pawn structure bonus
				if (piece instanceof Pawn) {
					if (isConnectedPawn(pos)) {
						value += 5;
					}
					if (isPassedPawn(pos, piece.getColor())) {
						value += 20;
					}
				}
				// King safety
				if (piece instanceof King) {
					if (isInEndgame()) {
						value += centralizeKingEndgame(pos);
					} else {
						value += evaluateKingSafety(pos, piece.getColor());
					}
				}
			}
		}
		return value;
	}

	private boolean isConnectedPawn(Position pos) {
		int col = pos.getColumn();
		// Check adjacent columns for friendly pawns
		for (int adjCol = col - 1; adjCol <= col + 1; adjCol += 2) {
			Position adjPos = Position.getPosition(pos.getRow(), adjCol);
			if (adjPos != null && piecesMap.containsKey(adjPos)) {
				ChessPiece adjPiece = piecesMap.get(adjPos);
				if (adjPiece instanceof Pawn && adjPiece.getColor() == piecesMap.get(pos).getColor()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isPassedPawn(Position pos, Colour colour) {
		int row = pos.getRow();
		if (colour == Colour.WHITE) {
			return row == 8;
		} else {
			return row == 1;
		}
	}

	private boolean isInEndgame() {
		int majorPieces = 0;
		for (ChessPiece piece : piecesMap.values()) {
			if (piece instanceof Queen || piece instanceof Rook) {
				majorPieces++;
			}
		}
		return majorPieces <= 2;
	}

	private int centralizeKingEndgame(Position pos) {
		// In endgame, king should move to center
		int row = pos.getRow();
		int col = pos.getColumn();
		int distanceFromCenter = Math.abs(4 - row) + Math.abs(4 - col);
		return (7 - distanceFromCenter) * 10;
	}

	private int evaluateKingSafety(Position pos, Colour colour) {
		int value = 0;
		// Prefer back rank in early/mid game
		int safeRank = colour == Colour.WHITE ? 1 : 8;
		if (pos.getRow() == safeRank) {
			value += 30;
		}
		// Pawn shield
		value += countPawnShield(pos, colour) * 10;
		return value;
	}

	private int countPawnShield(Position kingPos, Colour colour) {
		int count = 0;
		int row = kingPos.getRow();
		int col = kingPos.getColumn();
		int pawnRow = colour == Colour.WHITE ? row + 1 : row - 1;
		
		for (int checkCol = col - 1; checkCol <= col + 1; checkCol++) {
			Position checkPos = Position.getPosition(pawnRow, checkCol);
			if (checkPos != null && piecesMap.containsKey(checkPos)) {
				ChessPiece piece = piecesMap.get(checkPos);
				if (piece instanceof Pawn && piece.getColor() == colour) {
					count++;
				}
			}
		}
		return count;
	}
}