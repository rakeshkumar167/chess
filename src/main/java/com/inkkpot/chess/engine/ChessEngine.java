package com.inkkpot.chess.engine;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.move.NormalMove;

public interface ChessEngine {
	NormalMove getNextMoveBoard(ChessBoard board,Colour nextMoveColor, int level);
}
