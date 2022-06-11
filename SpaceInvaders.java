package proj.sprite;

import java.awt.EventQueue;
import javax.swing.JFrame;
public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {

        zacetniUI();
    }

    private void zacetniUI() {

        add(new Board());
        setTitle("Space Invaders");
        setSize(Nastavitve.sirinaOkna, Nastavitve.visinaOkna);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
        	var ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}
    
