package spaceInvaders;

import javax.swing.ImageIcon;

public class Vesoljcek extends ObjektIgre {

    private Bomba bomba;
    public Vesoljcek(int x, int y) {

        initVesoljcek(x, y);
    }
    
    private void initVesoljcek(int x, int y) {

        this.x = x;
        this.y = y;

        bomba = new Bomba(x, y);
    }

    public void prestaviVesoljcka(int smer) {

        this.x += smer;
    }

    public Bomba getBomba() {

        return bomba;
    }

    public class Bomba extends ObjektIgre {

        private boolean unicen;
        public Bomba(int x, int y) {
        	initBomba(x, y);
        }

        private void initBomba(int x, int y) {
        	setUnicen(true);
        	this.x = x;
            this.y = y;

            var bombaIkona = "src/images/bomb.png";
            var bomba = new ImageIcon(bombaIkona);
            setIkona(bomba.getImage());
        }

        public void setUnicen(boolean unicen) {

            this.unicen= unicen;
        }

        public boolean jeUnicen() {

            return unicen;
        }
    }
}
