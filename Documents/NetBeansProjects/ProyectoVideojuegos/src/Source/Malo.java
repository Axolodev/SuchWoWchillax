package Source;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Rob
 */
public class Malo extends Base {

    private static int conteo;
    private int speed;

    public Malo(int px, int py) {
        super(px, py);
        anim = new Animacion();
        Image tImg11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/1.png"));
        Image tImg12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Imagenes/0.png"));

        anim.sumaCuadro(tImg11, 100);
        anim.sumaCuadro(tImg12, 100);
    }

    public static int getConteo() {
        return conteo;
    }

    public static void setConteo(int cont) {
        conteo = cont;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int vel) {
        speed = vel;
    }
}
