package argentum.indicators;

import argentum.Series;

public interface Indicator {

	public abstract double calculate(int pos, Series series);

}