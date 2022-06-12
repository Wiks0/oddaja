package spaceInvaders;


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
    //Nastavimo atribute, ki jih potrebujemo znotraj razreda
    /**
	 * 
	 */
    private static final long serialVersionUID = 2227341085817477927L;
    private Dimension dim;
    private List<Vesoljcek> vesoljcki;
    private Igralec igralec;
    private Strel strel;
    private int tocke;
    private int smer = -2;
    private int stUnicenih = 0;
    private int sirinaVesoljcka = 16;
    private int visinaVesoljcka = 16;
  
    private boolean seIgra = true; //Sledi ali je igra aktivna in kdaj jo ustaviti
    private String explIkona = "src/images/explosion.png";
    private String tekst = "Game Over";

    private Timer timer;
    public Board() {
    	initBoard();
        igraInit();}
    private void initBoard() {

        addKeyListener(new Kljuc());
        setFocusable(true);
        dim = new Dimension(Nastavitve.sirinaOkna, Nastavitve.visinaOkna);
        setBackground(Color.BLACK);
        timer = new Timer(Nastavitve.zamik, new KrogIgre());
        timer.start();

        igraInit();
    }


    private void igraInit() {

        vesoljcki = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {

                var vesoljcek = new Vesoljcek(Nastavitve.vesoljcekX + 30 * j,
                        Nastavitve.vesoljcekY + 30 * i);
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

                g.drawImage(ii.getImage(), vesoljcek.getX(), vesoljcek.getY(), sirinaVesoljcka, visinaVesoljcka, this);
            }

            if (vesoljcek.vUnicenju()) {

                vesoljcek.die();
            }
        }
    }

    private void narisiIgralca(Graphics g) {
    	  var igralecIkona = "src/images/rsz_heroship.png";
          var ikona = new ImageIcon(igralecIkona);

        if (igralec.jePrikazan()) {

            g.drawImage(ikona.getImage(), igralec.getX(), igralec.getY(), 20, Nastavitve.visinaIgralca, this);
        }

        if (igralec.vUnicenju()) {

            igralec.die();
            seIgra = false;
        }
    }

    private void narisiStrel(Graphics g) {

        if (strel.jePrikazan()) {

            g.drawImage(strel.getImage(), strel.getX(), strel.getY(), this);
        }
    }

    private void narisiBombo(Graphics g) {

        for (Vesoljcek a : vesoljcki) {

            Vesoljcek.Bomba b = a.getBomba();

            if (!b.jeUnicen()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        risiObjekte(g);
    }

    private void risiObjekte(Graphics g) {
    	final Color background = new Color(35,31,32);
        g.setColor(background);
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(Color.BLUE);

        if (seIgra) {

        	g.fillRect(0, 600, Nastavitve.sirinaOkna, 600);
            g.setColor(Color.GREEN);
            g.drawString("Score: " + Integer.toString(this.tocke), Nastavitve.sirinaOkna - 100,50);
            narisiVesoljcka(g);
            narisiIgralca(g);
            narisiStrel(g);
            narisiBombo(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
           }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver (Graphics g) {
    	//Nastavimo zaslon, ki se prikaze, ko je igre konec
    	g.setColor(Color.black);
        g.fillRect(0, 0, Nastavitve.sirinaOkna, Nastavitve.visinaOkna);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Nastavitve.sirinaOkna/2 - 30, Nastavitve.sirinaOkna - 100, 100);
        g.setColor(Color.white);
        g.drawRect(50, Nastavitve.sirinaOkna / 2 - 30, Nastavitve.sirinaOkna - 100, 100);

        var small = new Font("Helvetica", Font.BOLD, 14);

        g.setColor(Color.white);
        g.setFont(small);
        var dolzina = this.getFontMetrics(small);
        g.drawString(tekst, (Nastavitve.sirinaOkna - dolzina.stringWidth(tekst)) / 2, Nastavitve.sirinaOkna / 2);
        g.drawString("Score: " + Integer.toString(this.tocke), (Nastavitve.sirinaOkna - dolzina.stringWidth(tekst)) / 2, Nastavitve.sirinaOkna / 2 + 50); 
        
        
    }
    //Metoda za posodabljanje objektov skozi igranje
    private void posodobi() {
    	if (stUnicenih == Nastavitve.steviloVesoljckov) {
    		seIgra = false;
            timer.stop();
            tekst = "Game won!";
        }

        igralec.prestaviIgralca();

        if (strel.jePrikazan()) {

            int strelX = strel.getX();
            int strelY = strel.getY();

            for (Vesoljcek vesoljcek : vesoljcki) {

                int vesoljcekX = vesoljcek.getX();
                int vesoljcekY = vesoljcek.getY();
                //Obravnavamo primer, ko s strelom zadanemo vesoljcka
                if (vesoljcek.jePrikazan() && strel.jePrikazan()) {
                   if (strelX >= (vesoljcekX) && strelX <= (vesoljcekX + sirinaVesoljcka) && strelY >= (vesoljcekY)&& strelY <= (vesoljcekY + visinaVesoljcka))
                             {

                        var eksplozija = new ImageIcon(explIkona);
                        vesoljcek.setIkona(eksplozija.getImage());
                        vesoljcek.setvUnicenju(true);
                        stUnicenih++;
                        tocke += 50;
                        strel.die();
                    }}}
            //Premik strela. Ce je y<0 se uniči
            int y = strel.getY();
            y -= 5; //Hitrost s katero se v y smer giblje nas metek

            if (y < 0) {
                strel.die();
            } else {
                strel.setY(y);
            } }

        for (Vesoljcek vesoljcek : vesoljcki) {

            int x = vesoljcek.getX();
            //Upravljanje s premiki vesoljcka. Ce pridejo do roba se predznak gibanja obrne in se za Nastavitve.premikY pomakne proti dnu
            if (x >= Nastavitve.sirinaOkna - 30 && smer != -2) {

                smer = -2;

                Iterator<Vesoljcek> v1 = vesoljcki.iterator();

                while (v1.hasNext()) {

                    Vesoljcek v2 = v1.next();
                    v2.setY(v2.getY() + Nastavitve.premikY);
                }
            }

            if (x <= 5 && smer != 2) {

                smer = 2;
                Iterator<Vesoljcek> v3 = vesoljcki.iterator();

                while (v3.hasNext()) {

                    Vesoljcek a = v3.next();
                    a.setY(a.getY() + Nastavitve.premikY);
                }}}

        Iterator<Vesoljcek> v4 = vesoljcki.iterator();

        while (v4.hasNext()) {

            Vesoljcek vesoljcek = v4.next();

            if (vesoljcek.jePrikazan()) {

                int y = vesoljcek.getY();
                //Pogoj, ko pridejo vesoljcki do dna
                if (y > 600 - visinaVesoljcka) {
                    seIgra = false;
                    tekst = "Invasion!";
                }

                vesoljcek.prestaviVesoljcka(smer);
            }}
        
        //S pomocjo random() in nextInt() generiramo metke vesoljckov
        var nakljucnoSt = new Random();

        for (Vesoljcek vesoljcek : vesoljcki) {

            int poskus = nakljucnoSt.nextInt(15);
            Vesoljcek.Bomba bomba = vesoljcek.getBomba();
            
            //Nastavimo pogoje pri katerih vesoljcek izstreli bombo
            if (poskus == 1 && vesoljcek.jePrikazan() && bomba.jeUnicen()) {

                bomba.setUnicen(false);
                bomba.setX(vesoljcek.getX());
                bomba.setY(vesoljcek.getY());
            }

            int bombaX = bomba.getX();
            int bombaY = bomba.getY();
            int playerX = igralec.getX();
            int playerY = igralec.getY();

            //Primer ko bomba zadane in posledicno unici igralca
            if (igralec.jePrikazan() && (bomba.jeUnicen() == false)) {

                if (bombaX >= (playerX) && bombaX <= (playerX + 20) && bombaY >= (playerY) && bombaY <= (playerY + Nastavitve.visinaIgralca)) {

                    var ii = new ImageIcon(explIkona);
                    igralec.setIkona(ii.getImage());
                    igralec.setvUnicenju(true);
                    bomba.setUnicen(true);
                }}

            if (bomba.jeUnicen() == false) {

                bomba.setY(bomba.getY() + 1); //Kontroliramo hitrost bomb
                //Ko bomba pride do tal se unici
                if (bomba.getY() >= 595) {

                    bomba.setUnicen(true);
                }}}}

    private void zacniKrogIgre() {
    	//Posodobimo in ponovno izrisemo objekte na nase okno
        posodobi();
        repaint();
    }

    private class KrogIgre implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent k) {

            zacniKrogIgre();
        }
    }

    private class Kljuc extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            igralec.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent k) {
        	igralec.keyPressed(k);
            int x = igralec.getX();
            int y = igralec.getY();

            int kljuc = k.getKeyCode();
            
            //Omogoči, da moramo počakati, da prvi strel izgine iz zaslona preden izstrelimo naslednjega
            if (kljuc == KeyEvent.VK_SPACE) {
            	if ((seIgra) & (strel.jePrikazan() == false)){
                        strel = new Strel(x, y);
                    }}}}}
