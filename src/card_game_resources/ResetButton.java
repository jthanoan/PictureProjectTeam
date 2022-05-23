package card_game_resources;

import picture_project_resources.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class ResetButton extends Button{
    private static final int WIDTH = 302;
    private static final int HEIGHT = 136;
    private static final int x = 920;
    private static final int y = 70;

    BufferedImage resetButton = new Picture("GameResources//General//reset_game.png").getBufferedImage();

    public ResetButton() {
        super(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void draw(Graphics graphics, ImageObserver io) {
        graphics.drawImage(resetButton, x, y, io);
    }
}
