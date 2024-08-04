package main.window;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	private static final long serialVersionUID = -4244333664652114702L;

	public Window(JPanel panel) {
		setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
		setTitle("Pong Game");
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
}
