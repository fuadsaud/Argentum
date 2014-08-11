package argentum.indicators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import argentum.Candlestick;
import argentum.Series;
import argentum.TimeSeries;
import argentum.indicators.SimpleMovingAverage;

public class SimpleMovingAverageTest {

	@Test
	public void testSimpleMedia() {
		Series series = generateSeries(1, 2, 3, 4, 3, 4, 5, 4, 3);
		Indicator media = new SimpleMovingAverage(3, new CloseIndicator());

		assertEquals(2.0, media.calculate(2, series), 0.00001);
		assertEquals(3.0, media.calculate(3, series), 0.00001);
		assertEquals(10.0 / 3, media.calculate(4, series), 0.00001);
		assertEquals(11.0 / 3, media.calculate(5, series), 0.00001);
		assertEquals(4.0, media.calculate(6, series), 0.00001);
		assertEquals(13.0 / 3, media.calculate(7, series), 0.00001);
		assertEquals(4.0, media.calculate(8, series), 0.00001);
	}

	private Series generateSeries(double... values) {
		List<Candlestick> candles = new ArrayList<Candlestick>();
		for (double value : values) {
			candles.add(new Candlestick(value, value, value, value, 1000,
					Calendar.getInstance()));
		}

		return new TimeSeries(candles);
	}
}
