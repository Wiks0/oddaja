package proj.sprite;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2227341085817477927L;
	private Dimension d;
    private List<Vesoljcek> vesoljcki;
    private Igralec igralec;
    private Strel strel;
    private int tocke;
    
    private int smer = -2;
    private int stVnicenih = 0;

    private boolean seIgra = true;
    private String explImg = "src/images/explosion.png";
    private String tekst = "Game Over";

    private Timer timer;


    public Board() {
    	initBoard();
        gameInit();}

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Nastavitve.sirinaOkna, Nastavitve.visinaOkna);
        setBackground(Color.BLACK);

        timer = new Timer(Nastavitve.DELAY, new GameCycle());
        timer.start();

        gameInit();
    }


    private void gameInit() {

        vesoljcki = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {

                var vesoljcek = new Vesoljcek(Nastavitve.ALIEN_INIT_X + 30 * j,
                        Nastavitve.ALIEN_INIT_Y + 30 * i);
                vesoljcki.add(vesoljcek);
            }
        }

        igralec = new Igralec();
        strel = new Strel();
    }

    private void narisiVesoljcka(Graphics g) {
        var alienImg = "src/images/rsz_invadership.png";
        var ii = new ImageIcon(alienImg);

        for (Vesoljcek vesoljcek : vesoljcki) {

            if (vesoljcek.jePrikazan()) {

                g.drawImage(ii.getImage(), vesoljcek.getX(), vesoljcek.getY(), Nastavitve.ALIEN_WIDTH, Nastavitve.ALIEN_HEIGHT, this);
            }

            if (vesoljcek.isDying()) {

                vesoljcek.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {
    	  var igralecIkona = "src/images/rsz_heroship.png";
          var ikona = new ImageIcon(igralecIkona);

        if (igralec.jePrikazan()) {

            g.drawImage(ikona.getImage(), igralec.getX(), igralec.getY(), Nastavitve.PLAYER_WIDTH, Nastavitve.visinaIgralca, this);
        }

        if (igralec.isDying()) {

            igralec.die();
            seIgra = false;
        }
    }

    private void drawStrel(Graphics g) {

        if (strel.jePrikazan()) {

            g.drawImage(strel.getImage(), strel.getX(), strel.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {

        for (Vesoljcek a : vesoljcki) {

            Vesoljcek.Bomba b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
    	final Color background = new Color(35,31,32);
        g.setColor(background);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.BLUE);

        if (seIgra) {

        	g.fillRect(0, 600, Nastavitve.sirinaOkna, 600);
            g.setColor(Color.GREEN);
            g.drawString("Score: " + Integer.toString(this.tocke), Nastavitve.sirinaOkna - 100,50);


            narisiVesoljcka(g);
            drawPlayer(g);
            drawStrel(g);
            drawBombing(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver (Graphics g) {
    	g.setColor(Color.black);
        g.fillRect(0, 0, Nastavitve.sirinaOkna, Nastavitve.visinaOkna);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Nastavitve.sirinaOkna/2 - 30, Nastavitve.sirinaOkna - 100, 100);
        g.setColor(Color.white);
        g.drawRect(50, Nastavitve.sirinaOkna / 2 - 30, Nastavitve.sirinaOkna - 100, 100);

        var small = new Font("Helvetica", Font.BOLD, 14);

        g.setColor(Color.white);
        g.setFont(small);
        var fontMetrics = this.getFontMetrics(small);
        g.drawString(tekst, (Nastavitve.sirinaOkna - fontMetrics.stringWidth(tekst)) / 2,
                Nastavitve.sirinaOkna / 2);
        g.drawString("Score: " + Integer.toString(this.tocke), (Nastavitve.sirinaOkna - fontMetrics.stringWidth(tekst)) / 2,
                Nastavitve.sirinaOkna / 2 + 50); 
        
        
    }

    private void posodobi() {

        if (stVnicenih == Nastavitve.steviloVesoljckov) {

            seIgra = false;
            timer.stop();
            tekst = "Game won!";
        }

        // player
        igralec.act();

        // shot
        if (strel.jePrikazan()) {

            int shotX = strel.getX();
            int shotY = strel.getY();

            for (Vesoljcek vesoljcek : vesoljcki) {

                int vesoljcekX = vesoljcek.getX();
                int vesoljcekY = vesoljcek.getY();

                if (vesoljcek.jePrikazan() && strel.jePrikazan()) {
                   if (shotX >= (vesoljcekX)
                           && shotX <= (vesoljcekX + Nastavitve.ALIEN_WIDTH)
                           && shotY >= (vesoljcekY)
                           && shotY <= (vesoljcekY + Nastavitve.ALIEN_HEIGHT)) {

                        var ii = new ImageIcon(explImg);
                        vesoljcek.setImage(ii.getImage());
                        vesoljcek.setDying(true);
                        stVnicenih++;
                        tocke += 50;
                        strel.die();
                    }
                }
            }

            int y = strel.getY();
            y -= 4;

            if (y < 0) {
                strel.die();
            } else {
                strel.setY(y);
            }
        }

        // aliens

        for (Vesoljcek vesoljcek : vesoljcki) {

            int x = vesoljcek.getX();

            if (x >= Nastavitve.sirinaOkna - 30 && smer != -2) {

                smer = -2;

                Iterator<Vesoljcek> i1 = vesoljcki.iterator();

                while (i1.hasNext()) {

                    Vesoljcek a2 = i1.next();
                    a2.setY(a2.getY() + Nastavitve.premikY);
                }
            }

            if (x <= 5 && smer != 2) {

                smer = 2;

                Iterator<Vesoljcek> i2 = vesoljcki.iterator();

                while (i2.hasNext()) {

                    Vesoljcek a = i2.next();
                    a.setY(a.getY() + Nastavitve.premikY);
                }
            }
        }

        Iterator<Vesoljcek> it = vesoljcki.iterator();

        while (it.hasNext()) {

            Vesoljcek vesoljcek = it.next();

            if (vesoljcek.jePrikazan()) {

                int y = vesoljcek.getY();

                if (y > 600 - Nastavitve.ALIEN_HEIGHT) {
                    seIgra = false;
                    tekst = "Invasion!";
                }

                vesoljcek.act(smer);
            }
        }

        // bombs
        var generator = new Random();

        for (Vesoljcek vesoljcek : vesoljcki) {

            int shot = generator.nextInt(15);
            Vesoljcek.Bomba bomb = vesoljcek.getBomb();

            if (shot == Nastavitve.CHANCE && vesoljcek.jePrikazan() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(vesoljcek.getX());
                bomb.setY(vesoljcek.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = igralec.getX();
            int playerY = igralec.getY();

            if (igralec.jePrikazan() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Nastavitve.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Nastavitve.visinaIgralca)) {

                    var ii = new ImageIcon(explImg);
                    igralec.setImage(ii.getImage());
                    igralec.setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= 600 - Nastavitve.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle() {

        posodobi();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            igralec.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            igralec.keyPressed(e);

            int x = igralec.getX();
            int y = igralec.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (seIgra) {

                    if (strel.jePrikazan() == false) {

                        strel = new Strel(x, y);
                    }
                }
            }
        }
    }}
