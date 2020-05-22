import java.util.Arrays;
import java.util.Scanner;

public class ConsoleTicTacToe
{

  public static void main(String[] args)
  {
    playGame();
  }

  public static void playGame()
  {
    Scanner s = new Scanner(System.in);
    System.out.println("How many players? (2 or 1)");
    int playerCount = s.nextInt();

    while (playerCount != 2 && playerCount != 1)
    {
      System.out
        .println("Either play alone or with a friend.\nHow many players?");
      playerCount = s.nextInt();
    }
    s.nextLine();
    System.out.println("Enter Player 1 name: ");
    Player xPlayer = new Player(s.nextLine());
    Player oPlayer;

    if (playerCount == 2)
    {
      System.out.println("Enter Player 2 name: ");
      oPlayer = new Player(s.nextLine());
    }
    else
    {
      oPlayer = new Player("Computer");
    }

    Board board = new Board();
    int tieCount = 0;

    while (true)
    {
      int turn = 0;
      while (board.getWinner().equals("Tie") && turn < 9)
      {
        board.showBoard();
        System.out
          .println("\n" + (turn % 2 == 0 ? xPlayer.getPlayerName() + "'s Turn"
            : oPlayer.getPlayerName() + "'s Turn"));

        if (playerCount == 1 && turn % 2 == 1)
        {
          board.selectBestMove();
        }
        else
        {
          board.userMove(turn);
        }
        turn++;
      }
      board.showBoard();
      String winner = board.getWinner();
      if (winner.equals("X"))
      {
        System.out.println(oPlayer.getPlayerName() + " Wins");
        xPlayer.increaseScore();
      }
      else if (winner.equals("O"))
      {
        System.out.println(oPlayer.getPlayerName() + " Wins");
        oPlayer.increaseScore();
      }
      else
      {
        tieCount++;
        System.out.println("Tie");
      }
      board.resetBoard();
      System.out.println("------------------------");
      System.out.println("Score: |" + xPlayer.getPlayerName() + ": "
        + xPlayer.getScore() + "| " + oPlayer.getPlayerName() + ": "
        + oPlayer.getScore() + "| Ties: " + tieCount);
      System.out.println("------------------------");
      System.out.println("New game? Enter any key to play again, X to quit.");
      String choice = s.next();
      if (choice.equalsIgnoreCase("x"))
        break;
    }
  }
}
