package com.inkkpot.chess.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.inkkpot.chess.board.ChessBoard;
import com.inkkpot.chess.enums.Colour;
import com.inkkpot.chess.enums.Position;
import com.inkkpot.chess.move.NormalMove;
import com.inkkpot.chess.pieces.*;

public class CreateBoard extends JFrame implements MouseListener, MouseMotionListener {
	private static final int CELL_SIZE_SMALL = 40;
	private int CELL_SIZE = 75;
	private static final long serialVersionUID = 1L;
	JLayeredPane layeredPane;
	public JPanel chessBoardPanel;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	int origXOfChessPiece;
	int origYOfChessPiece;
	ChessBoard board = new ChessBoard();
	public boolean gameOver = false;
	public boolean computerThinking = false;
	private boolean smallSize;
	static Dimension bigDimension = new Dimension(600, 600);
	static Dimension smallDimension = new Dimension(320,320);
	
	public static int HAND_CURSOR=12;
	public static int LOADING_CURSOR=3;
	public static Color MOVE_COLOUR = new Color(91,62,0);

	public CreateBoard(boolean smallSize) {
		setSmallSize(smallSize);
		Dimension boardSize = null;
		if(smallSize){
			boardSize = smallDimension;
			CELL_SIZE = CELL_SIZE_SMALL;
		}else {
			boardSize = bigDimension;
		}
		// Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);
		layeredPane.setName("Chess");

		// Add a chess board to the Layered Pane

		chessBoardPanel = new JPanel();
		layeredPane.add(chessBoardPanel, JLayeredPane.DEFAULT_LAYER);
		chessBoardPanel.setLayout(new GridLayout(8, 8));
		chessBoardPanel.setPreferredSize(boardSize);
		chessBoardPanel.setBounds(0, 0, boardSize.width, boardSize.height);
		
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoardPanel.add(square);

			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground(i % 2 == 0 ? Color.LIGHT_GRAY : Color.white);
			else
				square.setBackground(i % 2 == 0 ? Color.white : Color.LIGHT_GRAY);
		}
	}
	
	public void setBoard(ChessBoard cboard, NormalMove move){
		board = cboard;
		addPieces();
		colourMove(move);
		if(cboard.isOnCheck(cboard.getHumanPlayerColour())){
			for(ChessPiece piece :cboard.getPiecesMap().values()){
				if(piece instanceof King && piece.getColor().equals(cboard.getHumanPlayerColour())){
					JPanel panel = (JPanel) chessBoardPanel.getComponent(piece.getPosition().ordinal());
					panel.setBackground(Color.RED);
					break;
				}
			}
		}
	}
	
	private void colourMove(NormalMove move){
		if(move==null)return;
		JPanel fromPanel = (JPanel) chessBoardPanel.getComponent(move.getFrom().ordinal());
		JPanel toPanel = (JPanel) chessBoardPanel.getComponent(move.getTo().ordinal());
		fromPanel.setBackground(MOVE_COLOUR);
		fromPanel.setBorder(new LineBorder(Color.BLACK,1,true));
		toPanel.setBackground(MOVE_COLOUR);
		toPanel.setBorder(new LineBorder(Color.BLACK,1,true));
	}

	private void addPieces() {
		Collection<ChessPiece> pieces = board.getPiecesMap().values();
		for(int i =0 ;i<64;i++){
			JPanel panel = (JPanel) chessBoardPanel.getComponent(i);
			panel.removeAll();
			panel.setBorder(null);
			int row = (i / 8) % 2;
			if (row == 0)
				panel.setBackground(i % 2 == 0 ? new Color(220,146,0) : new Color(255,198,107));
			else
				panel.setBackground(i % 2 == 0 ? new Color(255,198,107) : new Color(220,146,0));
		}
		for (ChessPiece piece : pieces) {
			ImageIcon imageIcon = null;
			if (piece instanceof Pawn) {
				String name = piece.getColor().equals(Colour.WHITE) ? "wPawn" : "bPawn";
				if(isSmallSize()){
					name = name +"Small";
				}
				imageIcon = new ImageIcon(getClass().getClassLoader().getResource(name+".png"));
			}
			if (piece instanceof Rook) {
				String name = piece.getColor().equals(Colour.WHITE) ? "wRook" : "bRook";
				if(isSmallSize()){
					name = name +"Small";
				}
				imageIcon = new ImageIcon(getClass().getClassLoader().getResource(name+".png"));
			}
			if (piece instanceof Horse) {
				String name = piece.getColor().equals(Colour.WHITE) ? "wKnight" : "bKnight";
				if(isSmallSize()){
					name = name +"Small";
				}
				imageIcon = new ImageIcon(getClass().getClassLoader().getResource(name+".png"));
			}
			if (piece instanceof Bishop) {
				String name = piece.getColor().equals(Colour.WHITE) ? "wBishop" : "bBishop";
				if(isSmallSize()){
					name = name +"Small";
				}
				imageIcon = new ImageIcon(getClass().getClassLoader().getResource(name+".png"));
			}
			if (piece instanceof Queen) {
				String name = piece.getColor().equals(Colour.WHITE) ? "wQueen" : "bQueen";
				if(isSmallSize()){
					name = name +"Small";
				}
				imageIcon = new ImageIcon(getClass().getClassLoader().getResource(name+".png"));
			}
			if (piece instanceof King) {
				String name = piece.getColor().equals(Colour.WHITE) ? "wKing" : "bKing";
				if(isSmallSize()){
					name = name +"Small";
				}
				imageIcon = new ImageIcon(getClass().getClassLoader().getResource(name+".png"));
			}

			JLabel jPiece = new JLabel(imageIcon);
			JPanel panel = (JPanel) chessBoardPanel.getComponent(piece.getPosition().ordinal());
			panel.setName(piece.getPosition().name());
			panel.add(jPiece);
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if(isComputerThinking()){
			return;
		}
		if(gameOver){
			JOptionPane.showMessageDialog(null, "Not allowed.Game over", "Game over", 0);
		}
		chessPiece = null;
		Component c = chessBoardPanel.findComponentAt(e.getX(), e.getY());
		origXOfChessPiece = e.getX();
		origYOfChessPiece = e.getY();
		if (c instanceof JPanel)
			return;

		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel) c;
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}

	public void mouseReleased(MouseEvent e) {
		if(isComputerThinking()){
			return;
		}
		if(gameOver){
			JOptionPane.showMessageDialog(null, "Not allowed.Game over", "Game over", 0);
		}
		if (chessPiece == null)
			return;
		Component c = chessBoardPanel.findComponentAt(e.getX(), e.getY());
		if(c==null){
			return;
		}
		int index = 0;
		if(c instanceof JLabel){
			Container parentt = c.getParent();
			index = (parentt.getX()/CELL_SIZE)+(8*(parentt.getY()/CELL_SIZE));
		}else {
			index = (c.getX()/CELL_SIZE)+(8*(c.getY()/CELL_SIZE));
		}
		Position to = Position.getPositionFromIndex(index);
		index = (origXOfChessPiece/CELL_SIZE)+(8*(origYOfChessPiece/CELL_SIZE));
		Position from = Position.getPositionFromIndex(index);
		if(isMoveAllowed(from, to)){//is new position allowed
			chessPiece.setVisible(false);
		if (c instanceof JLabel) {
			Container parent = c.getParent();
			parent.remove(0);
			parent.add(chessPiece);
		} else {
			Container parent = (Container) c;
			parent.add(chessPiece);
		}
		chessPiece.setVisible(true);
		NormalMove move = new NormalMove(from, to);
		board.makeMove(move);
		synchronized(board){
			board.notifyAll();
		}
		}else {
			System.out.println("Move not allowed");
			chessPiece.setVisible(false);
			Component cOrig = chessBoardPanel.findComponentAt(origXOfChessPiece, origYOfChessPiece);
			if (cOrig instanceof JLabel) {
				Container parent = cOrig.getParent();
				parent.remove(0);
				parent.add(chessPiece);
			} else {
				Container parent = (Container) cOrig;
				parent.add(chessPiece);
			}
			chessPiece.setVisible(true);

		}


	}
	
	private boolean isMoveAllowed(Position from, Position to){
		System.out.println(from+"-->"+to);
		ChessPiece piece = board.getPiecesMap().get(from);
		if(piece.getAvailableMoves(board).contains(to)&&piece.getColor().equals(board.getHumanPlayerColour())){
			if(board.isMoveAllowed(from, to)){
				return true;
			}
		}
		
		return false;
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if(isComputerThinking()){
			return;
		}
		if(gameOver){
			JOptionPane.showMessageDialog(null, "Not allowed.Game over", "Game over", 1);
		}
		if (chessPiece == null)
			return;
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);

	}

	public void mouseMoved(MouseEvent e) {

	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isComputerThinking() {
		return computerThinking;
	}

	public void setComputerThinking(boolean computerThinking) {
		this.computerThinking = computerThinking;
	}

	public boolean isSmallSize() {
		return smallSize;
	}

	public void setSmallSize(boolean smallSize) {
		this.smallSize = smallSize;
	}
}
