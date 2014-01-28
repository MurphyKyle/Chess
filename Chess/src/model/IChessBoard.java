package model;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * This interface provides a uniform view of a Chess board to 
 * the IChessPiece interface.
 *
 * @author Zachary Kurmas
 * @author John O'Brien
 * @author Louis Marzorati
 * @author Shane Higgins
 * @version Jan 28, 2014
 *******************************************************************/
public interface IChessBoard {

   /*****************************************************************
    * Returns the number of rows on the chess board .
    * (a standard game has 8 rows)
    *
    * @return the number of rows on the chess board.
    ****************************************************************/
   int numRows();

   /*****************************************************************
    * Returns the number of columns on the chess board .
    * (a standard game has 8 columns)
    *
    * @return the number of columns on the chess board.
    ****************************************************************/
   int numColumns();

   /*****************************************************************
    * Return the ChessPiece object at location [row, column].
    *
    * @param row the row coordinate
    * @param column the column coordinate
    * @return the {@code ChessPiece} object at location {@code [row, column]}.
    * @throws IndexOutOfBoundsException if {@code [row, column]} 
    * 			is not a valid location on the board.
    ****************************************************************/
   IChessPiece pieceAt(int row, int column);
   
    /****************************************************************
    * Make the specified move. The move must be validated 
    * (i.e., this method will not verify that the move is legal).
    *
    * @param move the move to be made.
    ****************************************************************/
   public void move(Move move);

   /*****************************************************************
    * Place piece at [row, column].
    *
    * @param piece  the piece
    * @param row    the row
    * @param column the column
    ****************************************************************/
   public void set(IChessPiece piece, int row, int column);


   /*****************************************************************
    * Remove the piece from [row, column].
    *
    * @param row    the row
    * @param column the column
    ****************************************************************/
   public void unset(int row, int column);
}
