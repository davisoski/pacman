package uned.es.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import uned.es.game.controller.TecladoController;
import uned.es.game.maze.Map;
import uned.es.game.model.PacMan;

/**
 * 
 * @author david
 * 
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private Image iPacman;
	private Image iBlinky, iPinky, iInky, iClyde;
	// Cuando se come uno de los puntos grandes, los fantamas se vuelven azules
	private Image iBlinky_blue, iPinky_blue, iInky_blue, iClyde_blue;

	// Controlla los eventos de teclado
	TecladoController entradasTeclado;

	int xPacman = 30;
	int yPacman = 10;

	private Timer timer;
	private Map m;
	private PacMan pacman;
	
	

	public Board() {

		setBackground(Color.BLACK);
		// Activo el doble buffer
		setDoubleBuffered(true);

		// Cargo las imagenes de los dibujos
		loadImages();

		// Activo el controlador para el teclado
		this.addKeyListener();

		// Aniado el controlador para el sonido
		// sonido();

		// Creo una instancia de pacman
		pacman = new PacMan(entradasTeclado);
		//Creo y pinto el laberinto (maze)
		m = new Map();

		// Cuanto mas pequenio es el valor mas rapido va la animacion
		timer = new Timer(15, this);
//		timer.start();
	}

	private void addKeyListener() {
		entradasTeclado = new TecladoController();
		this.addKeyListener(entradasTeclado);
		setFocusable(true);

	}

	private void sonido() {
		// Open an input stream to the audio file.
		InputStream in = null;
		try {
			in = new FileInputStream("sonidos/gameover.wav");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create an AudioStream object from the input stream.
		AudioStream as = null;
		try {
			as = new AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Use the static class member "player" from class AudioPlayer to play
		// clip.
		AudioPlayer.player.start(as);
		// Similarly, to stop the audio.
		// AudioPlayer.player.stop(as);

	}

	/**
	 * Leo los ficheros con las imagenes de los elementos que pululan por el
	 * laberinto
	 */
	private void loadImages() {
		iPacman = new ImageIcon("images/pacman.png").getImage();
		iBlinky = new ImageIcon("images/blinky.png").getImage();
		iPinky = new ImageIcon("images/pinky.png").getImage();
		iInky = new ImageIcon("images/inky.png").getImage();
		iClyde = new ImageIcon("images/clyde.png").getImage();
		iBlinky_blue = new ImageIcon("images/blinky_blue.png").getImage();
		iPinky_blue = new ImageIcon("images/pinky_blue.png").getImage();
		iInky_blue = new ImageIcon("images/inky_blue.png").getImage();
		iClyde_blue = new ImageIcon("images/clyde_blue.png").getImage();
	}

	/**
	 * Este metodo se dispara siempre que se minimiza, maximiza, se cierra, se
	 * abre la ventana, etc...
	 */
	@Override
	public void paint(Graphics g) {
		System.out.println("paint");

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		drawPacman(g2d);
		
		
		if (m != null) {
			for (int y = 0; y < 29; y++) {
				for (int x = 0; x < 27; x++) {

					if (m.getMap(x, y).equals("0")) {
						g.drawImage(m.getCeldaVacia(), x * 16, y * 16, null);
					}
					if (m.getMap(x, y).equals("1")) {
						g.drawImage(m.getDot(), x * 16, y * 16, null);
					}
					if (m.getMap(x, y).equals("2")) {
						g.drawImage(m.getPowerPellet(), x * 16, y * 16, null);
					}
					if (m.getMap(x, y).equals("3")) {
						g.drawImage(m.getPared(), x * 16, y * 16, null);
					}
					if (m.getMap(x, y).equals("4")) {
						g.drawImage(m.getCeldaAbierta(), x * 16, y * 16, null);
					}
				}
			}
		} else {
			System.out.println("m == null");
		}
	
	}

	private void drawPacman(Graphics2D g2d) {
		System.out.println("drawPacman");
		g2d.drawImage(iPacman, pacman.getX(), pacman.getY(), this);

		g2d.drawImage(iBlinky, 0, 50, this);
		g2d.drawImage(iPinky, 0, 100, this);
		g2d.drawImage(iInky, 0, 150, this);
		g2d.drawImage(iClyde, 0, 200, this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pacman.move();
		repaint();

	}

}
