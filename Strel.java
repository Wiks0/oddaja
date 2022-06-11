package proj.sprite;

import javax.swing.ImageIcon;

public class Strel extends ObjektIgre {
	public Strel() {}
    public Strel(int x, int y) {
    	initStrel(x, y);
    }

    private void initStrel(int x, int y) {

        var strelIkona = "src/images/shot.png";
        var ii = new ImageIcon(strelIkona);
        setImage(ii.getImage());

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}
