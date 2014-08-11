package argentum.indicators;

import argentum.Series;

public class SimpleMovingAverage implements Indicator {

	private final int days;
	private final Indicator indicator;

	public SimpleMovingAverage(int days,Indicator indicator) {
		this.days = days;
		this.indicator = indicator;
	}

	@Override
	public double calculate(int pos, Series series) {
		double sum = 0;

		for (int i = pos - (days - 1); i <= pos; i++) {
			sum += this.indicator.calculate(i, series);
		}

		return sum / 3;
	}
	
	@Override
	public String toString() {
		return this.indicator + " simple moving average"; 
	}
}
