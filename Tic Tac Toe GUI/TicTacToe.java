import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class TicTacToe
{
  private static TicTacToe window;

  private JFrame choiceFrame;
  private JFrame gameFrame;
  private JButton[][] gridButtons;

  private InternalBoard internalBoard;
  private String playerMarker = "";

  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          window = new TicTacToe();
          window.choiceFrame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  public TicTacToe()
  {
    // change the UI look and feel to fit that of the system of the user
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    // Installed: Metal Nimbus CDE/Motif Windows Windows Classic
    catch (Exception e)
    {
    }

    initializeChoiceFrame();
    initializeChoiceFrameButtons();
    ////////////////////////////////
    initializeGameFrame();
    initializeGameButtons();
  }

  private void initializeChoiceFrame()
  {
    choiceFrame = new JFrame();
    choiceFrame.setBounds(100, 100, 445, 300);
    choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    choiceFrame.getContentPane().setLayout(null);
    choiceFrame.setResizable(false);
    choiceFrame.setTitle("Tic Tac Toe");
  }

  public void initializeChoiceFrameButtons()
  {
    JButton xButton = new JButton("X");
    xButton.setBounds(60, 60, 150, 150);
    xButton.setFont(new Font("Arial", Font.PLAIN, 50));
    xButton.setRolloverEnabled(true);
    xButton.setFocusable(false);

    xButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        playerMarker = "X";
        window.choiceFrame.setVisible(false);
        window.gameFrame.setVisible(true);
      }
    });
    choiceFrame.getContentPane().add(xButton);

    ///////////////////////////////////////////

    JButton oButton = new JButton("O");
    oButton.setBounds(230, 60, 150, 150);
    oButton.setFont(new Font("Arial", Font.PLAIN, 50));
    oButton.setFocusable(false);

    oButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        playerMarker = "O";
        window.choiceFrame.setVisible(false);
        window.gameFrame.setVisible(true);
      }
    });
    choiceFrame.getContentPane().add(oButton);
  }

  private void initializeGameFrame()
  {
    gameFrame = new JFrame();
    gameFrame.setBounds(100, 100, 450, 300);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.getContentPane().setLayout(null);
    gameFrame.setResizable(false);
    gameFrame.setTitle("Tic Tac Toe");
  }

  private void initializeGameButtons()
  {
    gridButtons = new JButton[3][3];
    for (int row = 0; row < 3; row++)
    {
      for (int column = 0; column < 3; column++)
      {
        gridButtons[row][column] = new JButton("");
        gridButtons[row][column].setBounds(140 + column * 50, 43 + row * 50, 50,
          50);
        gridButtons[row][column].setFont(new Font("Arial", Font.PLAIN, 20));
        gridButtons[row][column].setFocusPainted(false);
        gridButtons[row][column].addActionListener(gridClick(row, column));
        gameFrame.getContentPane().add(gridButtons[row][column]);
      }
    }
    JButton resetButton = new JButton("New");
    resetButton.setBounds(180, 225, 70, 25);
    resetButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        resetGame();
      }
    });
    gameFrame.getContentPane().add(resetButton);
  }

  public void resetGame()
  {
    for (int r = 0; r < 3; r++)
      for (int c = 0; c < 3; c++)
      {
        gridButtons[r][c].setText("");
        internalBoard.getInternalGrid()[r][c] = "";
      }
  }

  public ActionListener gridClick(final int row, final int column)
  {
    ActionListener gridClick = new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        String textInButton = gridButtons[row][column].getText();
        if (textInButton.equals(""))
        {
          gridButtons[row][column].setText(playerMarker);
          processGrid(gridButtons);
        }
      }
    };
    return gridClick;
  }

  public void processGrid(JButton[][] grid)
  {
    String[][] internalGrid = new String[3][3];
    for (int r = 0; r < 3; r++)
      for (int c = 0; c < 3; c++)
        internalGrid[r][c] = grid[r][c].getText();

    internalBoard = new InternalBoard(internalGrid, playerMarker);
    if (internalBoard.hasMovesLeft())
    {
      Move best = internalBoard.makeMove(internalBoard.getBestMove());
      gridButtons[best.getRow()][best.getColumn()].setText(getOpposingMarker());
      if (internalBoard.getScore() == 10)
        JOptionPane.showMessageDialog(null, "You lose.");
      else if (internalBoard.getScore() == -10)
        JOptionPane.showMessageDialog(null, "Congratulations! You have won!");
    }
  }

  public String getOpposingMarker()
  {
    if (playerMarker.equals("X"))
      return "O";
    return "X";
  }
}
