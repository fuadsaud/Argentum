package argentum.reader;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import argentum.Trade;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLReader {

	@SuppressWarnings("unchecked")
	public List<Trade> load(Reader source) {
		XStream stream = new XStream(new DomDriver());
		stream.alias("trade", Trade.class);
		return (List<Trade>) stream.fromXML(source);
	}

	@SuppressWarnings("unchecked")
	public List<Trade> load(InputStream source) {
		List<Trade> trades;
		XStream stream = new XStream(new DomDriver());
		stream.alias("trade", Trade.class);
		try {
			trades = (List<Trade>) stream.fromXML(source);
		} catch (StreamException e) {
			trades = null;
		}

		return trades;
	}
}
