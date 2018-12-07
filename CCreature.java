package pack;

public class CCreature {
	public enum m_types {
		HUMAN, PREDATOR
	};

	public final int MAX_HP_HUMAN = 10;
	public final int MAX_HP_PREDATOR = 10;

	private int m_type;
	private int m_life;

	public CCreature() {
		m_type = (int) (Math.random() * m_types.values().length);
		if (m_type == m_types.HUMAN.ordinal()) {
			m_life = 1;
		} else if (m_type == m_types.PREDATOR.ordinal()) {
			m_life = MAX_HP_PREDATOR;
		}
	}

	public CCreature(int type) {
		m_type = type;
		if (m_type == m_types.HUMAN.ordinal()) {
			m_life = 1;
		} else if (m_type == m_types.PREDATOR.ordinal()) {
			m_life = MAX_HP_PREDATOR;
		}
	}

	public int getM_type() {
		return m_type;
	}

	public int getM_life() {
		return m_life;
	}

	public boolean Progress() {// retourne la direction de déplacement

		if (m_type == m_types.HUMAN.ordinal()) {
			m_life++;
			if (m_life == MAX_HP_HUMAN) {
				m_life = 1;
				return false;
			}
		} else if (m_type == m_types.PREDATOR.ordinal()) {
			m_life--;
			if (m_life == 0)
				return false;
		}

		return true;
	}

	public boolean IsDead() {
		if (m_type == m_types.PREDATOR.ordinal()) {
			if (m_life <= 0)
				return true;
		}
		return false;
	}

	public void IncreaseLifeBy(int life) {
		if (m_type == m_types.PREDATOR.ordinal())
			m_life += life;
	}

	public String toString() {
		return "type : " + m_type + "\nlife : " + m_life;
	}
}
