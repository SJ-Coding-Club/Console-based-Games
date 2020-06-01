import java.util.Scanner;

public class Main
{
  public static void main(String[] args)
  {
    Scanner s = new Scanner(System.in);
    while (true)
    {
      int difficulty = -1;
      while (difficulty < 1 || difficulty > 3)
      {
        System.out.println("Enter difficulty (1, 2, 3):");
        difficulty = s.nextInt();
      }
      Board board = new Board(difficulty);
      boolean boardIsCorrect = board.validateBoard();
      while (!boardIsCorrect)
      {
        board.copy2dArray(board.getTempBoard(), board.getUserBoard());
        while (board.hasSpaces(board.getUserBoard()))
        {
          board.showBoard();
          int row = -1;
          int column = -1;
          while (!board.moveIsValid(row, column))
          {
            System.out.println("Enter a valid row (0, 1, 2): ");
            row = s.nextInt();
            System.out.println("Enter a valid column (0, 1, 2): ");
            column = s.nextInt();
          }
          System.out.println("Enter the number you wish to place here: ");
          int number = s.nextInt();
          board.playerMove(row, column, number);
        }
        boardIsCorrect = board.validateBoard();
      }
      System.out.println(
        "=========================================\nCongratulations! You have solved the magic square!");
      board.showBoard();
      System.out.println("Enter 1 to exit. Enter anything else to play again.");
      if (s.next() == "1")
        break;
    }
  }
}