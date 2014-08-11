package argentum;

import java.util.List;

public class TimeSeries implements Series {

	private final List<Candlestick> candles;

	public TimeSeries(List<Candlestick> candles) {
		if(candles == null) {
			throw new IllegalArgumentException("Candle list cannot be null");
		}
		this.candles = candles;
	}

	@Override
	public Candlestick getCandle(int i) {
		return this.candles.get(i);
	}

	@Override
	public int getTotal() {
		return this.candles.size();
	}
}
