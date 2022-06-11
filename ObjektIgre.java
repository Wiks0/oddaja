package proj.sprite;

import java.awt.Image;

public class ObjektIgre {

    int x;
    int y;
    int premik;
    private boolean prikazan;
    private Image image;
    private boolean dying;

    public ObjektIgre() {

        prikazan = true;
    }

    public void die() {

        prikazan = false;
    }

    public boolean jePrikazan() {

        return prikazan;
    }

    protected void setPrikazan(boolean prikazan) {

        this.prikazan = prikazan;
    }

   public void setImage(Image image) {

        this.image = image;
    }

   public Image getImage() {

        return image;
    }
   public void setDying(boolean dying) {

       this.dying = dying;
   }

   public boolean isDying() {

       return this.dying;
   }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }

}
