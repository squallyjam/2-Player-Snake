/**
 * Daten
 * Ein Segment ist quasi ein Block der Schlange.
 */
public class SnakeSegment {
  private int x;
  private int y;

  // Konstruktor
  public SnakeSegment(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getx() {
    return x;
  }

  public int gety() {
    return y;
  }
}
