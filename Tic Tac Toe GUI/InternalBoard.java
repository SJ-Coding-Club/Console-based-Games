public class InternalBoard
{
  private String[][] grid;
  private String playerMarker;

  public InternalBoard(String[][] internalGrid, String playerMarker)
  {
    this.playerMarker = playerMarker;
    grid = new String[3][3];
    for (int row = 0; row < 3; row++)
      for (int column = 0; column < 3; column++)
        grid[row][column] = internalGrid[row][column];
  }

  public InternalBoard(String playerMarker)
  {
    this.playerMarker = playerMarker;
    grid = new String[3][3];
    for (int r = 0; r < 3; r++)
      for (int c = 0; c < 3; c++)
        grid[r][c] = "";
  }

  public String[][] getInternalGrid()
  {
    return grid;
  }

  public Move makeMove(Move move)
  {
    grid[move.getRow()][move.getColumn()] = getOpposingMarker();
    return new Move(move.getRow(), move.getColumn());
  }

  public boolean hasMovesLeft()
  {
    for (int r = 0; r < 3; r++)
      for (int c = 0; c < 3; c++)
        if (grid[r][c].equals(""))
          return true;
    return false;
  }

  public int getScore()
  {
    for (int j = 0; j < 3; j++)
    {
      if (grid[0][j].equals(grid[1][j]) && grid[1][j].equals(grid[2][j]))
      {
        if (grid[0][j].equals(getOpposingMarker()))
          return 10;
        else if (grid[0][j].equals(playerMarker))
          return -10;
      }

      if (grid[j][0].equals(grid[j][1]) && grid[j][1].equals(grid[j][2]))
      {
        if (grid[j][0].equals(getOpposingMarker()))
          return 10;
        else if (grid[j][0].equals(playerMarker))
          return -10;
      }
    }
    if ((grid[0][0].equals(grid[1][1]) && grid[1][1].equals(grid[2][2]))
      || (grid[0][2].equals(grid[1][1]) && grid[1][1].equals(grid[2][0])))
    {
      if (grid[1][1].equals(getOpposingMarker()))
        return 10;
      else if (grid[1][1].equals(playerMarker))
        return -10;
    }
    return 0;
  }

  public int minimax(int depth, boolean isMaximizing)
  {
    int score = getScore();

    if (score == 10 || score == -10)
      return score;

    if (!hasMovesLeft())
      return 0;

    int best = isMaximizing ? -1000 : 1000;
    for (int row = 0; row < 3; row++)
      for (int col = 0; col < 3; col++)
        if (grid[row][col].equals(""))
        {
          grid[row][col] = isMaximizing ? getOpposingMarker() : playerMarker;
          best =
            isMaximizing ? Math.max(best, minimax(depth + 1, !isMaximizing))
              : Math.min(best, minimax(depth + 1, !isMaximizing));
          grid[row][col] = "";
        }
    return best;
  }

  public Move getBestMove()
  {
    int bestValue = -1000;
    Move bestMove = new Move(-1, -1);
    for (int r = 0; r < 3; r++)
      for (int c = 0; c < 3; c++)
        if (grid[r][c].equals(""))
        {
          grid[r][c] = getOpposingMarker();
          int moveValue = minimax(0, false);
          grid[r][c] = "";
          if (moveValue > bestValue)
          {
            bestMove = new Move(r, c);
            bestValue = moveValue;
          }
        }
    return bestMove;
  }

  private String getOpposingMarker()
  {
    if (playerMarker.equals("X"))
      return "O";
    return "X";
  }
}
