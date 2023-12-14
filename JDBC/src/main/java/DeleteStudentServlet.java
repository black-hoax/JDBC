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
@WebServlet("/deleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {
	private Connection conn;
	private PreparedStatement ps;
	
	@Override
	public void init() {
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/ e2102970_Student","e2102970","");
			ps = conn.prepareStatement("DELETE FROM student WHERE number = ?");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int number = Integer.parseInt(req.getParameter("number"));
		
		try {
			ps.setInt(1, number);
			int result = ps.executeUpdate();
			PrintWriter out = res.getWriter();
			out.println(result + " student record deleted");
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