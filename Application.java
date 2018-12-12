package pack;

public class Application {
	private final int NB_ROW = 1000;
	private final int NB_COL = 1000;
	private final int NB_HUMAN = 10;// pourcentage
	private final int NB_PREDATOR = 1;// pourcentage
	private CCreature[][] grid = new CCreature[NB_ROW][NB_COL];
	private CWindow m_window;

	private final CCreature creaVoid = new CCreature(CCreature.m_types.NONECRE.ordinal());

	public Application() {// initialisation
		m_window = new CWindow(NB_COL, NB_ROW);
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {
				grid[i][j] = creaVoid;
			}
		}
		int nbIt = (int) (NB_COL * NB_ROW * NB_HUMAN) / 100;
		while (nbIt > 0) {
			int iTemp = (int) (Math.random() * NB_ROW);
			int jTemp = (int) (Math.random() * NB_COL);
			if (grid[iTemp][jTemp].isNone()) {
				grid[iTemp][jTemp] = new CCreature(CCreature.m_types.HUMAN.ordinal());
				nbIt--;
			}
		}

		nbIt = (int) (NB_COL * NB_ROW * NB_PREDATOR) / 100;
		while (nbIt > 0) {
			int iTemp = (int) (Math.random() * NB_ROW);
			int jTemp = (int) (Math.random() * NB_COL);
			if (grid[iTemp][jTemp].isNone()) {
				grid[iTemp][jTemp] = new CCreature(CCreature.m_types.PREDATOR.ordinal());
				nbIt--;
			}
		}
	}

	public void updateWindow() {
		m_window.update(grid);
	}

	public String toString() {
		String sTemp = "";
		int nbHu = 0;
		int nbPr = 0;
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {

				if (grid[i][j].isNone()) {
					sTemp += "  ";
				} else if (grid[i][j].getM_type() == CCreature.m_types.HUMAN.ordinal()) {
					sTemp += "h ";
					nbHu++;
				} else if (grid[i][j].getM_type() == CCreature.m_types.PREDATOR.ordinal()) {
					sTemp += "p ";
					nbPr++;
				}
			}
			sTemp += "\n\r";
		}
		sTemp += "humain " + nbHu + "| predator " + nbPr;
		return sTemp;
	}

	public void progress() {
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {

				if (grid[i][j].isPredator() && !grid[i][j].isUpdated()) {
					// PREDATOR
					boolean bTemp = false;
					for (int ii = -1; ii < 2 && !bTemp; ii++) {
						// recherche d'humain
						for (int jj = -1; jj < 2 && !bTemp; jj++) {
							if (ii + i >= 0 && ii + i < NB_ROW && jj + j >= 0 && jj + j < NB_COL
									&& !grid[i][j].isUpdated()) {
								if (grid[ii + i][jj + j].isHuman()) {
									grid[i][j].eatCreature(grid[ii + i][jj + j]);
									grid[i][j].progress();
									bTemp = true;
								}
							}
						}
					}

					if (!bTemp) {
						// si pas d'humain, il se déplace
						int ii = (int) (Math.random() * 3) - 1;
						int jj = (int) (Math.random() * 3) - 1;
						if (i + ii >= 0 && i + ii < NB_ROW && j + jj >= 0 && j + jj < NB_COL
								&& !grid[i][j].isUpdated()) {
							grid[i][j].progress();
							if (grid[i + ii][j + jj].isNone()) {
								grid[ii + i][jj + j] = grid[i][j];
								grid[i][j] = creaVoid;
							}
						}
					}
				} else if (grid[i][j].isHuman()) {
					// HUMAN se déplace
					int ii = (int) (Math.random() * 3) - 1;
					int jj = (int) (Math.random() * 3) - 1;
					if (i + ii >= 0 && i + ii < NB_ROW && j + jj >= 0 && j + jj < NB_COL && !grid[i][j].isUpdated()) {
						grid[i][j].progress();
						if (grid[i + ii][j + jj].isNone()) {
							grid[ii + i][jj + j] = grid[i][j];
							grid[i][j] = creaVoid;
						}
					}
				}
			}
		}
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {
				// ÉTAT spéciale
				if (grid[i][j].isReachState()) {
					if (grid[i][j].isHuman()) {
						// l'hmain se duplique
						int ii = (int) (Math.random() * 2) * 2 - 1;
						int jj = (int) (Math.random() * 2) * 2 - 1;
						if (i + ii >= 0 && i + ii < NB_ROW && j + jj >= 0 && j + jj < NB_COL) {
							if (grid[i + ii][j + jj].isNone())
								grid[ii + i][jj + j] = new CCreature(CCreature.m_types.HUMAN.ordinal());
						}
					} else if (grid[i][j].isPredator()) {
						// le prédateur meurt
						grid[i][j] = creaVoid;
					}
				}
				grid[i][j].clrUpdate();
			}
		}
	}
}