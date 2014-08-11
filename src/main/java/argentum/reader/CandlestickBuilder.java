package argentum.reader;

import java.util.Calendar;

import argentum.Candlestick;

public class CandlestickBuilder {

	private double open;
	private double close;
	private double low;
	private double high;
	private double volume;
	private Calendar date;

	private boolean[] checkData = new boolean[6];

	public CandlestickBuilder open(double open) {
		this.open = open;
		this.checkData[0] = true;
		return this;
	}

	public CandlestickBuilder last(double close) {
		this.close = close;
		this.checkData[1] = true;
		return this;
	}

	public CandlestickBuilder low(double low) {
		this.low = low;
		this.checkData[2] = true;
		return this;
	}

	public CandlestickBuilder high(double high) {
		this.high = high;
		this.checkData[3] = true;
		return this;
	}

	public CandlestickBuilder volume(double volume) {
		this.volume = volume;
		this.checkData[4] = true;
		return this;
	}

	public CandlestickBuilder date(Calendar date) {
		this.date = date;
		this.checkData[5] = true;
		return this;
	}

	public Candlestick generateCandlestick() {
		for (boolean check : this.checkData) {
			if (!check) {
				throw new IllegalStateException(
						"You need fill all blanks to create a candlestick");
			}
		}
		return new Candlestick(open, close, low, high, volume, date);
	}
}
