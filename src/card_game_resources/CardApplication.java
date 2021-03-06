package card_game_resources;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import card_games.solitaire.Solitaire;
import picture_project_resources.DigitalPicture;
import picture_project_resources.FlexiblePictureExplorer;
import picture_project_resources.Picture;
import picture_project_resources.Pixel;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class CardApplication extends FlexiblePictureExplorer implements ImageObserver{
	public static final int HEIGHT = 800;
	public static final int WIDTH = 1200;

	//GUI gui = new GUI();
	CardGame currentGame = new Solitaire(this);
	GameType currentGameType = GameType.SOLITAIRE;

	ResetButton resetButton = new ResetButton();

	Coordinate mouseLocation = new Coordinate(0,0);
	
	public CardApplication() {
		super(new Picture(HEIGHT, WIDTH));
		draw();
	}

	public enum GameType {
		SOLITAIRE
	}
	public void draw() {
		Picture display = new Picture(HEIGHT, WIDTH);
		Graphics2D graphics = display.createGraphics();

		graphics.drawImage(new Picture("GameResources\\General\\table.png").getBufferedImage(),0,0,this);
		if(currentGame.cardSelected()) currentGame.setSelectedCardLocation(mouseLocation);
			currentGame.draw(graphics, this);

		resetButton.draw(graphics, this);
		setImage(display);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

	@Override
	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
		currentGame.mouseClickedAction(pict, pix);
		if(resetButton.wasClicked(pix))
			if(currentGameType==GameType.SOLITAIRE)
				currentGame = new Solitaire(this);
		draw();
	}
	
	public static void main(String[] args) {
		CardApplication ca = new CardApplication();
	}

	@Override
	public void mouseDraggedAction(DigitalPicture pict, Pixel pix) {
		mouseLocation = new Coordinate(pix.getX(), pix.getY());
		currentGame.mouseDraggedAction(pict, pix);
		draw();
	}

	@Override
	public void mouseUpdateAction(boolean mouseDown, Pixel pix) {
		if(!mouseDown) {
			currentGame.returnSelectedCard(pix);
		}
		currentGame.mouseUpdateAction(mouseDown, pix);
		draw();
	}
	
}
