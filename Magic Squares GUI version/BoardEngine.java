package MagicSquares;

import java.util.ArrayList;

public class BoardEngine
{
  private int[][] answerBoard;
  private int[][] userBoard;
  private int[][] tempBoard;
  private int difficulty;

  public BoardEngine(int difficulty)
  {
    this.difficulty = difficulty;
    answerBoard = new int[3][3];
    userBoard = new int[3][3];
    tempBoard = new int[3][3];
    generateAnswerBoard();
    generateUserBoard();
  }

  /*
   * Generates a random number, sets middle space in the first row to this
   * value. Until the board is completely filled, move up and to the right one
   * space and assign the previous value + 1 to that space. If the up is out of
   * bounds, go to bottom. If right is out of bounds, go left. If this space is
   * occupied, go directly down.
   */
  public void generateAnswerBoard()
  {
    int value = (int) (Math.random() * Math.pow(10, difficulty) + 1);
    int row = 0;
    int column = 1;

    while (hasSpaces(answerBoard))
    {
      answerBoard[row][column] = value;
      value++;
      if (answerBoard[getUp(row)][getRight(column)] != 0)
      {
        row++;
      }
      else
      {
        row = getUp(row);
        column = getRight(column);
      }
    }
  }

  /*
   * Returns the index of the column to the right of a given column.
   */
  public int getRight(int col)
  {
    if (col == 2)
      return 0;
    return col + 1;
  }

  /*
   * Returns the index of the row above a given row.
   */
  public int getUp(int row)
  {
    if (row == 0)
      return 2;
    return row - 1;
  }

  /*
   * Returns true if there are still unoccupied spaces in the square.
   */
  public boolean hasSpaces(int[][] board)
  {
    for (int i = 0; i < board.length; i++)
    {
      for (int j = 0; j < board[i].length; j++)
      {
        if (board[i][j] == 0)
          return true;
      }
    }
    return false;
  }

  // generates board for user
  public void generateUserBoard()
  {
    copy2dArray(answerBoard, userBoard);
    int numberToRemove = 2 + difficulty;
    ArrayList<Integer> pointsToRemove = new ArrayList<>();
    int point = -1;
    for (int i = 0; i < numberToRemove; i++)
    {
      while (point == -1 || pointsToRemove.contains(point))
      {
        point = (int) (Math.random() * 9);
      }
      pointsToRemove.add(point);
    }

    for (int i = 0; i < pointsToRemove.size(); i++)
    {
      int row = pointsToRemove.get(i) / 3;
      int column = pointsToRemove.get(i) % 3;
      userBoard[row][column] = 0;
    }
    copy2dArray(userBoard, tempBoard);
  }

  /*
   * Returns the 'magic sum' of the square. This value is the sum of all
   * straight lines that can be drawn across any three numbers in the square. In
   * a traditional Magic Square, this value is 15.
   */
  public int getMagicSum()
  {
    return answerBoard[0][0] + answerBoard[0][1] + answerBoard[0][2];
  }

  /*
   * checks if user's answers are right
   */
  public boolean validateBoard()
  {
    int magicSum = getMagicSum();
    for (int i = 0; i < tempBoard.length; i++)
    {
      if (tempBoard[i][0] + tempBoard[i][1] + tempBoard[i][2] != magicSum
        || tempBoard[0][i] + tempBoard[1][i] + tempBoard[2][i] != magicSum)
      {
        return false;
      }
    }
    return tempBoard[0][0] + tempBoard[1][1] + tempBoard[2][2] == magicSum
      || tempBoard[0][2] + tempBoard[1][1] + tempBoard[2][0] == magicSum;
  }

  /*
   * copies content of 2d array from one into another
   */
  public void copy2dArray(int[][] a, int[][] b)
  {
    for (int row = 0; row < a.length; row++)
    {
      for (int col = 0; col < a[row].length; col++)
      {
        b[row][col] = a[row][col];
      }
    }
  }

  public int[][] getUserBoard()
  {
    return userBoard;
  }

  public int[][] getTempBoard()
  {
    return tempBoard;
  }

}