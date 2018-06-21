package lesson1.zuigers;

import java.util.Observable;
import java.util.Random;

/**
 * 
 * This is the model for the rectangle. All the logic happens here.
 *
 */

public class Zuiger extends Observable {
	
	/**
	 * height = hoogte van de zuiger. 
	 * deltaY = richting van de zuiger. -1 als zuiger omhoog gaat, 1 als de zuiger omlaag gaat.
	 * active = staat de zuiger aan of niet
	 */
	
	private int height, deltaY;
	private boolean active = true;
	/**
	 * 
	 * Constructor van zuiger. De initiele hoogte wordt random bepaald en is minimaal 50, maximaal 430
	 */
	public Zuiger() {
				
		height = new Random().nextInt(381) + 50;
	
		// Maak een toevallige keuze om de zuiger op en neer te laten gaan.
		if (System.nanoTime() %2 == 0) {
			deltaY = -1;
		} else {
			deltaY = 1;
		}
		
	}
	
	
	public int getHeight() {
		return height;
	}

	public int getDeltaY() {
		return deltaY;
	}
	
	/**
	 * Checkt eerst of de hoogte van de zuiger tegen de grenzen aanloopt. Als dat zo is, dan verandert de richting van de zuiger
	 * en laat de observer (het panel) weten dat er een verandering is geweest
	 */
	public void changeHeight() {
		flipDeltaY();
		height = height + getDeltaY() * 1;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Als de hoogte < 50 of groter dan 430 dan verander de richting van de zuigerbeweging
	 */
	public void flipDeltaY() {
		if (height <50 || height > 430)  changeDirection();
	}
	
	public void changeDirection() {
		deltaY *= -1;
	}
	
	public boolean isActive() {
		return active;
	}

	

}
