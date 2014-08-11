package argentum.indicators;

import argentum.Series;

public class CloseIndicator implements Indicator {

	@Override
	public double calculate(int pos, Series series) {
		return series.getCandle(pos).getClose();
	}

	@Override
	public String toString() {
		return "Close indicator"; 
	}
}
