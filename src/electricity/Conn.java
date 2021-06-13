/**
 * 
 */
package electricity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author Acer
 *
 */
public class Conn {
	Connection c;
	Statement s;

	public Conn() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql:///emp_details", "root", "");
			s = c.createStatement();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
