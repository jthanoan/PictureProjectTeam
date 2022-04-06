package card_games.slapjack;

import card_game_resources.*;
import picture_project_resources.DigitalPicture;
import picture_project_resources.Pixel;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Slapjack implements CardGame {

    @Override
    public void drawCard(Player player) {

    }

    @Override
    public void draw(Graphics2D graphics, ImageObserver io) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void mouseClickedAction(DigitalPicture pict, Pixel pix) {

    }

    @Override
    public void mouseDraggedAction(DigitalPicture pict, Pixel pix) {

    }

    @Override
    public void mouseUpdateAction(boolean mouseDown, Pixel pix) {

    }

    @Override
    public int deckSize() {
        return 0;
    }

    @Override
    public Card nthCard(int n) {
        return null;
    }

    @Override
    public boolean cardSelected() {
        return false;
    }

    @Override
    public InteractableObject selectedCard() {
        return null;
    }

    @Override
    public void setSelectedCardLocation(Coordinate c) {

    }

    @Override
    public void returnSelectedCard(Pixel pix) {

    }
}
