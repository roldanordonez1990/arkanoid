package arkanoid.version1;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Pantalla extends Canvas {

	// Ventana principal del juego
	JFrame ventana = new JFrame("Arkanoid");

	// Indicamos alto y ancho del objeto tipo Canvas
	private static final int JFRAME_WIDTH = 450;
	private static final int JFRAME_HEIGHT = 550;
	// Variable para establecer la instancia del patr�n singleton
	private static Pantalla instance = null;
	public int posX, posY, vX, vY;
	Pelota ball = new Pelota();
	Nave nave = new Nave();
	Ladrillo ladrillo = new Ladrillo();

	private long usedTime; // Tiempo usado en cada iteraci�n del bucle principal del juego
	// Velocidad de los fotogramas, en concreto este indica que el proceso de
	// redibujado dormir� 10 millis
	// tras haber repintado la escena
	public static final int SPEED_FPS = 70;

	// Lista con todos los actores que intervienen en el videojuego
	private List<Objeto> actors = new ArrayList<Objeto>();
	// private List<Ladrillo> ladrillos = new ArrayList<Ladrillo>();
	// Lista con actores que deben incorporarse en la siguiente iteraci�n del juego
	private List<Objeto> newActorsForNextIteration = new ArrayList<Objeto>();

	// BufferStrategy usado para conseguir la t�cnica de doble b�ffer
	private BufferStrategy strategy;
	// private long usedTime; // Tiempo usado en cada iteraci�n del bucle principal
	// del juego.
	// Referencia que guardaremos apuntando al objeto de tipo Player
	Nave player = null;

	public Pantalla() {

		// actors.add(ladrillo);

		posX = WIDTH / 2;
		posY = HEIGHT / 2;

		// Obtengo referencia al panel principal de la ventana
		JPanel panel = (JPanel) ventana.getContentPane();
		// Establezco una plantilla en el panel, para poder incorporar el Canvas
		panel.setLayout(new BorderLayout());
		// Agrego el Canvas al panel
		panel.add(this, BorderLayout.CENTER);
		// Desactivo el comportamiento por defecto al pulsar el bot�n de cierre de la
		// ventana
		ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Agrego un escuchador a la ventana, para detectar el evento de cierre de la
		// misma
		// Dimensiono la ventana
		ventana.setBounds(100, 100, JFRAME_WIDTH, JFRAME_HEIGHT);
		ventana.setVisible(true);
		// Con esto hacemos que la ventana no se mueva
		ventana.setResizable(false);
		// Con windowsAdapter lo que acemos es instanciar s�lo algunos m�todos del
		// windowsListener en vez de todos
		ventana.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cerrarApp();
			}
		});

		// Con ignoreRepaint le decimos al JFrame que no debe repintarse cuando el
		// Sistema Operativo se lo indique, nosotros nos ocupamos totalmente del
		// refresco de la pantalla
		ventana.setIgnoreRepaint(true);
		// El Canvas se dibujar� en pantalla con una estrategia de doble b�ffer
		this.createBufferStrategy(2);
		// Obtengo una referencia a la estrategia de doble b�ffer.
		strategy = getBufferStrategy();
		// El foco de Windows ir� al Canvas, para que directamente podamos controlar
		// este juego, si utiliz�ramos el teclado
		this.requestFocus();

	}

	/**
	 * 
	 * @param args
	 */

	// generamos el m�todo para cerrar la ventada d�ndo opciones
	public void cerrarApp() {
		String[] opciones = { "Aceptar", "Cancelar" };
		int eleccion = JOptionPane.showOptionDialog(ventana, "�Desea cerrar la aplicaci�n?", "Salir de la aplicaci�n",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
		if (eleccion == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * 
	 */

	// Se intenta cargar el recurso del disco duro, si no se pudiera se capturar� la
	// excepci�n y se mostrar� un mensaje en pantalla
	public BufferedImage loadImage(String nombre) {
		URL url = null;
		try {
			url = getClass().getResource(nombre);
			return ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No se pudo cargar la imagen " + nombre + " de " + url);
			System.out.println("El error fue : " + e.getClass().getName() + " " + e.getMessage());
			System.exit(0);
			return null;
		}
	}

	public void paintWorld() {
		// Resuelve un problema de sincronizaci�n de memoria de v�deo en Linux
		Toolkit.getDefaultToolkit().sync();

		// Obtengo el objeto gr�fico que me permita pintar en el doble b�ffer
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		// El rect�ngulo que estamos pintamos tiene las mismas dimensiones que el Canvas
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// BufferedImage nave = loadImage("../res/nave-50x15.png");
		// g.drawImage(nave, 185, 485, this);
		// Ladrillo.getInstance().paint(g);
		for (Objeto actores : actors) {
			actores.paint(g);
		}

		// ball.paint(g);

		strategy.show();

	}

	/**
	 * M�todo principal del juego, con el bucle cont�nuo que refresca el mismo en
	 * cada FPS
	 */
	public void game() {
		// Inicializaci�n del juego
		initWorld();

		// El bucle se ejecutar� mientras el objeto Canvas sea visible
		while (isVisible()) {
			long startTime = System.currentTimeMillis(); // Tomo el tiempo, en millis, antes de crear el siguiente Frame
															// del juego
			// actualizo y pinto la escena
			updateWorld();

			// ball.act();
			paintWorld();
			// Calculo el tiempo que se ha tardado en la ejecuci�n
			usedTime = System.currentTimeMillis() - startTime;

			// Hago que el bucle pare una serie de millis, antes de generar el siguiente FPS
			// El c�lculo hecho "duerme" el proceso para no generar m�s de los SPEED_FPS que
			// se haya espec�ficado
			try {
				int millisToSleep = (int) (1000 / SPEED_FPS - usedTime);
				if (millisToSleep < 0) {
					millisToSleep = 0;
				}
				Thread.sleep(millisToSleep);

			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
	}

	public void initWorld() {

		int posX = 7;
		int posy = 0;
		for (int i = 0; i <= 11; i++) {
			Ladrillo ladrillo = new Ladrillo();
			ladrillo.setColor(Color.YELLOW);
			ladrillo.setCoordX(posX);
			ladrillo.setCoordY(posY);
			actors.add(ladrillo);
			posX += ladrillo.getWidth() + 6;

		}
		posX = 7;
		posY = 35;
		for (int i = 0; i <= 11; i++) {
			Ladrillo ladrillo = new Ladrillo();
			ladrillo.setColor(Color.CYAN);
			ladrillo.setCoordX(posX);
			ladrillo.setCoordY(posY);
			actors.add(ladrillo);
			posX += ladrillo.getWidth() + 6;
		}
		posX = 7;
		posY = 70;
		for (int i = 0; i <= 11; i++) {
			Ladrillo ladrillo = new Ladrillo();
			ladrillo.setColor(Color.GREEN);
			ladrillo.setCoordX(posX);
			ladrillo.setCoordY(posY);
			actors.add(ladrillo);
			posX += ladrillo.getWidth() + 6;
		}
		posX = 7;
		posY = 105;
		for (int i = 0; i <= 11; i++) {
			Ladrillo ladrillo = new Ladrillo();
			ladrillo.setColor(Color.RED);
			ladrillo.setCoordX(posX);
			ladrillo.setCoordY(posY);
			actors.add(ladrillo);
			posX += ladrillo.getWidth() + 6;
		}

		actors.add(ball);
		// actors.add(nave);
		// Agrego a la lista de jugadores al actor de tipo Player
		nave = new Nave();
		nave.setCoordX(185);
		nave.setCoordY(485);
		actors.add(nave);
		// Mantengo una referencia al Player
		// Agrego un listener para eventos de teclado y, cuando se produzcan, los derivo
		// al objeto de tipo Player
		this.addKeyListener(nave);
		this.addMouseMotionListener(nave);

	}

	public void updateWorld() {

		// Puede ocurrir que existan actores que se deben eliminar para el siguiente
		// pintado de escena.
		// Cuando estoy recorriendo una lista no puedo eliminar elementos sin
		// arriesgarme a provocar un problema de
		// concurrencia de acceso. Por ello lo que hago es crear una nueva lista con los
		// elementos a eliminar. Despu�s
		// se recorre esa lista eliminando los elementos de la lista principal y, por
		// �ltimo, limpio la lista
		List<Objeto> actorsForRemoval = new ArrayList<Objeto>();
		for (Objeto actores : this.actors) {
			if (actores.isMarkedForRemoval()) {
				actorsForRemoval.add(actores);
			}
		}
		// Elimino los actores marcados para su eliminaci�n
		for (Objeto actores : actorsForRemoval) {
			this.actors.remove(actores);
		}
		// Limpio la lista de actores para eliminar
		actorsForRemoval.clear();
		// Adem�s de eliminar actores, tambi�n puede haber actores nuevos que se deban
		// insertar en la siguiente iteraci�n.
		// Se insertan y despu�s se limpia la lista de nuevos actores a insertar
		this.actors.addAll(newActorsForNextIteration);
		this.newActorsForNextIteration.clear();

		for (Objeto actores : this.actors) {
			actores.actor();
			actores.actor();

		}
		// Una vez que cada actor ha actuado, intento detectar colisiones entre los
		// actores y notificarlas. Para detectar
		// estas colisiones, no nos queda m�s remedio que intentar detectar la colisi�n
		// de cualquier actor con cualquier otro
		// s�lo con la excepci�n de no comparar un actor consigo mismo.
		// La detecci�n de colisiones se va a baser en formar un rect�ngulo con las
		// medidas que ocupa cada actor en pantalla,
		// De esa manera, las colisiones se traducir�n en intersecciones entre
		// rect�ngulos.
		for (Objeto actor1 : this.actors) {
			// Creo un rect�ngulo para este actor.
			Rectangle rect1 = new Rectangle(actor1.getCoordX(), actor1.getCoordY(), actor1.getWidth(),actor1.getHeight());
			// Compruebo un actor con cualquier otro actor
			for (Objeto actor2 : this.actors) {
				// Evito comparar un actor consigo mismo, ya que eso siempre provocar�a una
				// colisi�n y no tiene sentido
				if (!actor1.equals(actor2)) {
					// Formo el rect�ngulo del actor 2
					Rectangle rect2 = new Rectangle(actor2.getCoordX(), actor2.getCoordY(), actor2.getWidth(),actor2.getHeight());
					// Si los dos rect�ngulos tienen alguna intersecci�n, notifico una colisi�n en
					// los dos actores
					if (rect1.intersects(rect2)) {
						actor1.collisionWith(actor2); // El actor 1 colisiona con el actor 2
						actor2.collisionWith(actor1); // El actor 2 colisiona con el actor 1
					}
				}
			}
		}

	}

	/**
	 * 
	 * @param args
	 */

	public static Pantalla getInstance() {
		if (instance == null) {
			instance = new Pantalla();
		}
		return instance;
	}

	/**
	 * 
	 * @return
	 */

	public static int getJframeWidth() {
		return JFRAME_WIDTH;
	}

	public static int getJframeHeight() {
		return JFRAME_HEIGHT;
	}

	public static void setInstance(Pantalla instance) {
		Pantalla.instance = instance;
	}

	public static void main(String[] args) {

		Pantalla.getInstance().game();
	}

}
