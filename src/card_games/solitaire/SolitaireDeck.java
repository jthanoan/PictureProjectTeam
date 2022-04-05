package card_games.solitaire;
import card_game_resources.*;
import picture_project_resources.DigitalPicture;
import picture_project_resources.Pixel;

public class SolitaireDeck extends Deck{
	private static int xOffset = 30;
	private static int yOffset = 30;
	
	SolitaireDeck(int x, int y) {
		super(1, true, x + xOffset, y + yOffset);
	}

	@Override
	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
	}

}
