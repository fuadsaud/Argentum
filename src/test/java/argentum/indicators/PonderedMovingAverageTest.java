package argentum.indicators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import argentum.Candlestick;
import argentum.Series;
import argentum.TimeSeries;
import argentum.indicators.PonderedMovingAverage;

public class PonderedMovingAverageTest {

	@Test
	public void testPonderedMedia() {
		Series series = generateSeries(1, 2, 3, 4, 5, 6);
		PonderedMovingAverage media = new PonderedMovingAverage(3, new CloseIndicator());

		assertEquals(14d / 6, media.calculate(2, series), 0.00001);
		assertEquals(20d / 6, media.calculate(3, series), 0.00001);
		assertEquals(26d / 6, media.calculate(4, series), 0.00001);
		assertEquals(32d / 6, media.calculate(5, series), 0.00001);
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
