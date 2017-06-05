package domain;

public enum Genre {
	
	WOMEN(0), MAN(1), OTHER(2);
	
	private int value;

	private Genre(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
