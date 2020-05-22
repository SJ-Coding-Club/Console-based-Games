
// https://www.archimedes-lab.org/game_nim/nim.html#
// https://www.archimedes-lab.org/How_to_Solve/Win_at_Nim.html
import java.util.Scanner;

public class Game
{

  public static void main(String[] args)
  {
    System.out.println(
      "                      ~ nim ~\n=======================================================\n"
        + "Your goal is to make your opponent take the last match.\n=======================================================\n");
    game();

  }

  public static void game()
  {
    Scanner s = new Scanner(System.in);
    Board board = new Board();
    int turn = 0;
    while (board.getWinner(turn) == 0)
    {
      if (turn % 2 == 0)
      {
        board.showBoard();
      }

      if (turn % 2 == 0)
      {
        System.out.println("Which row would you like to take from?");
        int row = s.nextInt();
        System.out.println("How many matches would you like to remove?");
        int matches = s.nextInt();
        Move playerMove = new Move(row, matches);
        board.makeMove(playerMove);
      }
      else
      {
        Move computerMove = board.findBestMove();
        System.out.println("Computer took " + computerMove.getSticksToRemove()
          + " from Row " + computerMove.getRow());
        board.makeMove(computerMove);
      }
      turn++;
    }
    turn++;
    int winner = board.getWinner(turn);
    if (winner == 1)
    {
      System.out.println("Player 1 Wins.");
    }
    else
    {
      System.out.println("Computer wins.");
    }
    System.out.println("Enter 1 to play again, enter any other key to quit.");
    String choice = s.next();
    if (choice.equals("1"))
    {
      game();
    }
    return;
  }
}
