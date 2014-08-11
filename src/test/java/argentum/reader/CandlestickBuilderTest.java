package argentum.reader;

import org.junit.Test;

public class CandlestickBuilderTest {

	@Test(expected = IllegalStateException.class)
	public void testUncompleteCandlestickGeneration() {
		CandlestickBuilder c = new CandlestickBuilder();
		
		c.open(40.0).last(60.0).low(13.0).generateCandlestick();
	}

}
