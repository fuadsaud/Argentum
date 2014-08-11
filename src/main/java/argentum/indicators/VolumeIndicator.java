package argentum.indicators;

import argentum.Series;

public class VolumeIndicator implements Indicator {

	@Override
	public double calculate(int pos, Series series) {
		return series.getCandle(pos).getVolume();
	}

	@Override
	public String toString() {
		return "Volume indicator"; 
	}
}
