package pack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CWindow extends JFrame {
	private CCreature[][] grid;

	JPanel panel;

	public CWindow() {

	}

	public CWindow(int width, int height) {
		grid = null;
		setTitle("Prédateur");
		setSize(width, height);
		setResizable(false);
		panel = new JPanel();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.setProperty("sun.java2d.opengl", "true");
	}

	public void update(CCreature[][] grid) {
		this.grid = grid;
		paint(getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		int pred = 0;
		int hum = 0;
		if (grid == null)
			return;
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i].isHuman()) {
					hum++;
					g.setColor(new Color(0, 100, 0));
				} else if (grid[j][i].isPredator()) {
					pred++;
					g.setColor(Color.red);
				} else
					g.setColor(Color.black);
				g.fillRect(i, j, 1, 1);
			}
		}
		g.setColor(Color.white);
		g.setFont(new Font("consolas", Font.BOLD, 14));
		/*
		 * if (pred + hum != 0) { g.drawString(new String("Predateurs : " +
		 * (int) (pred * 100 / (pred + hum)) + "%"), 10, 50); g.drawString(new
		 * String("Humains    : " + (int) (hum * 100 / (pred + hum)) + "%"), 10,
		 * 65); }
		 */
	}
}
