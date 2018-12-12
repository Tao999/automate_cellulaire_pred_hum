package pack;

public class CCreature {
	public enum m_types {
		NONECRE, HUMAN, PREDATOR
	};

	public final int MIN_HP_HUMAN = 1;
	public final int MAX_HP_HUMAN = 5;
	public final int MAX_HP_PREDATOR = 10;

	private int m_type;
	private int m_life;
	private boolean m_lastUpdate;

	public CCreature() {
		m_type = (int) (Math.random() * m_types.values().length);
		if (m_type == m_types.HUMAN.ordinal()) {
			m_life = MIN_HP_HUMAN;
		} else if (m_type == m_types.PREDATOR.ordinal()) {
			m_life = MAX_HP_PREDATOR;
		}
		m_lastUpdate = false;
	}

	public CCreature(int type) {
		m_type = type;
		if (m_type == m_types.HUMAN.ordinal()) {
			m_life = MIN_HP_HUMAN;
		} else if (m_type == m_types.PREDATOR.ordinal()) {
			m_life = MAX_HP_PREDATOR;
		}
		m_lastUpdate = false;
	}

	public boolean isUpdated() {
		return m_lastUpdate;
	}

	public void clrUpdate() {
		m_lastUpdate = false;
	}

	public void setUpdate() {
		m_lastUpdate = true;
	}

	public int getM_type() {
		return m_type;
	}

	public int getM_life() {
		return m_life;
	}

	public void setM_type(int type) {
		m_type = type;
	}

	public void progress() {
		//augmente la vie pour les humains, et baisse pour les prédateurs
		if (m_type == m_types.HUMAN.ordinal()) {
			m_life++;
		} else if (m_type == m_types.PREDATOR.ordinal()) {
			m_life--;
		}
		m_lastUpdate = true;
	}

	public boolean isReachState() {
		//pour savoir si la créa à attend son état
		if (m_type == m_types.PREDATOR.ordinal()) {
			if (m_life == 0)
				return true;
		} else if (m_type == m_types.HUMAN.ordinal()) {
			if (m_life == MAX_HP_HUMAN) {
				m_life = MIN_HP_HUMAN;
				return true;
			}
		}
		return false;
	}

	public void increaseLifeBy(int life) {
		if (m_type == m_types.PREDATOR.ordinal())
			m_life += life;
	}

	public String toString() {
		return "type : " + m_type + " life : " + m_life;
	}

	public boolean isHuman() {
		if (m_type == m_types.HUMAN.ordinal())
			return true;
		return false;
	}

	public boolean isPredator() {
		if (m_type == m_types.PREDATOR.ordinal())
			return true;
		return false;
	}

	public boolean isNone() {
		if (m_type == m_types.NONECRE.ordinal())
			return true;
		return false;
	}

	public void eatCreature(CCreature c) {
		c.setUpdate();
		increaseLifeBy((int) (c.getM_life() / 2));
		c.setM_type(CCreature.m_types.PREDATOR.ordinal());
	}
}
