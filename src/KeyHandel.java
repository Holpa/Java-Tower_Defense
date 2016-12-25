import java.awt.event.*;
import java.awt.*;

public class KeyHandel implements MouseMotionListener, MouseListener {

	public void mouseDragged(MouseEvent e) {
		Screen.mse= new Point((e.getX()) + ((Frame.size.width - Screen.myWidth)/2 ) ,(e.getY()) + ((Frame.size.height - (Screen.myHeight))- (Frame.size.width - Screen.myWidth)/2) );
	}

	public void mouseMoved(MouseEvent e) {
		Screen.mse= new Point((e.getX()) - ((Frame.size.width - Screen.myWidth)/2 ) ,(e.getY()) - ((Frame.size.height - (Screen.myHeight))- (Frame.size.width - Screen.myWidth)/2) );
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		Screen.store.click(e.getButton());
		//System.out.println("hey"); 
	}

	public void mouseReleased(MouseEvent e) {
		
	}}
