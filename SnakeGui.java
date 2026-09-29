import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Gui
 */
public class SnakeGui extends JFrame {
  private JPanel drawPanel;
  private final int CELL_SIZE = 20;     
  private JLabel lbScore1 = new JLabel();
  private JLabel lbScore2 = new JLabel();
  private JLabel lbInfo = new JLabel();
  private JButton btnRestart = new JButton();
  private Timer timer;

  // Farben 
  private Color farbeHintergrund = new Color(30, 30, 30);
  private Color farbeRaster = new Color(60, 60, 60);
  private Color farbeFutter = Color.RED;
  private Color farbeSnake1 = new Color(60, 160, 220);   // hellblau
  private Color farbeKopf1 = new Color(120, 200, 255);
  private Color farbeSnake2 = new Color(80, 190, 80);    // gruen
  private Color farbeKopf2 = new Color(140, 230, 140);

  // Verbindung zur Logik
  private SnakeSteuerung steuerung = new SnakeSteuerung();
  

  // Konstruktor
  public SnakeGui() {
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    int feldBreite = steuerung.getGridWidth() * CELL_SIZE;  
    int feldHoehe = steuerung.getGridHeight() * CELL_SIZE;  

    int frameWidth = feldBreite + 40;
    int frameHeight = feldHoehe + 130;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((d.width - frameWidth) / 2, (d.height - frameHeight) / 2);
    setTitle("Snake - 2 Spieler");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);

    

    // Punkte Spieler 1 
    lbScore1.setBounds(20, 8, 200, 24);
    lbScore1.setFont(new Font("Dialog", Font.BOLD, 13));
    lbScore1.setText("Spieler 1: 0");
    cp.add(lbScore1);

    // Punkte Spieler 2 
    lbScore2.setBounds(feldBreite - 160, 8, 180, 24);
    lbScore2.setFont(new Font("Dialog", Font.BOLD, 13));
    lbScore2.setHorizontalAlignment(SwingConstants.RIGHT);
    lbScore2.setText("Spieler 2: 0");
    cp.add(lbScore2);

    // wie man steuert
    lbInfo.setBounds(20, 30, feldBreite, 18);
    lbInfo.setFont(new Font("Dialog", Font.PLAIN, 11));
    lbInfo.setHorizontalAlignment(SwingConstants.CENTER);
    lbInfo.setText("Spieler 1: W A S D    -    Spieler 2: Pfeiltasten");
    cp.add(lbInfo);

    // spielfeld
    drawPanel = new JPanel() {
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        zeichneSpielfeld(g);
      }
    };
    drawPanel.setBounds(20, 52, feldBreite, feldHoehe);
    cp.add(drawPanel);

    // Neustart Button 
    btnRestart.setBounds((frameWidth - 120) / 2, 52 + feldHoehe / 2 + 15, 120, 28);
    btnRestart.setFont(new Font("Dialog", Font.BOLD, 12));
    btnRestart.setText("Neustart");
    btnRestart.setFocusable(false);
    btnRestart.setVisible(false);
    btnRestart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnRestart_ActionPerformed(evt);
      }
    });
    cp.add(btnRestart);
    

    setVisible(true);

    // steuerung füe die schlangen
    setFocusable(true);
    requestFocusInWindow();
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          //  WASD bei der ersten
          case KeyEvent.VK_W: steuerung.setRichtung1("UP"); break;
          case KeyEvent.VK_S: steuerung.setRichtung1("DOWN"); break;
          case KeyEvent.VK_A: steuerung.setRichtung1("LEFT"); break;
          case KeyEvent.VK_D: steuerung.setRichtung1("RIGHT"); break;
          //Pfeile für die zweite
          case KeyEvent.VK_UP: steuerung.setRichtung2("UP"); break;
          case KeyEvent.VK_DOWN: steuerung.setRichtung2("DOWN"); break;
          case KeyEvent.VK_LEFT: steuerung.setRichtung2("LEFT"); break;
          case KeyEvent.VK_RIGHT: steuerung.setRichtung2("RIGHT"); break;
        }
      }
    });

    // Timer
    timer = new Timer(steuerung.getSpeed(), new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        spielTakt();
      }
    });
    timer.start();
  } // Ende Konstruktor

  // Takt vom Spiel
  private void spielTakt() {
    steuerung.schritt();
    timer.setDelay(steuerung.getSpeed());
    lbScore1.setText("Spieler 1: " + steuerung.getScore1());
    lbScore2.setText("Spieler 2: " + steuerung.getScore2());
    drawPanel.repaint();

    if (steuerung.istGameOver()) {
      timer.stop();
      btnRestart.setVisible(true);
    }
  }

  // Spielfeld zeichnen
  private void zeichneSpielfeld(Graphics g) {
    int cell = CELL_SIZE;
    int breite = steuerung.getGridWidth();
    int hoehe = steuerung.getGridHeight();

    // Hintergrund
    g.setColor(farbeHintergrund);
    g.fillRect(0, 0, breite * cell, hoehe * cell);

    // Raster
    g.setColor(farbeRaster);
    for (int x = 0; x < breite; x++) {
      for (int y = 0; y < hoehe; y++) {
        g.drawRect(x * cell, y * cell, cell, cell);
      }
    }

    //essen
    g.setColor(farbeFutter);
    g.fillOval(steuerung.getFoodX() * cell + 2, steuerung.getFoodY() * cell + 2, cell - 4, cell - 4);

    // beide Schlangen
    zeichneSchlange(g, steuerung.getBody1(), farbeSnake1, farbeKopf1);
    zeichneSchlange(g, steuerung.getBody2(), farbeSnake2, farbeKopf2);

    // Game Over Anzeige
    if (steuerung.istGameOver()) {
      g.setColor(Color.WHITE);
      g.setFont(new Font("Dialog", Font.BOLD, 22));
      String text;
      int gewinner = steuerung.getGewinner();
      if (gewinner == 1) {
        text = "Spieler 1 gewinnt!";
      } else if (gewinner == 2) {
        text = "Spieler 2 gewinnt!";
      } else {
        text = "Unentschieden!";
      }
      int textBreite = g.getFontMetrics().stringWidth(text);
      g.drawString(text, (breite * cell - textBreite) / 2, hoehe * cell / 2 - 20);
    }
  }

  // eine Schlange zeichnen
  private void zeichneSchlange(Graphics g, java.util.ArrayList<SnakeSegment> body, Color koerper, Color kopf) {
    int cell = CELL_SIZE;
    for (int i = 0; i < body.size(); i++) {
      SnakeSegment segment = body.get(i);
      if (i == 0) {
        g.setColor(kopf);
      } else {
        g.setColor(koerper);
      }
      g.fillRect(segment.getx() * cell + 1, segment.gety() * cell + 1, cell - 2, cell - 2);
    }
  }

  //Neustart Button
  private void btnRestart_ActionPerformed(ActionEvent evt) {
    steuerung.neustart();
    btnRestart.setVisible(false);
    lbScore1.setText("Spieler 1: 0");
    lbScore2.setText("Spieler 2: 0");
    requestFocusInWindow();
    timer.start();
  }

  
  public static void main(String[] args) {
    new SnakeGui();
  }
} // Ende der Klasse
