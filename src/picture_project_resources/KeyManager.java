package picture_project_resources;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyManager extends KeyAdapter{
	
	private static volatile boolean wPressed = false;
	
	@Override
    public void keyPressed(KeyEvent event) {

        char ch = event.getKeyChar();

        System.out.println(event.getKeyChar());

        
    }
	
    public static boolean isWPressed() {
        synchronized (KeyManager.class) {
            return wPressed;
        }
    }
    
	public static void main(String[] args) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	
	        @Override
	        public boolean dispatchKeyEvent(KeyEvent ke) {
	            synchronized (KeyManager.class) {
	                switch (ke.getID()) {
	                case KeyEvent.KEY_PRESSED:
	                    if (ke.getKeyCode() == KeyEvent.VK_W) {
	                        wPressed = true;
	                    }
	                    break;
	
	                case KeyEvent.KEY_RELEASED:
	                    if (ke.getKeyCode() == KeyEvent.VK_W) {
	                        wPressed = false;
	                    }
	                    break;
	                }
	                return false;
	            }
	        }
	    });
	}
}
