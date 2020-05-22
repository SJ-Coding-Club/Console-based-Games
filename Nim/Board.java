public class Board
{
  final String[][] defaultBoard =
    {{null, null, null, "!", null, null, null},
        {null, null, "!", "!", "!", null, null},
        {null, "!", "!", "!", "!", "!", null},
        {"!", "!", "!", "!", "!", "!", "!"}};
  String board[][];

  public Board(String[][] currentBoard)
  {
    board = new String[4][7];
    for (int row = 0; row < board.length; row++)
    {
      for (int col = 0; col < board[0].length; col++)
      {
        board[row][col] = currentBoard[row][col];
      }
    }
  }

  public Board()
  {
    board = new String[4][7];
    for (int row = 0; row < board.length; row++)
    {
      for (int col = 0; col < board[0].length; col++)
      {
        board[row][col] = defaultBoard[row][col];
      }
    }
  }

  /*
   * Returns 2d board array
   */
  public String[][] getBoard()
  {
    return board;
  }

  /*
   * displays current board
   */
  public void showBoard()
  {
    for (int r = 0; r < board.length; r++)
    {
      System.out.print(r + " ");
      for (int c = 0; c < board[r].length; c++)
      {
        if (board[r][c] == null)
        {
          System.out.print(" ");
        }
        else
        {
          System.out.print(board[r][c]);
        }
      }
      System.out.println();
    }

  }

  /*
   * Determines best computer move.
   */
  public Move findBestMove()
  {
    // scan board --> check if taking one set of matches results in an immediate
    // win
    int[] rowSums = getRowSums();
    if ((rowSums[0] > 0 && rowSums[1] == 1 && rowSums[2] == 0
      && rowSums[3] == 0)
      || (rowSums[0] > 0 && rowSums[1] == 0 && rowSums[2] == 1
        && rowSums[3] == 0)
      || (rowSums[0] > 0 && rowSums[1] == 0 && rowSums[2] == 0
        && rowSums[3] == 1))
    {
      return new Move(0, rowSums[0]);
    }

    if ((rowSums[0] == 1 && rowSums[1] > 0 && rowSums[2] == 0
      && rowSums[3] == 0)
      || (rowSums[0] == 0 && rowSums[1] > 0 && rowSums[2] == 1
        && rowSums[3] == 0)
      || (rowSums[0] == 0 && rowSums[1] > 0 && rowSums[2] == 0
        && rowSums[3] == 1))
    {
      return new Move(1, rowSums[1]);
    }

    if ((rowSums[0] == 1 && rowSums[1] == 0 && rowSums[2] > 0
      && rowSums[3] == 0)
      || (rowSums[0] == 0 && rowSums[1] == 1 && rowSums[2] > 0
        && rowSums[3] == 0)
      || (rowSums[0] == 0 && rowSums[1] == 0 && rowSums[2] > 0
        && rowSums[3] == 1))
    {
      return new Move(2, rowSums[2]);
    }

    if ((rowSums[0] == 1 && rowSums[1] == 0 && rowSums[2] == 0
      && rowSums[3] > 0)
      || (rowSums[0] == 0 && rowSums[1] == 1 && rowSums[2] == 0
        && rowSums[3] > 0)
      || (rowSums[0] == 0 && rowSums[1] == 0 && rowSums[2] == 1
        && rowSums[3] > 0))
    {
      return new Move(3, rowSums[3]);
    }
    // if above checks fail, return the move that removes the most matches, while
    // maintaining a nim-sum of 0
    int mostMatches = 0;
    int bestRow = 0;
    for (int row = 0; row < board.length; row++)
    {
      for (int matches = 1; matches < board[row].length; matches++)
      {
        Board temp = new Board(board);
        Move temp1 = new Move(row, matches);
        if (temp.moveIsValid(temp1))
        {
          temp.makeMove(temp1);
          if (temp.nimSumEqualsZero())
          {
            if (matches > mostMatches)
            {
              mostMatches = matches;
              bestRow = row;
            }
          }
        }
      }
    }
    return new Move(bestRow, mostMatches);
  }

  /*
   * Determines if move can be made.
   */
  public boolean moveIsValid(Move move)
  {
    int row = move.getRow();
    int matchesToRemove = move.getSticksToRemove();

    int matchesInRow = 0;
    for (int i = 0; i < board[row].length; i++)
    {
      if (board[row][i] != null)
      {
        matchesInRow++;
      }
    }
    if (matchesToRemove > matchesInRow)
    {
      return false;
    }
    return true;
  }

  /*
   * returns true if move is possible, and makes it.
   */

  public void makeMove(Move move)
  {
    int row = move.getRow();
    int matchesToRemove = move.getSticksToRemove();
    int i = 0;
    int j = 0;
    while (row < board.length && i < board[row].length && j < matchesToRemove)
    {
      if (board[row][i] != null)
      {
        board[row][i] = null;
        j++;
      }
      i++;
    }
  }
  /*
   * calculate sum of each row, store in array
   */
  public int[] getRowSums()
  {
    int[] rowSums = new int[4];
    for (int row = 0; row < board.length; row++)
    {
      int rowSum = 0;
      for (int column = 0; column < board[row].length; column++)
      {
        if (board[row][column] != null)
          rowSum++;
      }
      rowSums[row] = rowSum;
    }
    return rowSums;
  }

  /*
   * Calculates nim sum of current board configuration, returns true if the sum
   * is 0
   */
  public boolean nimSumEqualsZero()
  {
    int[] rowSums = getRowSums();

    int oneCount = 0;
    int twoCount = 0;
    int fourCount = 0;
    for (int i = 0; i < rowSums.length; i++)
    {
      if (rowSums[i] >= 4)
      {
        rowSums[i] = rowSums[i] - 4;
        fourCount++;
      }
      if (rowSums[i] >= 2)
      {
        rowSums[i] = rowSums[i] - 2;
        twoCount++;
      }
      if (rowSums[i] == 1)
      {
        oneCount++;
      }
    }
    return oneCount % 2 == 0 && twoCount % 2 == 0 && fourCount % 2 == 0;
  }

  /*
   * Returns 0 --> No winner.
   * Returns 1 --> Player wins.
   * Returns 2 --> Computer wins.
   */
  public int getWinner(int turn)
  {
    int stickCount = 0;
    for (int i = 0; i < board.length; i++)
    {
      for (int j = 0; j < board[0].length; j++)
      {
        if (board[i][j] != null)
        {
          stickCount++;
        }
      }
    }
    if (stickCount > 1)
      return 0;
    if (stickCount == 1)
      if (turn % 2 == 0)
      {
        return 1;
      }
    return 2;
  }
}
