package argentum.reader;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import argentum.Trade;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RandomXMLGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Calendar date = Calendar.getInstance();
		Random random = new Random(123);
		List<Trade> trades = new ArrayList<Trade>();

		double value = 40;
		int quantity = 1000;

		// Generate 30 days of trades
		for (int i = 0; i < 30; i++) {

			// Each day can have 0 to 19 trades
			for (int x = 0; x < random.nextInt(20); x++) {
				// Up or down 1.00
				value += (random.nextInt(200) - 100) / 100.0;

				// Up or down 100
				quantity += (random.nextInt(200) - 100);

				Trade n = new Trade(value, quantity, date);
				trades.add(n);
			}

			date = (Calendar) date.clone();
			date.add(Calendar.DAY_OF_YEAR, 1);
		}

		XStream stream = new XStream(new DomDriver());
		stream.alias("trade", Trade.class);
		PrintStream file;
		try {
			file = new PrintStream("random.xml");
			file.print(stream.toXML(trades));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private RandomXMLGenerator() {
	}
}
