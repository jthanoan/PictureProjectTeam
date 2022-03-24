package card_game_resources;
import picture_project_resources.Pixel;

public class BoundingBox {
	private int[][] bounds = new int[2][2];
	
	public BoundingBox(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
		bounds[0][0] = topLeftX;
		bounds[0][1] = topLeftY;
		bounds[1][0] = bottomRightX;
		bounds[1][1] = bottomRightY;
	}
	
	public int getLeftBound() {
		return bounds[0][0];
	}
	
	public int getTopBound() {
		return bounds[0][1];
	}
	
	public int getRightBound() {
		return bounds[1][0];
	}
	
	public int getBottomBound() {
		return bounds[1][1];
	}
	
	public boolean wasClicked(Pixel pixel) {
		boolean clickInBounds = false;
		
		int x = pixel.getX();
		int y = pixel.getY();
		
		if(this.getLeftBound()<x && x<this.getRightBound()) {
			if(this.getBottomBound()>y && y>this.getTopBound()) {
				clickInBounds = true;
			}
		}
		
		return clickInBounds;
	}
}
