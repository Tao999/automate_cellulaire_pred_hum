
public class CCreature {
	public enum m_types {// les types de créature
		NONECRE, HUMAN, PREDATOR
	};

	public final static int MIN_HP_HUMAN = 1;
	public final static int MAX_HP_HUMAN = 30;
	public final static int MAX_HP_PREDATOR = 30;
	public final static int OFFSET_MAX_HP_HUMAN = 10;

	private int m_type;
	private int m_life;
	private boolean m_lastUpdate;

	public CCreature() {
		// constructeur aléatoire
		m_type = (int) (Math.random() * m_types.values().length);
		if (isHuman()) {
			m_life = MIN_HP_HUMAN + (int) (Math.random() * MAX_HP_HUMAN);
		} else if (isPredator()) {
			m_life = MAX_HP_PREDATOR;
		}
		m_lastUpdate = false;
	}

	public CCreature(int type) {
		m_type = type;
		if (isHuman()) {
			m_life = MIN_HP_HUMAN + (int) (Math.random() * MAX_HP_HUMAN);
		} else if (isPredator()) {
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

	public int getType() {
		return m_type;
	}

	public int getLife() {
		return m_life;
	}

	public void setType(int type) {
		m_type = type;
	}

	public void progress() {
		// augmente la vie pour les humains, et baisse pour les prédateurs
		if (isHuman()) {
			m_life++;
		} else if (isPredator()) {
			m_life--;
		}
		m_lastUpdate = true;
	}

	public boolean isReachState() {
		// pour savoir si la créa à attend son état
		if (isPredator()) {
			if (m_life <= 0)
				return true;
		} else if (isHuman()) {
			if (m_life >= MAX_HP_HUMAN) {
				m_life = MIN_HP_HUMAN + (int) (Math.random() * OFFSET_MAX_HP_HUMAN);
				return true;
			}
		}
		return false;
	}

	public void increaseLifeBy(int life) {
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
		increaseLifeBy((int) (c.getLife()));
		c.setType(CCreature.m_types.PREDATOR.ordinal());
	}
}
