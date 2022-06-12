package spaceInvaders;

import javax.swing.JFrame;
public class SpaceInvaders extends JFrame  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3666345753500236646L;

	public SpaceInvaders() {

        zacetniUI();
    }
	//Ustvarimo okno
    private void zacetniUI() {

        add(new Board());
        setTitle("Space Invaders");
        setSize(Nastavitve.sirinaOkna, Nastavitve.visinaOkna);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
    		var spaceInvaders = new SpaceInvaders();
            spaceInvaders.setVisible(true);
        };
    }

    
