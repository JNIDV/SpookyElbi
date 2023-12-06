package sprite;

import application.GameStage;
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
	}
	
	public void setSizes(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public void setImage(String filename, double width, double height) {
		Image image = new Image(filename, width, height, true, true);
		setImage(image);
		setSizes(width, height);
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
		
		if (this.positionX + this.width >= GameStage.CANVAS_WIDTH) {
			this.positionX = GameStage.CANVAS_WIDTH - this.width;
		} else if (this.positionX <= 0) {
			this.positionX = 0;
		}
		
		if (this.positionY + this.height >= GameStage.CANVAS_HEIGHT) {
			this.positionY = GameStage.CANVAS_HEIGHT - this.height;
		} else if (this.positionY <= 0) {
			this.positionY = 0;
		}
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
