import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Board
{
  private char[][] grid;

  // initialize board with constructor
  public Board()
  {
    grid = new char[3][3];
    for (char[] e : grid)
    {
      Arrays.fill(e, '-');
    }
  }

  public void setCoordinate(int row, int column, char key)
  {
    grid[row][column] = key;
  }

  public char getCoordinate(int row, int column)
  {
    return grid[row][column];
  }

  public String getWinner()
  {
    if (grid[1][1] != '-'
      && ((grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
        || (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])))
    {
      if (grid[1][1] == 'X')
        return "X";
      return "O";
    }
    for (int row = 0; row < grid.length; row++)
    {
      for (int col = 0; col < grid[row].length; col++)
      {
        if (grid[row][col] != '-'
          && ((col == 0 && grid[row][col] == grid[row][col + 1]
            && grid[row][col] == grid[row][col + 2])
            || (row == 0 && grid[row][col] == grid[row + 1][col]
              && grid[row][col] == grid[row + 2][col])))
        {
          if (grid[row][col] == 'X')
            return "X";
          return "O";
        }
      }
    }
    return "Tie";
  }

  /*
   * Issue;
   * if user enters non integer input,
   * code crashes. add something to catch this error
   * 
   * 
   */
  public void userMove(int turn)
  {
    Scanner s = new Scanner(System.in);
    int row = -1;
    int column = -1;

    while (!coordinateIsOpen(row, column))
    {
      System.out.println("Enter Valid Coordinates: ");
      row = s.nextInt();
      column = s.nextInt();
    }
    setCoordinate(row, column, turn % 2 == 0 ? 'X' : 'O');
  }

  public void selectBestMove()
  {
    // find each possible move
    // add each move to list
    ArrayList<Integer[]> possibleMoves = new ArrayList<>();

    for (int row = 0; row < 3; row++)
    {
      for (int col = 0; col < 3; col++)
      {
        if (grid[row][col] == '-')
        {
          possibleMoves.add(new Integer[] {row, col});
        }
      }
    }
    int[] scores = new int[possibleMoves.size()];

    // get the score for each move
    for (int i = 0; i < possibleMoves.size(); i++)
    {
      scores[i] =
        getMoveScore(possibleMoves.get(i)[0], possibleMoves.get(i)[1]);
    }

    // find optimal move
    int index = -1;
    int max = 0;
    for (int i = 0; i < scores.length; i++)
    {
      if (scores[i] > max)
      {
        index = i;
        max = scores[index];
      }
    }
    // if it finds an optimal move, do it
    if (index != -1)
    {
      grid[possibleMoves.get(index)[0]][possibleMoves.get(index)[1]] = 'O';
    }
    // otherwise, make a random move
    else
    {
      int random = (int) (Math.random() * possibleMoves.size());
      grid[possibleMoves.get(random)[0]][possibleMoves.get(random)[1]] = 'O';
    }

  }

  public int getMoveScore(int row, int col)
  {
    int score = 0;
    // optimal move = winning move
    if ((col == 0 && grid[row][col + 1] == 'O' && grid[row][col + 2] == 'O')
      || (col == 1 && grid[row][col - 1] == 'O' && grid[row][col + 1] == 'O')
      || (col == 2 && grid[row][col - 2] == 'O' && grid[row][col - 1] == 'O')
      || (row == 0 && grid[row + 1][col] == 'O' && grid[row + 2][col] == 'O')
      || (row == 1 && grid[row - 1][col] == 'O' && grid[row + 1][col] == 'O')
      || (row == 2 && grid[row - 2][col] == 'O' && grid[row - 1][col] == 'O')
      || (col == 0 && row == 0 && grid[1][1] == 'O' && grid[2][2] == 'O')
      || (col == 1 && row == 1 && grid[0][0] == 'O' && grid[2][2] == 'O')
      || (col == 2 && row == 2 && grid[0][0] == 'O' && grid[1][1] == 'O')
      || (row == 0 && col == 2 && grid[1][1] == 'O' && grid[2][0] == 'O')
      || (row == 1 && col == 1 && grid[2][0] == 'O' && grid[0][2] == 'O')
      || (row == 2 && col == 0 && grid[1][1] == 'O' && grid[0][2] == 'O'))
    {
      score = 10;
    }

    // next optimal move = block user from winning
    else if ((col == 0 && grid[row][col + 1] == 'X'
      && grid[row][col + 2] == 'X')
      || (col == 1 && grid[row][col - 1] == 'X' && grid[row][col + 1] == 'X')
      || (col == 2 && grid[row][col - 2] == 'X' && grid[row][col - 1] == 'X')
      || (row == 0 && grid[row + 1][col] == 'X' && grid[row + 2][col] == 'X')
      || (row == 1 && grid[row - 1][col] == 'X' && grid[row + 1][col] == 'X')
      || (row == 2 && grid[row - 2][col] == 'X' && grid[row - 1][col] == 'X')
      || (col == 0 && row == 0 && grid[1][1] == 'X' && grid[2][2] == 'X')
      || (col == 1 && row == 1 && grid[0][0] == 'X' && grid[2][2] == 'X')
      || (col == 2 && row == 2 && grid[0][0] == 'X' && grid[1][1] == 'X')
      || (row == 0 && col == 2 && grid[1][1] == 'X' && grid[2][0] == 'X')
      || (row == 1 && col == 1 && grid[2][0] == 'X' && grid[0][2] == 'X')
      || (row == 2 && col == 0 && grid[1][1] == 'X' && grid[0][2] == 'X'))
    {
      score = 5;
    }

    return score;
  }

  public boolean coordinateIsOpen(int x, int y)
  {
    if (x < 0 || x > 3 || y < 0 || y > 3)
      return false;
    return grid[x][y] != 'X' && grid[x][y] != 'O';
  }

  public void resetBoard()
  {
    for (int row = 0; row < grid.length; row++)
    {
      for (int col = 0; col < grid.length; col++)
      {
        grid[row][col] = '-';
      }
    }
  }

  public void showBoard()
  {
    for (int row = 0; row < grid.length; row++)
    {
      for (int col = 0; col < grid.length; col++)
      {
        System.out.print(grid[row][col]);
        if (col != grid.length - 1)
        {
          System.out.print("|");
        }
      }
      System.out.println();
    }
  }

}
