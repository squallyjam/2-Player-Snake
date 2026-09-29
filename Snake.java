import java.util.ArrayList;

/**
 * Daten der schlange
 */
public class Snake {
  private ArrayList<SnakeSegment> body;   
  private int headX;
  private int headY;
  private int score = 0;

  // Konstruktor: Startstelle 
  public Snake(int startX, int startY) {
    headX = startX;
    headY = startY;
    body = new ArrayList<>();
    body.add(new SnakeSegment(headX, headY));
  }

  //  Getter 
  public ArrayList<SnakeSegment> getBody() {
    return body;
  }

  public int getHeadX() {
    return headX;
  }

  public int getHeadY() {
    return headY;
  }

  public int getScore() {
    return score;
  }

  //  Kopf bewegen und nen kopf vorne dran machen 
  public void moveSnake(String direction) {
    switch (direction) {
      case "UP":
        headY--;
        break;
      case "DOWN":
        headY++;
        break;
      case "LEFT":
        headX--;
        break;
      case "RIGHT":
        headX++;
        break;
    }
    body.add(0, new SnakeSegment(headX, headY));
  }

  //  Schwanz entfernen 
  public void removeTail() {
    body.remove(body.size() - 1);
  }

  //  einen Punkt dazu 
  public void addPunkt() {
    score++;
  }

  //  überprüfen ob sich die Sclange selbst getroffen hat 
  public boolean hitSelf() {
    for (int i = 1; i < body.size(); i++) {
      SnakeSegment segment = body.get(i);
      if (segment.getx() == headX && segment.gety() == headY) {
        return true;
      }
    }
    return false;
  }

  //  hat die Schlange die andere getroffen
  public boolean hitBody(ArrayList<SnakeSegment> andereBody) {
    for (SnakeSegment segment : andereBody) {
      if (segment.getx() == headX && segment.gety() == headY) {
        return true;
      }
    }
    return false;
  }

  //  Schlange auf Start zurück 
  public void restart(int startX, int startY) {
    body.clear();
    headX = startX;
    headY = startY;
    body.add(new SnakeSegment(headX, headY));
    score = 0;
  }
}
