package electricity;

import java.awt.Color;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class BillDetails extends JFrame {

	JTable t1;
	String x[] = { "Meter Number", "Month", "Units", "Total Bill", "Status", "Name" };
	String y[][] = new String[40][8];
	int i = 0, j = 0;

	BillDetails(String meter) {
		super("Bill Details");
		setSize(700, 650);
		setLocation(600, 150);
		setLayout(null);
		getContentPane().setBackground(Color.WHITE);

		t1 = new JTable(y, x);

		try {
			Conn c = new Conn();
			String s1 = "select * from bill where meter = '" + meter + "'";
			String s2 = "select name from customer where Meter_number = '" + meter + "'";
			ResultSet rs = c.s.executeQuery(s1);
			// rs = c.s.executeQuery(s2);

			t1.setModel(DbUtils.resultSetToTableModel(rs));

			// ResultSet rs1 = c.s.executeQuery(s2);
			// t1.setModel(DbUtils.resultSetToTableModel(rs1));

		} catch (Exception e) {
			e.printStackTrace();
		}

		JScrollPane sp = new JScrollPane(t1);
		sp.setBounds(0, 0, 700, 650);
		add(sp);

	}

	public static void main(String[] args) {
		new BillDetails("").setVisible(true);
	}

}
