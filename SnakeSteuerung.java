import java.util.ArrayList;

/**
 * Logik 
 */
public class SnakeSteuerung {
  private final int GRID_WIDTH = 20;
  private final int GRID_HEIGHT = 16;

  private Snake snake1;
  private Snake snake2;
  private String richtung1 = "RIGHT";
  private String richtung2 = "LEFT";

  private int foodX;
  private int foodY;
  private int speed = 220;          // länge vom spiel schritt
  private boolean gameOver = false;
  private int gewinner = 0;         // 0=läuft, 1=Spieler1, 2=Spieler2, 3=unentschieden

  // Konstruktor
  public SnakeSteuerung() {
    snake1 = new Snake(2, GRID_HEIGHT / 2);
    snake2 = new Snake(GRID_WIDTH - 3, GRID_HEIGHT / 2);
    makeFood();
  }

  //   spielschritt 
  public void schritt() {
    if (gameOver) {
      return;
    }

    // Schlangen bewegen
    snake1.moveSnake(richtung1);
    snake2.moveSnake(richtung2);

    // fressen prüfen 
    pruefeFutter(snake1);
    pruefeFutter(snake2);

    // treffer prüfen
    boolean tot1 = istTot(snake1, snake2);
    boolean tot2 = istTot(snake2, snake1);

    if (tot1 || tot2) {
      gameOver = true;
      if (tot1 && tot2) {
        gewinner = 3;            // beide gleichzeitig
      } else if (tot1) {
        gewinner = 2;            // Schlange 1 tot 
      } else {
        gewinner = 1;            // Schlange 2 tot
      }
    }
  }

  // hat sies gegessen wnn nicht schwanz ab machen
  private void pruefeFutter(Snake s) {
    if (s.getHeadX() == foodX && s.getHeadY() == foodY) {
      s.addPunkt();
      if (speed > 80) {
        speed = speed - 4;       // spiel schneller machen
      }
      makeFood();
    } else {
      s.removeTail();
    }
  }

  // ist sie tot 
  private boolean istTot(Snake s, Snake andere) {
    if (s.getHeadX() < 0 || s.getHeadX() >= GRID_WIDTH
        || s.getHeadY() < 0 || s.getHeadY() >= GRID_HEIGHT) {
      return true;             // Wand
    }
    if (s.hitSelf()) {
      return true;             // sich selbst
    }
    if (s.hitBody(andere.getBody())) {
      return true;             // andere Schlange
    }
    return false;
  }

  //  essen an freier Stelle machen 
  private void makeFood() {
    boolean besetzt;
    do {
      foodX = (int) (Math.random() * GRID_WIDTH);
      foodY = (int) (Math.random() * GRID_HEIGHT);
      besetzt = belegt(foodX, foodY, snake1) || belegt(foodX, foodY, snake2);
    } while (besetzt);
  }

  // ist an der stelle vom essen schon ne schlange
  private boolean belegt(int x, int y, Snake s) {
    for (SnakeSegment segment : s.getBody()) {
      if (segment.getx() == x && segment.gety() == y) {
        return true;
      }
    }
    return false;
  }

  //  Richtung geben und schauen das sie sich nicht um 180 grad dreht 
  public void setRichtung1(String neu) {
    if (!istGegenrichtung(richtung1, neu)) {
      richtung1 = neu;
    }
  }

  public void setRichtung2(String neu) {
    if (!istGegenrichtung(richtung2, neu)) {
      richtung2 = neu;
    }
  }
  // gegenrichtung prüfen
  private boolean istGegenrichtung(String alt, String neu) {
    return (alt.equals("UP") && neu.equals("DOWN"))
        || (alt.equals("DOWN") && neu.equals("UP"))
        || (alt.equals("LEFT") && neu.equals("RIGHT"))
        || (alt.equals("RIGHT") && neu.equals("LEFT"));
  }

  // von vorne
  public void neustart() {
    snake1.restart(2, GRID_HEIGHT / 2);
    snake2.restart(GRID_WIDTH - 3, GRID_HEIGHT / 2);
    richtung1 = "RIGHT";
    richtung2 = "LEFT";
    speed = 220;
    gameOver = false;
    gewinner = 0;
    makeFood();
  }

  //  Getter 
  public boolean istGameOver() {
    return gameOver;
  }

  public int getGewinner() {
    return gewinner;
  }

  public ArrayList<SnakeSegment> getBody1() {
    return snake1.getBody();
  }

  public ArrayList<SnakeSegment> getBody2() {
    return snake2.getBody();
  }

  public int getScore1() {
    return snake1.getScore();
  }

  public int getScore2() {
    return snake2.getScore();
  }

  public int getFoodX() {
    return foodX;
  }

  public int getFoodY() {
    return foodY;
  }

  public int getSpeed() {
    return speed;
  }

  public int getGridWidth() {
    return GRID_WIDTH;
  }

  public int getGridHeight() {
    return GRID_HEIGHT;
  }
}
