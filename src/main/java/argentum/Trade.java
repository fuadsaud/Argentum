package argentum;

import java.util.Calendar;

import argentum.ui.Column;

public final class Trade implements Comparable<Trade> {

	private final double value;
	private final int quantity;
	private final Calendar date;

	public Trade() {
		value = 10.0;
		quantity = 10;
		date = Calendar.getInstance();
	}

	public Trade(double value, int quantity, Calendar date) {
		if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		this.value = value;
		this.quantity = quantity;
		this.date = date;
	}

	@Override
	public int compareTo(Trade anotherTrade) {
		return date.compareTo(anotherTrade.getDate());
	}

	@Column(position = 2, name = "Date", format = "%1$Td/%1$Tm/%1$TY")
	public Calendar getDate() {
		return (Calendar) this.date.clone();
	}

	@Column(position = 1, name = "Quantity")
	public int getQuantity() {
		return this.quantity;
	}

	@Column(position = 0, name = "Value", format = "RS %.2f")
	public double getValue() {
		return this.value;
	}

	@Column(position = 3, name = "Volume", format = "R$ %,#.2f")
	public double getVolume() {

		return this.quantity * this.value;
	}

	@Override
	public String toString() {
		return "Trade [Value=" + this.value + " | Quantity=" + this.quantity + " | Date="
				+ this.date + "]";
	}

}
