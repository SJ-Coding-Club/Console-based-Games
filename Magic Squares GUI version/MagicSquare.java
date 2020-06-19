package MagicSquares;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class MagicSquare
{

  private JFrame frame;
  private JComboBox difficultySelector;
  private JLabel magicSumLabel;
  private JTextField[][] square = new JTextField[3][3];
  private BoardEngine board;

  /**
   * Launch the application.
   */
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          MagicSquare window = new MagicSquare();
          window.frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public MagicSquare()
  {
    initializeFrame();
    populateFrame();
    initializeSquare();
  }

  // initialize frame
  private void initializeFrame()
  {
    frame = new JFrame();
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
      MagicSquare.class.getResource("/MagicSquares/images/icon.jpg")));
    frame.getContentPane().setForeground(Color.WHITE);
    frame.setTitle("Magic Square Game");
    frame.getContentPane().setBackground(new Color(245, 222, 179));
    frame.setBounds(100, 100, 311, 305);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    frame.setResizable(false);

    /*
     * Makes look and feel of UI relative to that of the user's system. So on
     * Windows it would look like Windows UI
     */
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void populateFrame()
  {
    JLabel gameTitle = new JLabel("Magic Square");
    gameTitle.setFont(new Font("Cambria", Font.PLAIN, 22));
    gameTitle.setBackground(Color.WHITE);
    gameTitle.setBounds(88, 11, 136, 46);
    frame.getContentPane().add(gameTitle);

    JLabel magicSumTextLabel = new JLabel("Magic Sum = ");
    magicSumTextLabel.setFont(new Font("Cambria", Font.PLAIN, 16));
    magicSumTextLabel.setBounds(92, 213, 107, 24);
    frame.getContentPane().add(magicSumTextLabel);

    difficultySelector = new JComboBox();
    difficultySelector.setFont(new Font("Tahoma", Font.PLAIN, 11));
    difficultySelector.setToolTipText("Difficulty");
    difficultySelector.setBounds(253, 198, 32, 22);
    difficultySelector.addItem(1);
    difficultySelector.addItem(2);
    difficultySelector.addItem(3);
    frame.getContentPane().add(difficultySelector);

    JButton resetButton = new JButton("Reset");
    resetButton.setToolTipText("Reset square to original");
    resetButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
    resetButton.setFocusPainted(false);
    resetButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        resetBoard();
      }
    });
    resetButton.setBounds(10, 231, 61, 24);
    frame.getContentPane().add(resetButton);

    JButton newGameButton = new JButton("New");
    newGameButton.setToolTipText("Generate new square");
    newGameButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
    newGameButton.setFocusPainted(false);
    newGameButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        initializeNewGame();
      }
    });
    newGameButton.setBounds(224, 232, 61, 23);
    frame.getContentPane().add(newGameButton);

    JButton checkAnswerButton = new JButton("Check");
    checkAnswerButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
    checkAnswerButton.setToolTipText("Submit your current board");
    checkAnswerButton.setBounds(10, 198, 61, 23);
    checkAnswerButton.setFocusPainted(false);
    checkAnswerButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        checkAnswer();
      }
    });
    frame.getContentPane().add(checkAnswerButton);

    magicSumLabel = new JLabel("");
    magicSumLabel.setForeground(new Color(0, 0, 255));
    magicSumLabel.setFont(new Font("Cambria", Font.PLAIN, 16));
    magicSumLabel.setBounds(184, 217, 46, 14);
    frame.getContentPane().add(magicSumLabel);
  }

  // initialize square grid
  private void initializeSquare()
  {
    square[0][0] = new JTextField();
    square[0][0].setColumns(10);
    square[0][0].setBounds(95, 83, 35, 35);
    frame.getContentPane().add(square[0][0]);

    square[0][1] = new JTextField();
    square[0][1].setColumns(10);
    square[0][1].setBounds(130, 83, 35, 35);
    frame.getContentPane().add(square[0][1]);

    square[0][2] = new JTextField();
    square[0][2].setColumns(10);
    square[0][2].setBounds(165, 83, 35, 35);
    frame.getContentPane().add(square[0][2]);

    square[1][0] = new JTextField();
    square[1][0].setColumns(10);
    square[1][0].setBounds(95, 118, 35, 35);
    frame.getContentPane().add(square[1][0]);

    square[1][1] = new JTextField();
    square[1][1].setBounds(130, 118, 35, 35);
    frame.getContentPane().add(square[1][1]);
    square[1][1].setColumns(10);

    square[1][2] = new JTextField();
    square[1][2].setColumns(10);
    square[1][2].setBounds(165, 118, 35, 35);
    frame.getContentPane().add(square[1][2]);

    square[2][0] = new JTextField();
    square[2][0].setColumns(10);
    square[2][0].setBounds(95, 153, 35, 35);
    frame.getContentPane().add(square[2][0]);

    square[2][1] = new JTextField();
    square[2][1].setColumns(10);
    square[2][1].setBounds(130, 153, 35, 35);
    frame.getContentPane().add(square[2][1]);

    square[2][2] = new JTextField();
    square[2][2].setColumns(10);
    square[2][2].setBounds(165, 153, 35, 35);
    frame.getContentPane().add(square[2][2]);
  }

  /*
   * Activated when 'New' is clicked.
   */
  public void initializeNewGame()
  {
    int difficulty = (int) difficultySelector.getSelectedItem();
    board = new BoardEngine(difficulty);
    for (int row = 0; row < square.length; row++)
    {
      for (int col = 0; col < square[row].length; col++)
      {
        if (board.getUserBoard()[row][col] != 0)
          square[row][col].setText(board.getUserBoard()[row][col] + "");
        else
          square[row][col].setText("");
      }
    }
    magicSumLabel.setText(board.getMagicSum() + "");
  }

  // reset board to original generation
  public void resetBoard()
  {
    try
    {
      for (int row = 0; row < square.length; row++)
      {
        for (int col = 0; col < square[row].length; col++)
        {
          if (board.getUserBoard()[row][col] != 0)
            square[row][col].setText(board.getUserBoard()[row][col] + "");
          else
            square[row][col].setText("");
        }
      }
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(null,
        "Cannot reset a board that has not been generated.");
    }
  }

  // check if the answer is correct.
  public void checkAnswer()
  {
    try
    {
      for (int row = 0; row < square.length; row++)
      {
        for (int col = 0; col < square[row].length; col++)
        {
          board.getTempBoard()[row][col] =
            Integer.parseInt(square[row][col].getText());
        }
      }
      boolean isCorrect = board.validateBoard();
      if (isCorrect)
      {
        JOptionPane.showMessageDialog(null,
          "Congratulations! You have solved the Magic Square! Click New to play again.");
      }
      else
      {
        JOptionPane.showMessageDialog(null,
          "Sorry, incorrect. Click Reset to undo your answers.");
      }
    }
    catch (Exception e)
    {
      if (board == null)
      {
        JOptionPane.showMessageDialog(null, "Please click New to begin.");
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Please fill in all spaces.");
      }
    }
  }
}