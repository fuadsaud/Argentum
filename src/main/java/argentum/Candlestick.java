package argentum;

import java.util.Calendar;

public class Candlestick {

	private final double open;
	private final double close;
	private final double low;
	private final double high;
	private final double volume;
	private final Calendar date;

	public Candlestick(double open, double close, double low,
			double high, double volume, Calendar date) {
		if (open < 0 || close < 0 || low < 0 || high < 0
				|| volume < 0) {
			throw new IllegalArgumentException("No negative parameter allowed");
		} else if (high < low) {
			throw new IllegalArgumentException(
					"High value cannot be lower than low");
		} else if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}

		this.open = open;
		this.close = close;
		this.low = low;
		this.high = high;
		this.volume = volume;
		this.date = date;
	}

	public Calendar getDate() {
		return this.date;
	}

	public double getClose() {
		return this.close;
	}

	public double getHigh() {
		return this.high;
	}

	public double getLow() {
		return this.low;
	}

	public double getOpen() {
		return this.open;
	}

	public double getVolume() {
		return this.volume;
	}

	@Override
	public String toString() {
		String str = "Candlestick [Open " + open + " | Close "
				+ this.close + " | Low " + this.low + " | High "
				+ this.high + " | Volume " + this.volume + " | Date ";
		if (this.date.get(Calendar.DATE) < 10) {
			str += "0";
		}
		str += this.date.get(Calendar.DATE) + "/";
		if (this.date.get(Calendar.MONTH) + 1 < 10) {
			str += "0";
		}
		str += (this.date.get(Calendar.MONTH) + 1) + "/"
				+ this.date.get(Calendar.YEAR) + " ]";

		return str;
	}
}
