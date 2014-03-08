package Source;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author Rob
 */
public class Bueno extends Base {

    final static String DESAPARECE = "DESAPARECE";
    final static String PAUSADO = "PAUSA";
    ImageIcon icon;

    public Bueno(int px, int py) {
        super(px, py);
        anim = new Animacion();
        Image tImg0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/0_1.png")).getScaledInstance(50, 60, 1);
        Image tImg1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/1_1.png")).getScaledInstance(50, 60, 1);
        Image tImg2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/2.png")).getScaledInstance(50, 60, 1);
        Image tImg3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/3.png")).getScaledInstance(50, 60, 1);
        Image tImg4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/4.png")).getScaledInstance(50, 60, 1);
        Image tImg5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/5.png")).getScaledInstance(50, 60, 1);
        Image tImg8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/4.png")).getScaledInstance(50, 60, 1);
        Image tImg9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/3.png")).getScaledInstance(50, 60, 1);
        Image tImg10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/2.png")).getScaledInstance(50, 60, 1);
        Image tImg11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/1_1.png")).getScaledInstance(50, 60, 1);

        icon = new ImageIcon(tImg0);

        anim.sumaCuadro(tImg0, 100);
        anim.sumaCuadro(tImg1, 100);
        anim.sumaCuadro(tImg2, 100);
        anim.sumaCuadro(tImg3, 100);
        anim.sumaCuadro(tImg4, 100);
        anim.sumaCuadro(tImg5, 100);
        anim.sumaCuadro(tImg8, 100);
        anim.sumaCuadro(tImg9, 100);
        anim.sumaCuadro(tImg10, 100);
        anim.sumaCuadro(tImg11, 100);
    }

    public static String getPausado() {
        return PAUSADO;
    }

    public static String getDesaparece() {
        return DESAPARECE;
    }
}
