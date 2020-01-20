package arkanoid.version1;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ladrillo extends Objeto {

	//int vX = 20;
	//int vY = 20;
	
	// Usamos un patrón singleton para poder llamar a este objeto desde cualquier
	// clase
	private static Ladrillo instance = null;
	
	
	public Ladrillo() {
		//this.coordX = 10;
		//this.coordY = 10;
		//this.color = Color.GREEN;
		this.height = 35;
		this.width = 30;
		
	}

	public static Ladrillo getInstance() {
		if (instance == null) {
			instance = new Ladrillo();
		}
		return instance;
	}

	/**
	 * 
	 * @param h
	 */

	// Método que pinta el ladrillo
	
	@Override
	public void paint(Graphics2D h) {
		// System.out.println("Estamos pintando un rectángulo para el ladrillo");
		
		h.setColor(getColor());;
		h.fillRoundRect(this.coordX, this.coordY, this.height, this.width,9,9);
		h.setColor(Color.DARK_GRAY);
		h.drawRect(this.coordX, this.coordY, this.height, this.width);
		
	}
	public void collisionWith(Objeto actorCollisioned) {
		super.collisionWith(actorCollisioned);
		// Debo comprobar el tipo del actor que colisiona con este
		if (actorCollisioned instanceof Pelota) {
			// Si este actor colisiona con un ladrillo, debo eliminar el ladrillo
			this.setMarkedForRemoval(true);
			// Si el ladrillo colisiona con la pelota, también debo eliminar el mision o la bomba
			//actorCollisioned.setMarkedForRemoval(true);
			
		}
	}

	@Override
	public void actor() {

		
	}

	public int getX() {
		return coordX;
	}

	public void setX(int x) {
		this.coordX = x;
	}

	public int getY() {
		return coordY;
	}

	public void setY(int y) {
		this.coordY = y;
	}

	

	/*
	public int getvX() {
		return vX;
	}
*/
	/*
	public void setvX(int vX) {
		this.vX = vX;
	}
	*/
/*
	public int getvY() {
		return vY;
	}
	*/
/*
	public void setvY(int vY) {
		this.vY = vY;
	}
	*/

}
