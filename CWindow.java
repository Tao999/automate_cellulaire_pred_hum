package pack;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CWindow extends JFrame {
	private CCreature[][] grid;
	public CWindow() {

	}

	public CWindow(int width, int height) {
		JPanel panel = new JPanel();
		setSize(width, height);
		setResizable(false);
		panel.setSize(width, height);
		add(panel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void update(CCreature[][] grid) {
		this.grid = grid;
		paint(getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		if(grid==null)return;
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if(grid[j][i].getM_type()==CCreature.m_types.HUMAN.ordinal())
					g.setColor(Color.blue);
				else if(grid[j][i].getM_type()==CCreature.m_types.PREDATOR.ordinal())
					g.setColor(Color.red);
				else
					g.setColor(Color.black);
				g.fillRect(i, j, 1, 1);
			}
		}
	}
}
