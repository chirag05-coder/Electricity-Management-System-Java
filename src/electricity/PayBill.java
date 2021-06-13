package electricity;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PayBill extends JFrame implements ActionListener {
	JLabel l1, l2, l3, l4, l5, l6;
	JLabel l11, l12, l13, l14, l15;
	JTextField t1;
	Choice c1, c2;
	JButton b1, b2;
	String meter;
	int flag = 0;
	String str;

	PayBill(String meter) {
		this.meter = meter;
		setLayout(null);

		setBounds(550, 220, 900, 600);

		JLabel title = new JLabel("Electricity Bill");
		title.setFont(new Font("Tahoma", Font.BOLD, 24));
		title.setBounds(120, 5, 400, 30);
		add(title);

		l1 = new JLabel("Meter No");
		l1.setBounds(35, 80, 200, 20);
		add(l1);

		JLabel l11 = new JLabel();
		l11.setBounds(300, 80, 200, 20);
		add(l11);

		JLabel l2 = new JLabel("Name");
		l2.setBounds(35, 140, 200, 20);
		add(l2);

		JLabel l12 = new JLabel();
		l12.setBounds(300, 140, 200, 20);
		add(l12);

		l3 = new JLabel("Month");
		l3.setBounds(35, 200, 200, 20);
		add(l3);

		c1 = new Choice();
		c1.setBounds(300, 200, 200, 20);
		c1.add("January");
		c1.add("February");
		c1.add("March");
		c1.add("April");
		c1.add("May");
		c1.add("June");
		c1.add("July");
		c1.add("August");
		c1.add("September");
		c1.add("October");
		c1.add("November");
		c1.add("December");
		add(c1);

		l4 = new JLabel("Units");
		l4.setBounds(35, 260, 200, 20);
		add(l4);

		JLabel l13 = new JLabel();
		l13.setBounds(300, 260, 200, 20);
		add(l13);

		l5 = new JLabel("Total Bill");
		l5.setBounds(35, 320, 200, 20);
		add(l5);

		JLabel l14 = new JLabel();
		l14.setBounds(300, 320, 200, 20);
		add(l14);

		l6 = new JLabel("Status");
		l6.setBounds(35, 380, 200, 20);
		add(l6);

		JLabel l15 = new JLabel();
		l15.setBounds(300, 380, 200, 20);
		l15.setForeground(Color.RED);
		add(l15);

		try {
			Conn c = new Conn();
			ResultSet rs = c.s.executeQuery("select * from customer where Meter_number = '" + meter + "'");
			while (rs.next()) {
				l11.setText(rs.getString("Meter_number"));
				l12.setText(rs.getString("Name"));
			}
			rs = c.s.executeQuery(
					"select * from bill where meter = '" + meter + "' AND Month = '" + c1.getSelectedItem() + "'");

			while (rs.next()) {

				l13.setText(rs.getString("Units"));
				l14.setText(rs.getString("total_bill"));
				l15.setText(rs.getString("status"));

			}

		} catch (Exception e) {
		}

		b1 = new JButton("Pay");
		b1.setBounds(100, 460, 100, 25);
		add(b1);
		b2 = new JButton("Back");
		b2.setBounds(230, 460, 100, 25);
		add(b2);

		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);

		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);

		c1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ae) {
				try {
					Conn c = new Conn();
					ResultSet rs = c.s.executeQuery("select * from bill where meter = '" + meter + "' AND Month = '"
							+ c1.getSelectedItem() + "'");

					if (!rs.isBeforeFirst()) {
						b1.setVisible(false);

						l13.setText("N/A");
						l14.setText("N/A");
						l15.setText("N/A");
					} else {

						b1.setVisible(true);

						while (rs.next()) {

							l13.setText(rs.getString("Units"));
							l14.setText(rs.getString("total_bill"));
							l15.setText(rs.getString("status"));
							str = rs.getString("status");
							if (str.equals("Paid")) {

								b1.setVisible(false);

							} else {
								b1.setVisible(true);

							}

						}

					}

				} catch (Exception e) {
				}
			}
		});

		ImageIcon i1 = new ImageIcon(("D:\\Java_Programs\\ElectricityManagement\\src\\electricity\\icon\\bill.png"));
		Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l21 = new JLabel(i3);
		l21.setBounds(400, 120, 600, 300);
		add(l21);

//		 else {
//			b1.setVisible(true);
//		}
		b1.addActionListener(this);
		b2.addActionListener(this);

		getContentPane().setBackground(Color.WHITE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			try {
				Conn c = new Conn();
				c.s.executeUpdate("update bill set status = 'Paid' where meter = '" + meter + "' AND Month = '"
						+ c1.getSelectedItem() + "'");

			} catch (Exception e) {
			}
			this.setVisible(false);
			new Paytm(meter).setVisible(true);

		} else if (ae.getSource() == b2) {
			this.setVisible(false);
		}
	}

	public static void main(String[] args) {
		new PayBill("954178").setVisible(true);
	}
}