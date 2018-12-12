package pack;

public class Application {
	private final int NB_ROW = 1000;
	private final int NB_COL = 1500;
	private final int NB_HUMAN = 10;// pourcentage
	private final int NB_PREDATOR = 10;// pourcentage
	private CCreature[][] grid = new CCreature[NB_ROW][NB_COL];
	private CWindow m_window;

	public Application() {
		m_window = new CWindow(NB_COL, NB_ROW);
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {
				grid[i][j] = new CCreature(CCreature.m_types.NONECRE.ordinal());
			}
		}
		int nbIt = (int) (NB_COL * NB_ROW * NB_HUMAN) / 100;
		while (nbIt > 0) {
			int iTemp = (int) (Math.random() * NB_ROW);
			int jTemp = (int) (Math.random() * NB_COL);
			if (grid[iTemp][jTemp].getM_type() == CCreature.m_types.NONECRE.ordinal()) {
				grid[iTemp][jTemp] = new CCreature(CCreature.m_types.HUMAN.ordinal());
				nbIt--;
			}
		}

		nbIt = (int) (NB_COL * NB_ROW * NB_PREDATOR) / 100;
		while (nbIt > 0) {
			int iTemp = (int) (Math.random() * NB_ROW);
			int jTemp = (int) (Math.random() * NB_COL);
			if (grid[iTemp][jTemp].getM_type() == CCreature.m_types.NONECRE.ordinal()) {
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

				if (grid[i][j].getM_type() == CCreature.m_types.NONECRE.ordinal()) {
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

				if (grid[i][j].getM_type() == CCreature.m_types.PREDATOR.ordinal()) {
					// PREDATOR
					boolean bTemp = false;
					for (int ii = -1; ii < 2 && !bTemp; ii++) {// recherche
																// d'humain
						for (int jj = -1; jj < 2 && !bTemp; jj++) {
							if (ii + i >= 0 && ii + i < NB_ROW && jj + j >= 0 && jj + j < NB_COL
									&& !grid[i][j].isUpdated()) {
								if (grid[ii + i][jj + j].getM_type() == CCreature.m_types.HUMAN.ordinal()) {
									grid[i][j].progress();
									grid[i][j].increaseLifeBy(grid[ii + i][jj + j].getM_life());
									grid[ii + i][jj + j].setM_type(CCreature.m_types.PREDATOR.ordinal());
									bTemp = true;
								}
							}
						}
					}
					if (!bTemp) {// pas d'humain, il se déplace
						int ii = (int) (Math.random() * 3) - 1;
						int jj = (int) (Math.random() * 3) - 1;
						if (i + ii >= 0 && i + ii < NB_ROW && j + jj >= 0 && j + jj < NB_COL
								&& !grid[i][j].isUpdated()) {
							grid[i][j].progress();
							if (grid[i + ii][j + jj].getM_type() == CCreature.m_types.NONECRE.ordinal())
								grid[ii + i][jj + j] = grid[i][j];
							grid[i][j] = new CCreature(CCreature.m_types.NONECRE.ordinal());
						}
					}
				} else if (grid[i][j].getM_type() == CCreature.m_types.HUMAN.ordinal()) {
					// HUMAN
					int ii = (int) (Math.random() * 3) - 1;
					int jj = (int) (Math.random() * 3) - 1;
					if (i + ii >= 0 && i + ii < NB_ROW && j + jj >= 0 && j + jj < NB_COL && !grid[i][j].isUpdated()) {
						grid[i][j].progress();
						if (grid[i + ii][j + jj].getM_type() == CCreature.m_types.NONECRE.ordinal()) {
							grid[ii + i][jj + j] = grid[i][j];
							grid[i][j] = new CCreature(CCreature.m_types.NONECRE.ordinal());
						}
					}
				}

			}
		}
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {

				if (grid[i][j].isReachState()) {
					if (grid[i][j].getM_type() == CCreature.m_types.HUMAN.ordinal()) {
						int ii = (int) (Math.random() * 2) * 2 - 1;
						int jj = (int) (Math.random() * 2) * 2 - 1;
						if (i + ii >= 0 && i + ii < NB_ROW && j + jj >= 0 && j + jj < NB_COL) {
							//grid[i][j].progress();
							if (grid[i + ii][j + jj].getM_type() == CCreature.m_types.NONECRE.ordinal())
								grid[ii + i][jj + j] = new CCreature(CCreature.m_types.HUMAN.ordinal());
						}
					} else if (grid[i][j].getM_type() == CCreature.m_types.PREDATOR.ordinal()) {
						grid[i][j] = new CCreature(CCreature.m_types.NONECRE.ordinal());
					}
				}
				grid[i][j].clrUpdate();

			}
		}
	}
}

/*
 * if (grid[i][j].getM_type() == CCreature.m_types.HUMAN.ordinal() &&
 * !grid[i][j].isUpdated()) { switch ((int) (Math.random() * 3)) { case 0://
 * move up if (i - 1 >= 0) if (grid[i - 1][j].getM_type() ==
 * CCreature.m_types.NONECRE.ordinal()) { grid[i][j].progress(); grid[i - 1][j]
 * = grid[i][j]; grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; case 1:// move right
 * if (j + 1 < NB_COL) if (grid[i][j + 1].getM_type() ==
 * CCreature.m_types.NONECRE.ordinal()) { grid[i][j].progress(); grid[i][j + 1]
 * = grid[i][j]; grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; case 2:// move down
 * if (i + 1 < NB_ROW) if (grid[i + 1][j].getM_type() ==
 * CCreature.m_types.NONECRE.ordinal()) { grid[i][j].progress(); grid[i + 1][j]
 * = grid[i][j]; grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; case 3:// move left
 * if (j - 1 >= 0) if (grid[i][j - 1].getM_type() ==
 * CCreature.m_types.NONECRE.ordinal()) { grid[i][j].progress(); grid[i][j - 1]
 * = grid[i][j]; grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; } } else if
 * (grid[i][j].getM_type() == CCreature.m_types.PREDATOR.ordinal()) { if (i - 1
 * != 0) if (grid[i - 1][j].getM_type() == CCreature.m_types.HUMAN.ordinal()) {
 * grid[i][j].increaseLifeBy(grid[i - 1][j].getM_life()); grid[i - 1][j] =
 * grid[i][j]; grid[i][j] = new CCreature(CCreature.m_types.NONECRE.ordinal());
 * } if (j + 1 < NB_COL) if (grid[i][j + 1].getM_type() ==
 * CCreature.m_types.HUMAN.ordinal()) { grid[i][j].increaseLifeBy(grid[i][j +
 * 1].getM_life()); grid[i][j + 1] = grid[i][j]; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } if (j - 1 != 0) if
 * (grid[i][j - 1].getM_type() == CCreature.m_types.HUMAN.ordinal()) {
 * grid[i][j].increaseLifeBy(grid[i][j - 1].getM_life()); grid[i][j - 1] =
 * grid[i][j]; grid[i][j] = new CCreature(CCreature.m_types.NONECRE.ordinal());
 * } if (i + 1 < NB_COL) if (grid[i + 1][j].getM_type() ==
 * CCreature.m_types.HUMAN.ordinal()) { grid[i][j].increaseLifeBy(grid[i +
 * 1][j].getM_life()); grid[i + 1][j] = grid[i][j]; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } switch ((int)
 * (Math.random() * 3)) { case 0:// move up if (i - 1 >= 0) if (grid[i -
 * 1][j].getM_type() != CCreature.m_types.PREDATOR.ordinal()) { if (grid[i -
 * 1][j].getM_type() == CCreature.m_types.HUMAN.ordinal())
 * grid[i][j].increaseLifeBy(grid[i - 1][j].getM_life()); grid[i][j].progress();
 * grid[i - 1][j] = grid[i][j]; grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; case 1:// move right
 * if (j + 1 < NB_COL) if (grid[i][j + 1].getM_type() !=
 * CCreature.m_types.PREDATOR.ordinal()) { if (grid[i][j + 1].getM_type() ==
 * CCreature.m_types.HUMAN.ordinal()) grid[i][j].increaseLifeBy(grid[i][j +
 * 1].getM_life()); grid[i][j].progress(); grid[i][j + 1] = grid[i][j];
 * grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; case 2:// move down
 * if (i + 1 < NB_ROW) if (grid[i + 1][j].getM_type() !=
 * CCreature.m_types.PREDATOR.ordinal()) { if (grid[i + 1][j].getM_type() ==
 * CCreature.m_types.HUMAN.ordinal()) grid[i][j].increaseLifeBy(grid[i +
 * 1][j].getM_life()); grid[i][j].progress(); grid[i + 1][j] = grid[i][j];
 * grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; case 3:// move left
 * if (j - 1 >= 0) if (grid[i][j - 1].getM_type() !=
 * CCreature.m_types.PREDATOR.ordinal()) { if (grid[i][j - 1].getM_type() ==
 * CCreature.m_types.HUMAN.ordinal()) grid[i][j].increaseLifeBy(grid[i][j -
 * 1].getM_life()); grid[i][j].progress(); grid[i][j - 1] = grid[i][j];
 * grid[i][j] = null; grid[i][j] = new
 * CCreature(CCreature.m_types.NONECRE.ordinal()); } break; } }
 */