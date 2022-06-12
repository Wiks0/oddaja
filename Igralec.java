package spaceInvaders;

import java.awt.event.KeyEvent;


public class Igralec extends ObjektIgre {

    public Igralec() {

        ustvariIgralca();
    }

    private void ustvariIgralca() {
    	//Dolocimo zacetno pozicijo igralca
        int zacetniX = 300;
        setX(zacetniX);
        int zacetniY = 600 - Nastavitve.visinaIgralca;
        setY(zacetniY);
    }

    public void prestaviIgralca() {

        x += premik;

        if (x <= 3) {

            x = 3;
        }
        //Omogocimo, da igralec ostane znotraj okna
        if (x >= Nastavitve.sirinaOkna - 40) {

            x = Nastavitve.sirinaOkna - 40;
        } }

    public void keyPressed(KeyEvent k) {
    	//Vpravljanje igralca s puscicami
        int tipka = k.getKeyCode();
        switch(tipka) {
        case KeyEvent.VK_LEFT:
        	premik = -5;
        	break;
        case KeyEvent.VK_RIGHT:
        	premik = 5;
        	break;
        }
    }

    public void keyReleased(KeyEvent k) {

        int tipka = k.getKeyCode();

        if (tipka == KeyEvent.VK_LEFT | tipka == KeyEvent.VK_RIGHT ) {

            premik = 0;
        }

    }
}
