package argentum;

import java.util.Calendar;

import org.junit.Test;

public class CandlestickTest {

	@Test(expected = IllegalArgumentException.class)
	public void testMaxHigherThenMin() {
		new Candlestick(50, 70, 50, 25, 150,
				Calendar.getInstance());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeConstructorParameter() {
		new Candlestick(-50, 70, 50, 90, 150,
				Calendar.getInstance());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDateCandlestick() {
		new Candlestick(-50, 70, 50, 90, 150, null);
	}
	
	@Test
	public void testToString() {
		Candlestick c = new Candlestick(50, 70, 50, 90, 150, Calendar.getInstance());
		System.out.println(c);
	}
}
