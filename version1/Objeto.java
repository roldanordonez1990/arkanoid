package arkanoid.version1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Objeto {

	public Color color;
	public String nombre;
	public int width, height;
	public int coordX;
	public int coordY;
	//public int vX;
	//public int vY;
	protected BufferedImage spriteActual = null; // Sprite que representa actualmente a este actor
	protected boolean markedForRemoval = false;  // Pondremos a true esta bandera cuando el actor deba ser eliminado de la siguiente iteración
	  // del juego
	
	
	protected static int ANCHO_PELOTA = 15;
	protected static int ALTO_PELOTA = 15;

	protected BufferedImage image;
	// private int unidadDeTiempo = 0; // La unidad de tiempo aumenta cada vez que
	// se llama al método "act()" del Actor
	// protected int velocidadDeCambioDeSprite = 0; // Esta propiedad indica cada
	// cuántas "unidades de tiempo" debemos
	// mostrar
	protected List<BufferedImage> sprites = new ArrayList<BufferedImage>(); // Lista de archivos de imagen utilizado para representarse en panta // el
																			// siguiente sprite del actor
	//protected BufferedImage spriteActual = null; // Sprite que representa actualmente a este actor

	public Objeto() {
		
	}

		
	/**
	 * 
	 */

	public void AjustarImagen() {
		// Una vez que tengo la imagen que representa a este actor, obtengo el ancho y
		// el alto de la misma y se
		// los traspaso a objeto actual.
		height = this.image.getHeight();
		width = this.image.getWidth();
	}

	public abstract void actor();
	public abstract void paint(Graphics2D g);
	public void collisionWith (Objeto actorCollisioned) {
		
	}
	
	/**
	 * Método que lleva el control de las unidades de tiempo y el sprite que representa en cada momento al actor. Los subtipos
	 * deberán incorporar este método y realizar la llamada "super.act()".
	 */
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public BufferedImage getImage() {
		return image;
	}
	

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}


	public boolean isMarkedForRemoval() {
		return markedForRemoval;
	}


	public void setMarkedForRemoval(boolean markedForRemoval) {
		this.markedForRemoval = markedForRemoval;
	}
	
	
}
