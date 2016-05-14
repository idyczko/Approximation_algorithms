
public class Variables {
	
	private boolean lowerBound;
	private boolean upperBound;
	private int lowerBoundValue;
	private int upperBoundValue;
	
	public Variables(boolean lowerBound, boolean upperBound, int lowerBoundValue, int upperBoundValue) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.lowerBoundValue = lowerBoundValue;
		this.upperBoundValue = upperBoundValue;
	}

	public boolean isLowerBound() {
		return lowerBound;
	}

	public boolean isUpperBound() {
		return upperBound;
	}

	public int getLowerBoundValue() {
		return lowerBoundValue;
	}

	public int getUpperBoundValue() {
		return upperBoundValue;
	}
	
}
