package arkanoid.version1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pelota extends Objeto {

	protected int vX = 2;
	protected int vY = 2;

	// Usamos un patrón singleton para poder llamar a este objeto desde cualquier
	// clase
	public static Pelota instance = null;

	public Pelota() {
		
	}

	/**
	 * 
	 */

	public static Pelota getInstance() {
		if (instance == null) {
			instance = new Pelota();
		}
		return instance;
	}

	/**
	 * 
	 */

	// Método que pinta la pelota
	public void paint(Graphics2D g) {

		g.setColor(Color.WHITE);
		g.fillOval(this.coordX, this.coordY, ANCHO_PELOTA, ALTO_PELOTA);

	}

	public void act() {
		
	}
	@Override
	public void collisionWith(Objeto actorCollisioned) {
		super.collisionWith(actorCollisioned);

		if (actorCollisioned instanceof Ladrillo) {
			vX = -vX;
			vY = -vY;
		}

	}

	@Override
	public void actor() {
		
		this.coordX += vX;
		this.coordY += vY;

		if (this.coordX < 0 || this.coordX > (Pantalla.getInstance().getWidth() - this.ANCHO_PELOTA)) {
			vX = -vX;

		}
		if (this.coordY < 0 || this.coordY > (Pantalla.getInstance().getHeight() - this.ALTO_PELOTA)) {
			vY = -vY;
			
		}
		
	}
	
	

	public void setvX(int i) {
		// TODO Auto-generated method stub

	}

	public static void add(Pelota m) {
		// TODO Auto-generated method stub

	}

}
