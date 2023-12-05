package sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Sprite {
	protected Image image;
	protected double positionX;
	protected double positionY;
	protected double velocityX;
	protected double velocityY;
	protected double width;
	protected double height;
	
	public void setImage(Image image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public void setImage(String filename) {
		Image image = new Image(filename, 20, 20, true, true);
		setImage(image);
	}
	
	public void rotateImage(double degrees) {
		ImageView imageView = new ImageView(this.image);
		imageView.setRotate(degrees);
		
		SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        Image rotatedImage = imageView.snapshot(params, null);
        this.image = rotatedImage;
	}
	
	public void setPosition(double x, double y) {
		this.positionX = x;
		this.positionY = y;
	}
	
	public void setVelocity(double x, double y) {
		this.velocityX = x;
		this.velocityY = y;
	}
	
	public void addVelocity(double x, double y) {
		this.velocityX += x;
		this.velocityY += y;
	}
	
	public void update(double time) {
		this.positionX += velocityX * time;
		this.positionY += velocityY * time;
//		System.out.println(positionX + " " + positionY);
	}
	
	public void render(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(image, positionX, positionY);
	}
	
	public Rectangle2D getBoundary() {
		return new Rectangle2D(
			this.positionX,
			this.positionY,
			this.width,
			this.height
		);
	}
	
	public boolean intersects(Sprite sprite) {
		return sprite.getBoundary().intersects(this.getBoundary());
	}
	
	public String toString() {
		return " Position: [" + this.positionX + ", " + this.positionY + "]" + " Velocity: [" + this.velocityX + ", " + this.velocityY + "]";
	}
	
	// Setters and getters
	
	public double getPositionX() {
		return this.positionX;
	}
	
	public double getPositionY() {
		return this.positionY;
	}
}
