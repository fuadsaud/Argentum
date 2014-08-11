package argentum.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import argentum.Candlestick;
import argentum.Trade;

public class CandlestickFactoryTest {

	@Test
	public void testTradeSimpleOrder() {
		Calendar today = Calendar.getInstance();

		Trade trade1 = new Trade(40.5, 100, today);
		Trade trade2 = new Trade(45.0, 100, today);
		Trade trade3 = new Trade(39.8, 100, today);
		Trade trade4 = new Trade(42.3, 100, today);

		List<Trade> trades = Arrays.asList(trade1, trade2, trade3, trade4);

		CandlestickFactory fabrica = new CandlestickFactory();
		Candlestick candle = fabrica.buildCandleByDate(today, trades);

		assertEquals(40.5, candle.getOpen(), 0.00001);
		assertEquals(42.3, candle.getClose(), 0.00001);
		assertEquals(39.8, candle.getLow(), 0.00001);
		assertEquals(45.0, candle.getHigh(), 0.00001);
		assertEquals(16760.0, candle.getVolume(), 0.00001);
	}

	@Test
	public void testTradeCrescentOrder() {
		Calendar today = Calendar.getInstance();

		Trade trade1 = new Trade(39.8, 100, today);
		Trade trade2 = new Trade(40.5, 100, today);
		Trade trade3 = new Trade(42.3, 100, today);
		Trade trade4 = new Trade(45.0, 100, today);

		List<Trade> trades = Arrays.asList(trade1, trade2, trade3, trade4);

		CandlestickFactory fabrica = new CandlestickFactory();
		Candlestick candle = fabrica.buildCandleByDate(today, trades);

		assertEquals(39.8, candle.getOpen(), 0.00001);
		assertEquals(45.0, candle.getClose(), 0.00001);
		assertEquals(39.8, candle.getLow(), 0.00001);
		assertEquals(45.0, candle.getHigh(), 0.00001);
		assertEquals(16760.0, candle.getVolume(), 0.00001);
	}

	@Test
	public void testTradeDecrescentOrder() {
		Calendar today = Calendar.getInstance();

		Trade trade1 = new Trade(39.8, 100, today);
		Trade trade2 = new Trade(40.5, 100, today);
		Trade trade3 = new Trade(42.3, 100, today);
		Trade trade4 = new Trade(45.0, 100, today);

		List<Trade> trades = Arrays.asList(trade4, trade3, trade2, trade1);

		CandlestickFactory fabrica = new CandlestickFactory();
		Candlestick candle = fabrica.buildCandleByDate(today, trades);

		assertEquals(45.0, candle.getOpen(), 0.00001);
		assertEquals(39.8, candle.getClose(), 0.00001);
		assertEquals(39.8, candle.getLow(), 0.00001);
		assertEquals(45.0, candle.getHigh(), 0.00001);
		assertEquals(16760.0, candle.getVolume(), 0.00001);
	}

	@Test
	public void testNoTrades() {
		Calendar today = Calendar.getInstance();

		List<Trade> trades = Arrays.asList();

		CandlestickFactory fabrica = new CandlestickFactory();
		Candlestick candle = fabrica.buildCandleByDate(today, trades);

		assertEquals(0.0, candle.getVolume(), 0.00001);
	}

	@Test
	public void testOnlyOneTrade() {
		Calendar today = Calendar.getInstance();

		Trade trade = new Trade(40.5, 100, today);

		List<Trade> trades = Arrays.asList(trade);

		CandlestickFactory fabrica = new CandlestickFactory();
		Candlestick candle = fabrica.buildCandleByDate(today, trades);

		assertEquals(40.5, candle.getOpen(), 0.00001);
		assertEquals(40.5, candle.getClose(), 0.00001);
		assertEquals(40.5, candle.getLow(), 0.00001);
		assertEquals(40.5, candle.getHigh(), 0.00001);
		assertEquals(4050.0, candle.getVolume(), 0.00001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDateTrade() {
		new Trade(10, 5, null);
	}

	@Test
	public void testCompareDays() {
		CandlestickFactory factory = new CandlestickFactory();
		Calendar date1 = new GregorianCalendar(2008, 12, 25, 8, 30);
		Calendar date2 = new GregorianCalendar(2008, 12, 25, 10, 30);

		assertTrue(factory.areSameDay(date1, date2));
	}

	@Test
	public void testLotsOfTradesOrderedByDate() {
		Calendar today = Calendar.getInstance();

		Trade trade1 = new Trade(40.5, 100, today);
		Trade trade2 = new Trade(45.0, 100, today);
		Trade trade3 = new Trade(39.8, 100, today);
		Trade trade4 = new Trade(42.3, 100, today);

		Calendar tomorrow = (Calendar) today.clone();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);

		Trade trade5 = new Trade(48.8, 100, tomorrow);
		Trade trade6 = new Trade(49.3, 100, tomorrow);

		Calendar afterTomorrow = (Calendar) tomorrow.clone();
		afterTomorrow.add(Calendar.DAY_OF_MONTH, 1);

		Trade trade7 = new Trade(51.8, 100, afterTomorrow);
		Trade trade8 = new Trade(52.3, 100, afterTomorrow);

		List<Trade> trades = Arrays.asList(trade1, trade2, trade3, trade4,
				trade5, trade6, trade7, trade8);

		CandlestickFactory factory = new CandlestickFactory();

		List<Candlestick> candles = factory.buildCandles(trades);

		assertEquals(3, candles.size());
		assertEquals(40.5, candles.get(0).getOpen(), 0.00001);
		assertEquals(42.3, candles.get(0).getClose(), 0.00001);
		assertEquals(48.8, candles.get(1).getOpen(), 0.00001);
		assertEquals(49.3, candles.get(1).getClose(), 0.00001);
		assertEquals(51.8, candles.get(2).getOpen(), 0.00001);
	}

	@Test
	public void testLotsOfTradesNonOrderedByDate() {
		Calendar hoje = Calendar.getInstance();

		Trade trade1 = new Trade(40.5, 100, hoje);
		Trade trade2 = new Trade(45.0, 100, hoje);
		Trade trade3 = new Trade(39.8, 100, hoje);
		Trade trade4 = new Trade(42.3, 100, hoje);

		Calendar amanha = (Calendar) hoje.clone();
		amanha.add(Calendar.DAY_OF_MONTH, 1);

		Trade trade5 = new Trade(48.8, 100, amanha);
		Trade trade6 = new Trade(49.3, 100, amanha);

		Calendar depois = (Calendar) amanha.clone();
		depois.add(Calendar.DAY_OF_MONTH, 1);

		Trade trade7 = new Trade(51.8, 100, depois);
		Trade trade8 = new Trade(52.3, 100, depois);

		List<Trade> trades = Arrays.asList(trade5, trade1, trade2, trade3,
				trade4, trade6, trade7, trade8);

		CandlestickFactory factory = new CandlestickFactory();

		List<Candlestick> candles = factory.buildCandles(trades);

		assertEquals(3, candles.size());
		assertEquals(40.5, candles.get(0).getOpen(), 0.00001);
		assertEquals(42.3, candles.get(0).getClose(), 0.00001);
		assertEquals(48.8, candles.get(1).getOpen(), 0.00001);
		assertEquals(49.3, candles.get(1).getClose(), 0.00001);
		assertEquals(51.8, candles.get(2).getOpen(), 0.00001);
	}
}
