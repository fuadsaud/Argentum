package argentum;

import java.util.Calendar;

public class MockSeries implements Series {

	private double[] values;

	public MockSeries(double... values) {
		this.values = values;
	}

	@Override
	public Candlestick getCandle(int i) {
		return new Candlestick(values[i], values[i], values[i], values[i],
				1000, Calendar.getInstance() );
	}

	@Override
	public int getTotal() {
		return this.values.length;
	}

}
