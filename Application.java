package pack;

public class Application {
	private final int NB_ROW = 5;
	private final int NB_COL = 10;
	private CCreature[][] grid = new CCreature[NB_ROW][NB_COL];

	public Application() {
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {
				if ((int) (Math.random() * 3) == 0)
					grid[i][j] = new CCreature();
				else
					grid[i][j] = null;
			}
		}
	}

	public String toString() {
		String sTemp = "";
		for (int i = 0; i < NB_ROW; i++) {
			for (int j = 0; j < NB_COL; j++) {

				if (grid[i][j] == null) {
					sTemp += "  ";
				} else if (grid[i][j].getM_type() == CCreature.m_types.HUMAN.ordinal()) {
					sTemp += "+ ";
				} else if (grid[i][j].getM_type() == CCreature.m_types.PREDATOR.ordinal()) {
					sTemp += "- ";

				}
			}
			sTemp += "\n\r";
		}
		return sTemp;
	}
}
