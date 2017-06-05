package domain;

public enum KindRelationship {

	ACTIVITIES(0), FRIENDSHIP(1), LOVE(2);
	
	private int value;

	private KindRelationship(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
