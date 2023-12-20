/********************************************************
 * 
 * 
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package sprite;

import java.util.Timer;
import java.util.TimerTask;

import application.GameTimer;
import entities.MainCharacter;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Sprite {
	protected Image baseImage;
	protected Image redImage;
	protected Image image;
	protected double positionX;
	protected double positionY;
	protected double velocityX = 0;
	protected double velocityY = 0;
	protected double width;
	protected double height;
	
	public void setImage(Image image) {
		this.image = image;
		this.baseImage = image;
	}
	
	public void setImage(Image image, double width, double height) {
		this.image = image;
		this.baseImage = image;
		this.width = width;
		this.height = height;
	}
	
	public void setRedImage(Image redImage) {
		this.redImage = redImage;
	}
	
	public void setSizes(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public void setImage(String filename, double width, double height) {
		Image image = new Image(filename, width, height, true, true);
		this.setImage(image);
		this.setSizes(width, height);
	}
	
	public void rotateImage(double degrees) {
		ImageView imageView = new ImageView(this.baseImage);
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
		
		if (this.positionX + this.width >= GameTimer.CANVAS_WIDTH - GameTimer.VIEWPORT_WIDTH / 2) {
			this.positionX = GameTimer.CANVAS_WIDTH - this.width - GameTimer.VIEWPORT_WIDTH / 2;
		} else if (this.positionX <= GameTimer.VIEWPORT_WIDTH / 2) {
			this.positionX = GameTimer.VIEWPORT_WIDTH / 2;
		}
		
		if (this.positionY + this.height >= GameTimer.CANVAS_HEIGHT - GameTimer.VIEWPORT_HEIGHT / 2) {
			this.positionY = GameTimer.CANVAS_HEIGHT - this.height - GameTimer.VIEWPORT_HEIGHT / 2;
		} else if (this.positionY <= GameTimer.VIEWPORT_HEIGHT / 2) {
			this.positionY = GameTimer.VIEWPORT_HEIGHT / 2;
		}
	}
	
	public void render(GraphicsContext graphicsContext) {
		graphicsContext.drawImage(image, positionX, positionY);
	}
	
	public Rectangle2D getBoundary() {
		return new Rectangle2D(this.positionX, this.positionY, this.width, this.height);
	}
	
	public boolean intersects(Sprite sprite) {
		return sprite.getBoundary().intersects(this.getBoundary());
	}
	
	public void getHit() {
		this.image = this.redImage;
		Sprite self = this;
		
		new Timer().schedule(
        	new TimerTask() {
        		@Override
        		public void run() {
        			image = baseImage;
        			
        			if (self instanceof MainCharacter) {
        				((MainCharacter) self).isHit = false;
        			}
        			cancel();
        		}
        	}, 
        	500
		);
	}
	
	public double getPositionX() {
		return this.positionX;
	}
	
	public double getPositionY() {
		return this.positionY;
	}
	
	public double getVelocityX() {
		return this.velocityX;
	}
	
	public double getVelocityY() {
		return this.velocityY;
	}
}
