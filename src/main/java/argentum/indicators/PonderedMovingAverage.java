package argentum.indicators;

import argentum.Series;

public class PonderedMovingAverage implements Indicator {

	private final int days;
	private final Indicator indicator;

	public PonderedMovingAverage(int days, Indicator indicator) {
		this.days = days;
		this.indicator = indicator;
	}

	public double calculate(int pos, Series series) {
		double sum = 0;
		int weight = 1;
		int weigthSum = 1;

		for (int i = pos - (days - 1); i <= pos; i++, weight++) {
			sum += weight * this.indicator.calculate(i, series);
			weigthSum += weight;
		}

		return sum / weigthSum;
	}

	@Override
	public String toString() {
		return this.indicator + " pondered moving average";
	}
}
