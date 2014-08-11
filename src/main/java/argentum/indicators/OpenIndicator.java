package argentum.indicators;

import argentum.Series;

public class OpenIndicator implements Indicator {

	@Override
	public double calculate(int pos, Series series) {
		return series.getCandle(pos).getOpen();
	}

	@Override
	public String toString() {
		return "Open indicator"; 
	}
}
