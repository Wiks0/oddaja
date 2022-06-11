package proj.sprite;

import java.awt.event.KeyEvent;


public class Igralec extends ObjektIgre {
	private int width;

    public Igralec() {

        ustvariIgralca();
    }

    private void ustvariIgralca() {

        int zacetniX = 270;
        setX(zacetniX);
        int zacetniY = 600 - Nastavitve.visinaIgralca;
        setY(zacetniY);
    }

    public void act() {

        x += premik;

        if (x <= 3) {

            x = 3;
        }

        if (x >= Nastavitve.sirinaOkna - 2 * width) {

            x = Nastavitve.sirinaOkna - 2 * width;
        }
    }

    public void keyPressed(KeyEvent k) {

        int tipka = k.getKeyCode();

        if (tipka == KeyEvent.VK_LEFT) {

            premik = -5;
        }

        if (tipka == KeyEvent.VK_RIGHT) {

           premik = 5;
        }
    }

    public void keyReleased(KeyEvent k) {

        int tipka = k.getKeyCode();

        if (tipka == KeyEvent.VK_LEFT) {

            premik = 0;
        }

        if (tipka == KeyEvent.VK_RIGHT) {

            premik = 0;
        }
    }
}
