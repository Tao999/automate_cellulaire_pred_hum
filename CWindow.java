import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CWindow extends JFrame {

	private CCreature[][] grid;

	JPanel m_panel;
	BufferedImage m_imgBuf;
	Graphics m_imgBufG;
	int m_width;
	int m_height;

	public CWindow() {
		grid = null;
		m_panel = null;
		m_imgBuf = null;
		m_imgBufG = null;
		m_width = 0;
		m_height = 0;
	}

	public CWindow(int width, int height) {
		m_width = width;
		m_height = height;
		grid = null;
		m_imgBuf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		m_imgBufG = m_imgBuf.getGraphics();
		setTitle("Prédateur");
		setSize(width, height);
		setResizable(false);
		m_panel = new JPanel();
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
				if (grid[j][i].isHuman()) {// définie la couleur de l'humain
					int iTemp = (int) ((((grid[j][i].getLife() - CCreature.MIN_HP_HUMAN)
							% (CCreature.MAX_HP_HUMAN - CCreature.MIN_HP_HUMAN)) * 255) / CCreature.MAX_HP_HUMAN)
							+ 0;
					// iTemp = 255;
					m_imgBufG.setColor(new Color(0, iTemp, 0));
				} else if (grid[j][i].isPredator()) {// définie la couleur du prédateur
					int iTemp = (int) ((grid[j][i].getLife() * 255) / CCreature.MAX_HP_PREDATOR);
					if (iTemp > 255)
						iTemp = 255;
					m_imgBufG.setColor(new Color(iTemp, 0, 0));
				} else
					m_imgBufG.setColor(Color.black);
				m_imgBufG.fillRect(i, j, 1, 1);// Chargement du pixel dans le buffer
			}
		}
		// affichage du buffer
		g.drawImage(m_imgBuf, 0, 0, m_width, m_height, null);
	}
}
