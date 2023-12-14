import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/updateStudentServlet")
public class UpdateStudentServlet extends HttpServlet {
	private Connection conn;
	private PreparedStatement ps;
	
	@Override
	public void init() {
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2102970_Student","e2102970","");
			ps = conn.prepareStatement("UPDATE student SET firstname = ?, lastname = ? WHERE number = ?");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		int number = Integer.parseInt(req.getParameter("number"));
		
		try {
			 // Set the values for the parameters
			ps.setString(1,  firstname);
			ps.setString(2, lastname);
			ps.setInt(3, number);
			int result = ps.executeUpdate();
			PrintWriter out = res.getWriter();
			out.println(result + " student updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		@Override
		public void destroy() {
			try {
				ps.close();
				conn.close();	
			} catch(SQLException e) {
			e.printStackTrace();
			}
		}
}