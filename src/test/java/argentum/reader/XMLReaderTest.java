package argentum.reader;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import argentum.Trade;

public class XMLReaderTest {

	@Test
	public void testXMLReaderLoadTradeList() {
		String xml = "<list>" +
						"<trade>" +
							"<value>43.5</value>" +
							"<quantity>1000</quantity>" +
							"<date>" +
								"<time>555454646</time>" +
							"</date>" +
						"</trade>" +
					"</list>";

		XMLReader reader = new XMLReader();
		List<Trade> list = reader.load(new StringReader(xml));

		assertEquals(1, list.size());
		assertEquals(43.5, list.get(0).getValue(), 0.00001);
		assertEquals(1000, list.get(0).getQuantity());
	}
}
