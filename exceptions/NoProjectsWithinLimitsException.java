package exceptions;

@SuppressWarnings("serial")
public class NoProjectsWithinLimitsException extends Exception {
	
	private int lowerLimit;
	private int upperLimit;
	
	public NoProjectsWithinLimitsException(int lowerLimit, int upperLimit) {
		super();
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	public int getLowerLimit() {
		return lowerLimit;
	}

	public int getUpperLimit() {
		return upperLimit;
	}
}
