import java.util.ArrayList;

public class Board
{
  private int[][] answerBoard;
  private int difficulty; // can be 1, 2, 3
  private int[][] userBoard;
  private int[][] tempBoard;

  public Board(int difficulty)
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
   * If the column is on the right border, it returns the first one
   * on the left.
   */
  public int getRight(int col)
  {
    if (col == 2)
      return 0;
    return col + 1;
  }

  /*
   * Returns the index of the row above a given row. If the row is at the top,
   * it returns the bottom one.
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

  /*
   * Randomly generates the board the user sees based on selected difficulty.
   * The user will have to make more guesses at higher difficulties.
   */
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
   * Returns true if there is no number occupying the space the user chose.
   */
  public boolean moveIsValid(int row, int column)
  {
    return row >= 0 && row <= 2 && column >= 0 && row <= 2
      && userBoard[row][column] == 0;
  }

  /*
   * If move is valid, makes it.
   */
  public void playerMove(int row, int column, int guessNumber)
  {
    if (moveIsValid(row, column))
    {
      userBoard[row][column] = guessNumber;
    }
  }

  /*
   * Returns the 'magic sum' of the square. This value is the sum of any
   * straight line that can be drawn across three numbers in the square. In
   * a traditional Magic Square, this value is 15.
   */
  public int getMagicSum()
  {
    return answerBoard[0][0] + answerBoard[0][1] + answerBoard[0][2];
  }

  /*
   * returns true if user's answers are right
   */
  public boolean validateBoard()
  {
    int magicSum = getMagicSum();
    for (int i = 0; i < userBoard.length; i++)
    {
      if (userBoard[i][0] + userBoard[i][1] + userBoard[i][2] != magicSum
        || userBoard[0][i] + userBoard[1][i] + userBoard[2][i] != magicSum)
      {
        return false;
      }
    }
    return userBoard[0][0] + userBoard[1][1] + userBoard[2][2] == magicSum
      || userBoard[0][2] + userBoard[1][1] + userBoard[2][0] == magicSum;
  }

  /*
   * Displays the current values of the magic square to the user.
   */
  public void showBoard()
  {
    System.out.println("Magic Sum = " + getMagicSum());
    for (int i = 0; i < userBoard.length; i++)
    {
      for (int j = 0; j < userBoard[i].length; j++)
      {
        System.out.print(userBoard[i][j] == 0 ? "X " : userBoard[i][j] + " ");
      }
      System.out.println();
    }
  }

  /*
   * copies content of one 2d array to another
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
