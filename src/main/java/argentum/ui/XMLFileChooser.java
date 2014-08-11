package argentum.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import argentum.Trade;
import argentum.reader.XMLReader;

public class XMLFileChooser {

	public List<Trade> choose() {
		try {
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setFileFilter(new FileNameExtensionFilter(
					"XML Document", "xml"));
			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				InputStream file = new FileInputStream(
						fileChooser.getSelectedFile());
				return new XMLReader().load(file);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public static void main(String[] args) {
		new XMLFileChooser().choose();
	}
}
