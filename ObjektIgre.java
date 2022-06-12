package spaceInvaders;

import java.awt.Image;

public class ObjektIgre {

    int x;
    int y;
    int premik;
    private boolean prikazan;
    private Image ikona;
    private boolean unicen;

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

   public void setIkona(Image ikona) {

        this.ikona = ikona;
    }

   public Image getImage() {

        return ikona;
    }
   public void setvUnicenju(boolean unicen) {

       this.unicen = unicen;
   }

   public boolean vUnicenju() {

       return this.unicen;
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
