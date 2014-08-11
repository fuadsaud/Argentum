package argentum.chart;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import argentum.Candlestick;
import argentum.Series;
import argentum.TimeSeries;
import argentum.indicators.CloseIndicator;
import argentum.indicators.SimpleMovingAverage;

public class TestChart {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		Series series = generateSeries(1, 2, 3, 4, 5, 6, 7, 8, 8, 9, 9, 4, 3,
				2, 1, 2, 2, 4, 5, 6, 7, 8, 9, 10, 11, 10, 6, 3, 2, 6, 7, 8, 9);
		
		new ChartGenerator()
			.named("Close simple mean")
			.withTimeSeries(series)
			.from(3).to(10)
			.withIndicator(new SimpleMovingAverage(3, new CloseIndicator()))
			.save(new FileOutputStream("chart.png"));
	}

	private static Series generateSeries(double... values) {
		List<Candlestick> candles = new ArrayList<Candlestick>();
		for (double value : values) {
			candles.add(new Candlestick(value, value, value, value, 1000,
					Calendar.getInstance()));
		}

		return new TimeSeries(candles);
	}
}
