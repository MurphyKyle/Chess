package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * A King in a game of chess. 
 *
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @author Caleb Woods
 * @version Feb 11, 2014
 *******************************************************************/
public class King extends ChessPiece {

	/** Tells if the piece has moved of not. */
	private boolean hasMoved;
	
	/** Tells the pieces initial position. */
	private int initialRow, initialCol;
	
	private Player plr;

	/****************************************************************
	 * Constructor for King.
	 * 
	 * @param player the Player who owns this piece.
	 ***************************************************************/
	public King(Player player) {
		super(player);
		
		plr = player;
		hasMoved = false;
		initialRow = -1;
		initialCol = -1;
	}

	@Override
	public String type() {
		return "King";
	}

	@Override
	public boolean isValidMove(Move move, IChessBoard board) {
		
		if (!super.isValidMove(move, board)) {
			return false;
		}
		
		int fR = move.fromRow, fC = move.fromColumn;
		int tR = move.toRow, tC = move.toColumn;
		
		checkifMoved(fR, fC);

		if (mayCastle(move, board)) { return true; }
		
		// Relative coordinates of all points around the King
		int[] rowList = { 0,  1,  1, 1, 0, -1, -1, -1};
		int[] colList = {-1, -1,  0, 1, 1,  1,  0, -1};
		
		for (int i = 0; i < rowList.length; i++) {
			int row = rowList[i];
			int col = colList[i];
			
			/* If the destination matches a relative location from
			 * the arrays, then the move is valid */
			if (tR == fR + row && tC == fC + col) { return true; }
		}

		return false;
	}

	/****************************************************************
	 * Checks if the King has moved on the board before. 
	 * 
	 * @param fR the row location of the King.
	 * @param fC the column location of the King.
	 ***************************************************************/
	private void checkifMoved(final int fR, final int fC) {
		
		/* Once the piece has moved, hasMoved will always be true */
		if (!hasMoved) {
		
			/* Checks if the initial location is still -1, as set 
			 * by the constructor. */
			if (initialRow == -1 && initialCol == -1) {
				initialRow = fR;
				initialCol = fC;
				hasMoved = false;
				
			/* Checks if the initial location is the same as it was 
			 * when this piece last checked for a valid move*/	
			} else if (initialRow == fR && initialCol == fC) {
				hasMoved = false;
			} else {
				hasMoved = true;
			}
		}
	}
	
	/****************************************************************
	 * Tells if the King may castle.
	 * 
	 * @param m the Move being attempted.
	 * @param b the board the Move is being attempted on.
	 * @return true if the King may castle, false otherwise.
	 ***************************************************************/
	private boolean mayCastle(Move m, IChessBoard b) {
		int fR = m.fromRow, fC = m.fromColumn;
		int tR = m.toRow, tC = m.toColumn;		
		
		/* The can't castle if it has moved before */
		if (hasMoved || fC != 4) { return false; }
		
		IChessPiece toPiece = b.pieceAt(tR, tC);
		
		/* King needs to move to an empty square to castle */
		if (toPiece != null) { return false; }
		
		/* Only horizontal movement is valid */
		if (fR != tR) { return false; }
		
		IChessPiece rook = null;
		
		/* Castling on the King's side */
		if (tC == fC + 2) {
			
			/* Ensure the side is clear */
			if (b.pieceAt(fR, fC + 1) != null) { return false; }
			
			rook = b.pieceAt(tR, tC + 1);
		
		/* Castling on the Queen's side */
		} else if (tC == fC - 2) {
			
			/* Ensure the side is clear */
			if (b.pieceAt(fR, fC - 1) != null || 
					b.pieceAt(fR, fC - 3) != null) { 
				return false; 
			}
			
			rook = b.pieceAt(tR, tC - 2);
			
		} else { return false; }
		
		/* Null check for the rook */
		if (rook == null) { return false; }
		
		/* Looks to see if there is a friendly Rook in the proper
		 * position that has never moved */
		if (rook.is("Rook") && rook.player() == plr &&
				!((Rook) rook).hasMoved()) {
			return true;
		}
		
		return false;
	}
}
