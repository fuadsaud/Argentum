package argentum.chart;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import argentum.Series;
import argentum.indicators.Indicator;

public class ChartGenerator {

	private Series series;
	private int start;
	private int end;
	private DefaultCategoryDataset dataset;
	private JFreeChart chart;

	public ChartGenerator named(String title) {
		this.dataset = new DefaultCategoryDataset();
		this.chart = ChartFactory.createLineChart(title, "Days", "Values",
				dataset, PlotOrientation.VERTICAL, true, true, false);
		return this;
	}

	public ChartGenerator withTimeSeries(Series series) {
		this.series = series;
		return this;
	}

	public ChartGenerator from(int start) {
		this.start = start;
		return this;
	}

	public ChartGenerator to(int end) {
		this.end = end;
		return this;
	}

	public ChartGenerator withIndicator(Indicator indicator) {
		for (int i = start; i <= end; i++) {
			double value = indicator.calculate(i, series);
			dataset.addValue(value, indicator.toString(), "" + i);
		}
		return this;
	}

	public ChartGenerator save(OutputStream output) throws IOException {
		ChartUtilities.writeChartAsPNG(output, chart, 500, 350);
		return this;
	}

	public JPanel getPanel() {
		return new ChartPanel(chart);
	}
}
