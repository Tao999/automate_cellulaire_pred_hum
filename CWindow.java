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
		if (grid == null)
			return;
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i].isHuman()) {
					int iTemp = (int) ((((grid[j][i].getM_life() - grid[j][i].MIN_HP_HUMAN)
							% (grid[j][i].MAX_HP_HUMAN - grid[j][i].MIN_HP_HUMAN)) * 230) / grid[j][i].MAX_HP_HUMAN)
							+ 25;
					g.setColor(new Color(0, iTemp, 0));
				} else if (grid[j][i].isPredator()) {
					int iTemp = (int) ((grid[j][i].getM_life() * 255) / grid[j][i].MAX_HP_PREDATOR);
					if (iTemp > 255)
						iTemp = 255;
					g.setColor(new Color(iTemp, 0, 0));
				} else
					g.setColor(Color.black);
				g.fillRect(i, j, 1, 1);// AFFICHAGE DU PIXEL
			}
		}
	}
}
