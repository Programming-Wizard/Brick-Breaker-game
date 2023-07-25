package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick {
	private Rectangle shape;
	private Color color;
	private int height;
	private int width;

	public Brick(Color color, int width, int height) {
		this.color = color;
		this.width = width;
		this.height = height;

		shape = new Rectangle(width, height);
		shape.setFill(color);
	}

	public Rectangle getShape() {
		return shape;
	}
}
