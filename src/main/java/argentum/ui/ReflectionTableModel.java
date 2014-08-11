package argentum.ui;

import java.lang.reflect.Method;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import argentum.Trade;

@SuppressWarnings("serial")
public class ReflectionTableModel extends AbstractTableModel {

	private List<?> list;
	private Class<?> type;

	public ReflectionTableModel(List<Trade> list) {
		this.list = list;
		this.type = list.get(0).getClass();
	}

	@Override
	public int getColumnCount() {
		int columns = 0;
		for (Method method : type.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Column.class)) {
				columns++;
			}
		}
		return columns;
	}

	@Override
	public int getRowCount() {
		return this.list.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		try {
			Object object = this.list.get(row);
			for (Method method : type.getDeclaredMethods()) {
				Column c = method.getAnnotation(Column.class);
				if (c != null && c.position() == column) {
					return String.format(c.format(), method.invoke(object));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		for (Method method : type.getDeclaredMethods()) {
			Column c = method.getAnnotation(Column.class);
			if (c != null && c.position() == column) {
				return c.name();
			}
		}
		return null;
	}

}
