package arkanoid.version1;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Nave extends Objeto implements KeyListener, MouseMotionListener{

	protected int vx; // Cantidad de píxeles que aumentará la posición del jugador en cada iteración del bucle principal del juego
	protected int vy; // igual a la anterior en el eje vertical
	private boolean up, down, left, right; // Booleanas que determinan si el player se está moviendo actualmente
	protected static final int PLAYER_SPEED = 5; // velocidad del movimiento de la nave en los dos ejes
	// constructor por defecto que inicializa la imagen de la nave

	public Nave() {
		this.image = SpritesRepository.getInstance().getSprite("nave-50x15.png");
		AjustarImagen();
		
	}

	@Override
	public void actor() {
		// Movimiento en el eje horizontal
		this.coordX += this.vx; // En cada iteración del bucle principal, la nave cambiará su posición en el
								// eje X, sumándole a esta el valor de vx
		// si la nave intenta salir por la izquierda no se lo permitimos
		if (this.coordX < 0) {
			this.coordX = 0;
		}
		// si la nave intenta salir por la derecha no se lo permitimos
		if (this.coordX > (Pantalla.getInstance().getWidth() - this.width)) {
			this.coordX = (Pantalla.getInstance().getWidth() - this.width);
		}
	}


	@Override
	public void paint(Graphics2D g) {
		g.drawImage(image, this.coordX, 485, null);

	}

	/**
	 * Cuando pulsen una tecla activamos la bandera booleana concreta que indica que
	 * existe un movimiento
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		}
		updateSpeed();

	}

	/**
	 * Cuando una tecla se libera se desactiva la bandera booleana que se había
	 * activado al pulsarla
	 */

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		}
		updateSpeed();

	}

	/**
	 * Este método no se utiliza pero es necesario implementarlo por el KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * En función de las banderas booleanas de movimiento, actualizamos las
	 * velocidades en los dos ejes
	 */
	protected void updateSpeed() {
		vx = 0;
		// vy = 0;
		// if (down) vY = PLAYER_SPEED;
		// if (up) vY = -PLAYER_SPEED;
		if (left)
			vx = -PLAYER_SPEED;
		if (right)
			vx = PLAYER_SPEED;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		setCoordX(arg0.getX());
		
	}

	@Override
	public void collisionWith(Objeto actorCollisioned) {
		// TODO Auto-generated method stub
		
	}

}
