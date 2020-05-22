public class Move
{
  int row;
  int sticksToRemove;

  public Move(int row, int sticksToRemove)
  {
    this.row = row;
    this.sticksToRemove = sticksToRemove;
  }

  public int getRow()
  {
    return row;
  }

  public int getSticksToRemove()
  {
    return sticksToRemove;
  }
}