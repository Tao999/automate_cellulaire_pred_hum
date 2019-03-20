
public class Application {
	public final static int NB_ROW = 800; // dimension de l'écran en hauteur
	public final static int NB_COL = 800; // dimension de l'écran en largeuyr
	public final static float NB_HUMAN = 1f;// pourcentage des humains sur le terrain
	public final static float NB_PREDATOR = 0.5f;// pourcentage des prédateurs sur le terrain
	private CCreature[][] grid = new CCreature[NB_ROW][NB_COL];
	private CWindow m_window;

	private final static CCreature creaVoid = new CCreature(CCreature.m_types.NONECRE.ordinal());

	public Application() {// initialisation
		m_window = new CWindow(NB_COL, NB_ROW);

		// remplissage de la grille avec des riens
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {
				grid[i][j] = creaVoid;
			}
		}

		// remplissage de la grille des populations
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
		// ça sert à rien, mais bon, c'est la
		String sTemp = "";
		int nbHu = 0;
		int nbPr = 0;
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {

				if (grid[i][j].isNone()) {
					sTemp += "  ";
				} else if (grid[i][j].getType() == CCreature.m_types.HUMAN.ordinal()) {
					sTemp += "h ";
					nbHu++;
				} else if (grid[i][j].getType() == CCreature.m_types.PREDATOR.ordinal()) {
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
		// /!\WARNING/!\ code spaghetti
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {

				if (grid[i][j].isPredator() && !grid[i][j].isUpdated()) {
					// PREDATOR
					boolean bTemp = false;
					for (int ii = -1; ii < 2 && !bTemp; ii++) {
						// recherche d'humain
						for (int jj = -1; jj < 2 && !bTemp; jj++) {
							if (!grid[i][j].isUpdated()) {
								if (i + ii < 0)
									ii = NB_ROW - 1;
								if (j + jj < 0)
									jj = NB_COL - 1;
								if (grid[(i + ii) % NB_ROW][(j + jj) % NB_COL].isHuman()) {
									grid[i][j].eatCreature(grid[(i + ii) % NB_ROW][(j + jj) % NB_COL]);
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
						if (!grid[i][j].isUpdated()) {
							grid[i][j].progress();
							if (i + ii < 0)
								ii = NB_ROW - 1;
							if (j + jj < 0)
								jj = NB_COL - 1;
							if (grid[(i + ii) % NB_ROW][(j + jj) % NB_COL].isNone()) {
								grid[(i + ii) % NB_ROW][(j + jj) % NB_COL] = grid[i][j];
								grid[i][j] = creaVoid;
							}
						}
					}
				} else if (grid[i][j].isHuman()) {
					// HUMAN se déplace
					int ii = (int) (Math.random() * 3) - 1;
					int jj = (int) (Math.random() * 3) - 1;
					if (!grid[i][j].isUpdated()) {
						grid[i][j].progress();

						if (i + ii < 0)
							ii = NB_ROW - 1;
						if (j + jj < 0)
							jj = NB_COL - 1;
						if (grid[(i + ii) % NB_ROW][(j + jj) % NB_COL].isNone()) {
							grid[(i + ii) % NB_ROW][(j + jj) % NB_COL] = grid[i][j];
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
						// l'humain se duplique
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