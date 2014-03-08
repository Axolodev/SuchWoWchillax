package Source;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * update -> paint(); paint -> paint1();
 */
import javax.swing.JFrame;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.util.LinkedList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 *
 * @author Rob
 */
public class ProyectoHolaMundo extends JFrame implements Runnable, MouseListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private AudioClip sonido;    // Objeto AudioClip
    private AudioClip bomb;    //Objeto AudioClip 
    private Bueno bueno;    // Objeto de la clase Elefante
    private Malo malo;   //Objeto de la clase Raton
    private LinkedList<Malo> lista;           //lista de ratones
    private int cant;               //cantidad de asteroides
    private boolean presionado;     //Boleano que controla si el botÂ´Â´oon estÃ¡ siendo presionado
    private int coordenada_x;
    private int coordenada_y;
    private int off_x;              //Coordenada para ajustar la imagen con click
    private int off_y;              //Coordenada para ajustar la imagen con click 
    private int vidas;
//    private Image gameOver;        //Imagen de Game-over
    private int direccion;          //Variable para la direcciÃ³n del personaje
    private int posrX;
    private int posrY;
    private int score;
    private int cont;
    private int x_mayor;
    private int x_menor;
    private int y_mayor;
    private int y_menor;
    private boolean flag;
    private boolean move;
    private boolean pausa;
    private long tiempo;
    private SoundClip lose;	//Sonido de fondo
    private SoundClip win;	//Sonido de la jungla

    public ProyectoHolaMundo() {

        setSize(700, 700);
        setTitle("JFrame HolaMundo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        pausa = false;
        move = false;
        direccion = 0;
        score = 0;                    //puntaje inicial
        vidas = 5;                    //vidaas iniciales
        cont = 0;                     //contadaor que indica cuantos asteroides han golpeado el fondo del applet
        x_mayor = (getWidth() - getWidth() / 10);           //posicion mÃ¡xima en x que tendrÃ¡n los asteroides
        x_menor = 0;           //posicion mÃ­nima en x que tendrÃ¡n los asteroides
        y_mayor = -100;          //posicion mÃ¡xima en y que tendrÃ¡n los asteroides
        y_menor = -200;        //posicion mÃ­nima en y que tendrÃ¡n los asteroides
        flag = false;
        int posX = getWidth() / 2;              // posicion inicial del planeta en x
        int posY = getHeight();             // posicion inicial del planeta en y
        bueno = new Bueno(getWidth() / 2, getHeight() / 2);
        setBackground(Color.black);
        //
        int rand = 0;
        //se genera un numero random para 6, 10 o 12 malos.
        while (rand != 6 && rand != 10 && rand != 12) {
            rand = 6 + (int) (Math.random() * 7);
        }
        lista = new LinkedList();
        int vel = 0;
        cant = 10;            //se crea la cantidad de asteroides al azar
        for (int i = 0; i < rand; ++i) {
            vel = 3 + (int) (Math.random() * 3);
            if (i < rand / 2) {
                posrX = ((int) (Math.random() * 100) + getWidth());
                vel = -vel;
            } else {
                posrX = (int) (Math.random() * -100);
            }
            posrY = (int) (Math.random() * (getWidth() - 50)) + 25;
            malo = new Malo(posrX, posrY);
            malo.setPosX(posrX);
            malo.setPosY(posrY);

            malo.setSpeed(vel);
            lista.add(malo);
        }
        lose = new SoundClip("Sonidos/lose.wav");
        win = new SoundClip("Sonidos/coin.wav");
        lose.setLooping(false);
        win.setLooping(false);
        //Se crea el hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        tiempo = System.currentTimeMillis();
        while (vidas > 0) {
            actualiza();
            checaColision();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo <I>actualiza</I>.
     * <P>
     * En este metodo se actualizan las posiciones de bueno como de la malo, ya
     * sea por presionar una tecla o por moverlos con el mouse.
     */
    public void actualiza() {
        long tiempoTranscurrido = System.currentTimeMillis() - tiempo;
        tiempo += tiempoTranscurrido;
        bueno.actualiza(tiempoTranscurrido);
        malo.actualiza(tiempoTranscurrido);

        switch (direccion) {
            case 1: {
                bueno.setPosY(bueno.getPosY() - 8);
                break;
            }
            case 2: {
                bueno.setPosX(bueno.getPosX() + 8);
                break;
            }
            case 3: {
                bueno.setPosY(bueno.getPosY() + 8);
                break; //se mueve hacia la izquierda
            }
            case 4: {
                bueno.setPosX(bueno.getPosX() - 8);
                break; //se mueve hacia la derecha
            }
        }

        for (Malo n : lista) {
            n.setPosX(n.getPosX() + n.getSpeed());       //iran moviendose 
        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto bueno con el objeto
     * malo y ademÃ¡s con las orillas del <code>Applet</code>.
     */
    public void checaColision() {

        if (bueno.getPosY() < 0) {
            bueno.setPosY(0);
            direccion = 0;
        }

        if (bueno.getPosY() + bueno.getAlto() > getHeight()) {
            bueno.setPosY(getHeight() - bueno.getAlto());
            direccion = 0;
        }

        if (bueno.getPosX() < 0) {
            bueno.setPosX(0);
            direccion = 0;
        }

        if (bueno.getPosX() + bueno.getAncho() > getWidth()) {
            bueno.setPosX(getWidth() - bueno.getAncho());
            direccion = 0;
        }

        for (Malo m : lista) {
            if (m.getPosX() < 0 && m.getSpeed() < 0) {
                m.setPosX((getWidth() + 100));                                            //se reposiciona en su posicion inicial
                if (score > 0) {
                    score -= 20;
                    lose.play();

                }
            } else if (m.getPosX() + m.getAncho() > getWidth() && m.getSpeed() > 0) {
                m.setPosX(-50);                                           //se reposiciona en su posicion inicial
                if (score > 0) { //si el puntaje es mayor a 0..se quitan 20 puntos
                    score -= 20;
                    lose.play();

                }
            }

        }
        for (Malo m : lista) {
            if (bueno.intersecta(m)) {
                score += 100;
                if (m.getSpeed() > 0) {
                    m.setPosY((int) (Math.random() * (getHeight() - 50)) + 25);
                    m.setPosX(-50);
                    win.play();
                } else {
                    m.setPosY((int) (Math.random() * (getHeight() - 50)) + 25);
                    m.setPosX(getWidth() + 50);
                    win.play();
                }
            }
        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getX() > getWidth() / 2) {
            if (e.getY() > getHeight() / 2) {
                direccion = 2;
            } else {
                direccion = 1;
            }
        } else {
            if (e.getY() > getHeight() / 2) {
                direccion = 3;
            } else {
                direccion = 4;
            }
        }
    }

    /**
     * Metodo <I>mouseEntered</I> sobrescrito de la clase
     * <code>mouseEvent</code>.<P>
     * En este metodo se verifica si el mouse entra al applet o un objeto dado.
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseExited</I> sobrescrito de la clase
     * <code>mouseEvent</code>.<P>
     * En este metodo se verifica si el mouse sale del applet o un objeto dado.
     */
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Metodo <I>MousePressed</I> sobrescrito de la clase
     * <code>mouseEvent</code>.<P>
     * En este metodo se verifica si el mouse ha dado click sobre la imÃ¡gen. Al
     * verificar que haya dado un click se actualizan las coordenadas de 'x' y
     * 'y' para ajustar el desfase que puede tener la imagen con el click
     */
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Metodo <I>MouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * En este mÃ©todo se verifica si el click del mouse ha sido liberado, si
     * sÃ­ entonces la booleana que l ocontrola se hace falsa, para marcar que
     * ya no estÃ¡ siendo presionadao.
     */
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (vidas > 0) {
            if (bueno != null && lista != null) {
                g.drawImage(bueno.getImagenI(), bueno.getPosX(), bueno.getPosY(), this);
                g.setColor(Color.white);
                g.drawString("Puntos = " + score, 20, 20);
                if (pausa) {
                    g.setColor(Color.white);
                    g.drawString(bueno.getPausado(), bueno.getPosX() + bueno.getAncho() / 3, bueno.getPosY() + bueno.getAlto() / 2);
                }

                for (Malo m : lista) {
                    g.drawImage(m.getImagenI(), m.getPosX(), m.getPosY(), this);
                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        }
    }

}
