package spaceInvaders;

import javax.swing.ImageIcon;

public class Strel extends ObjektIgre {
	public Strel() {}
    public Strel(int x, int y) {
    	initStrel(x, y);
    }

    private void initStrel(int x, int y) {

        var strelIkona = "src/images/shot.png";
        var ii = new ImageIcon(strelIkona);
        setIkona(ii.getImage());

      //Iz kje bodo leteli streli
        int odmikX = 10; 
        setX(x + odmikX);

        int odmikY = 1;
        setY(y - odmikY);
    }
}
