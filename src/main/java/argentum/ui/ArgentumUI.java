package argentum.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import argentum.Candlestick;
import argentum.Series;
import argentum.TimeSeries;
import argentum.Trade;
import argentum.chart.ChartGenerator;
import argentum.indicators.CloseIndicator;
import argentum.indicators.OpenIndicator;
import argentum.indicators.SimpleMovingAverage;
import argentum.indicators.VolumeIndicator;
import argentum.reader.CandlestickFactory;

public class ArgentumUI {

	private class LoadXMLWorker extends SwingWorker<Object[], Void> {

		@Override
		protected Object[] doInBackground() throws Exception {
			List<Trade> trades = new XMLFileChooser().choose();
			dateFilter(trades);
			List<Candlestick> candles = new CandlestickFactory().buildCandles(trades);
			TimeSeries series = new TimeSeries(candles);

			JPanel chart = new ChartGenerator().named("Simple moving average")
					.withTimeSeries(series).from(2).to(series.getTotal() - 1)
					.withIndicator(new SimpleMovingAverage(3, new CloseIndicator())).getPanel();

			return new Object[] { trades, chart };
		}

		@Override
		protected void done() {
			try {
				Object[] returns = get();
				@SuppressWarnings("unchecked")
				List<Trade> trades = (List<Trade>) returns[0];
				JPanel grafico = (JPanel) returns[1];
				ReflectionTableModel model = new ReflectionTableModel(trades);
				table.setModel(model);
				tabs.setComponentAt(1, grafico);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}

	}

	public static void main(String[] args) {
		new ArgentumUI().show();
	}

	private JFrame mainFrame;
	private JPanel mainPanel;
	private JPanel buttonsPanel;
	private JTabbedPane tabs;
	private JFormattedTextField dateField;
	private JCheckBoxMenuItem openCheck;
	private JCheckBoxMenuItem closeCheck;

	private JCheckBoxMenuItem volumeCheck;

	private final JTable table = new JTable();

	public void show() {
		try {
			UIManager.setLookAndFeel(new javax.swing.plaf.nimbus.NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		initializeMainFrame();
		initializeMenu();
		initializeMainPanel();
		initializeTabs();
		// initializeTitle();
		initializeTable();
		initializeButtonsPanel();
		initializeDateField();
		initializeLoadButton();
		initializeExitButton();

		mainFrame.pack();
		mainFrame.setSize(300, 320);
		mainFrame.setVisible(true);
	}

	private void dateFilter(List<Trade> trades) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			Date date = format.parse((String) dateField.getValue());

			Iterator<Trade> it = trades.iterator();
			while (it.hasNext()) {
				if (it.next().getDate().before(date)) {
					it.remove();
				}
			}
		} catch (Exception e) {
			dateField.setValue(null);
		}
	}

	private void initializeButtonsPanel() {
		buttonsPanel = new JPanel(new GridLayout());
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void initializeDateField() {
		try {
			MaskFormatter mask = new MaskFormatter("##/##/####");
			mask.setPlaceholderCharacter('_');

			dateField = new JFormattedTextField(mask);
			buttonsPanel.add(dateField);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void initializeExitButton() {
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		buttonsPanel.add(exitButton);
	}

	private void initializeLoadButton() {
		JButton loadButton = new JButton("Load XML");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadXMLWorker().execute();
			}
		});

		buttonsPanel.add(loadButton);
	}

	private void initializeMainFrame() {
		mainFrame = new JFrame("Argentum");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(true);
	}

	private void initializeMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainFrame.add(mainPanel);
	}

	private void initializeMenu() {
		JMenuBar menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);

		JMenu indicatorsMenu = new JMenu("Indicators");
		menuBar.add(indicatorsMenu);

		openCheck = new JCheckBoxMenuItem("Open simple moving average");
		indicatorsMenu.add(openCheck);

		closeCheck = new JCheckBoxMenuItem("Close simple moving average");
		indicatorsMenu.add(closeCheck);

		volumeCheck = new JCheckBoxMenuItem("Volume simple moving average");
		indicatorsMenu.add(volumeCheck);
	}

	private void initializeTable() {
		table.setBorder(new LineBorder(Color.black));
		table.setGridColor(Color.black);
		table.setShowGrid(true);

		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().setBorder(null);
		scroll.getViewport().add(table);
		scroll.setSize(450, 450);

		tabs.setComponentAt(0, scroll);
	}

	private void initializeTabs() {
		tabs = new JTabbedPane();
		tabs.addTab("Trade Table", null);
		tabs.addTab("Chart", null);
		mainPanel.add(tabs);
	}

	@SuppressWarnings("unused")
	private void initializeTitle() {
		JLabel title = new JLabel("Trade List");
		title.setFont(new Font("Verdana", Font.BOLD, 25));
		title.setForeground(new Color(50, 50, 100));
		title.setHorizontalAlignment(SwingConstants.CENTER);

		mainPanel.add(title, BorderLayout.NORTH);
	}

	@SuppressWarnings("unused")
	private void loadData(List<Trade> trades) {
		Series series = new argentum.TimeSeries(new CandlestickFactory().buildCandles(trades));

		if (trades != null) {
			table.setModel(new ReflectionTableModel(trades));

			ChartGenerator gen = new ChartGenerator().named("Simple moving average")
					.withTimeSeries(series).from(2).to(series.getTotal() - 1);
			if (openCheck.isSelected()) {
				gen.withIndicator(new SimpleMovingAverage(3, new CloseIndicator()));
			}
			if (closeCheck.isSelected()) {
				gen.withIndicator(new SimpleMovingAverage(3, new OpenIndicator()));
			}
			if (volumeCheck.isSelected()) {
				gen.withIndicator(new SimpleMovingAverage(3, new VolumeIndicator()));
			}

			tabs.setComponentAt(1, gen.getPanel());
		} else {
			JOptionPane
					.showMessageDialog(null, "Cannot open file", null, JOptionPane.ERROR_MESSAGE);
		}
	}
}
