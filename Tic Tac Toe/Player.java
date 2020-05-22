public class Player
{
  private int score;
  private String name;

  public Player(String name)
  {
    this.name = name;
  }

  public Player()
  {
    name = "X";
  }

  public int getScore()
  {
    return score;
  }

  public void increaseScore()
  {
    score++;
  }

  public String getPlayerName()
  {
    return name;
  }

}
