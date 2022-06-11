package proj.sprite;

import javax.swing.ImageIcon;

public class Vesoljcek extends ObjektIgre {

    private Bomba bomba;

    public Vesoljcek(int x, int y) {

        initAlien(x, y);
    }

    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;

        bomba = new Bomba(x, y);

        var alienImg = "src/images/rsz_invadership.png";
        var ii = new ImageIcon(alienImg);

        setImage(ii.getImage());
    }

    public void act(int smer) {

        this.x += smer;
    }

    public Bomba getBomb() {

        return bomba;
    }

    public class Bomba extends ObjektIgre {

        private boolean destroyed;

        public Bomba(int x, int y) {

            initBomba(x, y);
        }

        private void initBomba(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = "src/images/bomb.png";
            var ii = new ImageIcon(bombImg);
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}
