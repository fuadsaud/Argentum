package argentum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class TimeSeriesTest {

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInvalidIndex() {
		List<Candlestick> candles = new ArrayList<Candlestick>();
		candles.add(new Candlestick(50, 60, 50, 60, 1000, Calendar.getInstance()));
		candles.add(new Candlestick(50, 60, 40, 70, 500, Calendar.getInstance()));
		new TimeSeries(candles).getCandle(3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullConstructorParameter() {
		new TimeSeries(null);
	}

}
