package argentum.reader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import argentum.Candlestick;
import argentum.Trade;

public class CandlestickFactory {

	public List<Candlestick> buildCandles(List<Trade> trades) {
		if (trades == null) {
			throw new IllegalArgumentException("Trades list cannot be null");
		} else if (trades.isEmpty()) {
			throw new IllegalArgumentException("Trades list cannot be empty");
		}

		Collections.sort(trades);
		List<Candlestick> candles = new ArrayList<Candlestick>();
		List<Trade> sameDayTrades = new ArrayList<Trade>();
		Calendar actualCandleDate = trades.get(0).getDate();

		for (Trade trade : trades) {
			if (!areSameDay(trade.getDate(), actualCandleDate)) {
				closeCandle(candles, sameDayTrades, actualCandleDate);

				sameDayTrades = new ArrayList<Trade>();
				actualCandleDate = trade.getDate();
			}

			sameDayTrades.add(trade);
		}

		closeCandle(candles, sameDayTrades, actualCandleDate);

		return candles;
	}

	private void closeCandle(List<Candlestick> candles,
			List<Trade> sameDayTrades, Calendar actualCandleDate) {
		candles.add(buildCandleByDate(actualCandleDate, sameDayTrades));
	}

	@Deprecated
	public List<Candlestick> buildCandlesInAnyOrder(List<Trade> trades) {
		if (trades == null) {
			throw new IllegalArgumentException("Trades list cannot be null");
		} else if (trades.isEmpty()) {
			throw new IllegalArgumentException("Trades list cannot be empty");
		}

		List<Candlestick> candles = new ArrayList<Candlestick>();
		List<List<Trade>> temp = new ArrayList<List<Trade>>();

		// Adds first trade on the list
		temp.add(new ArrayList<Trade>());
		temp.get(0).add(trades.get(0));

		// Organize the trades by date, on the temp list
		outer: for (int i = 1; i < trades.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (areSameDay(trades.get(i).getDate(), temp.get(j).get(0)
						.getDate())) {
					temp.get(j).add(trades.get(i));
					continue outer;
				}
			}

			temp.add(new ArrayList<Trade>());
			temp.get(temp.size() - 1).add(trades.get(i));

			System.gc();
		}

		for (int i = 0; i < temp.size() - 1; i++) {
			if (temp.get(i).get(0).getDate()
					.after(temp.get(i + 1).get(0).getDate())) {
				List<Trade> swap = temp.get(i);
				temp.set(i, temp.get(i + 1));
				temp.set(i + 1, swap);
			}
		}

		for (List<Trade> sameDayTrades : temp) {
			candles.add(buildCandleByDate(sameDayTrades.get(0).getDate(),
					sameDayTrades));
		}

		return candles;
	}

	public Candlestick buildCandleByDate(Calendar date, List<Trade> trades) {
		if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		} else if (trades == null) {
			throw new IllegalArgumentException("Trade list cannot be null");
		}
		double high = trades.isEmpty() ? 0 : Double.MIN_VALUE;
		double low = trades.isEmpty() ? 0 : Double.MAX_VALUE;
		double open = trades.isEmpty() ? 0 : trades.get(0).getValue();
		double close = trades.isEmpty() ? 0 : trades.get(trades.size() - 1)
				.getValue();
		double volume = 0;

		for (Trade trade : trades) {
			volume += trade.getVolume();

			if (trade.getValue() > high) {
				high = trade.getValue();
			}

			if (trade.getValue() < low) {
				low = trade.getValue();
			}
		}

		return new Candlestick(open, close, low, high, volume, date);
	}

	public boolean areSameDay(Calendar date1, Calendar date2) {
		return date1.get(Calendar.DAY_OF_MONTH) == date2
				.get(Calendar.DAY_OF_MONTH)
				&& date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
				&& date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
	}
}
