// Import required java libraries
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
 
// Extend HttpServlet class
@WebServlet("/GradeBookServlet")
public class GradeBookServlet extends HttpServlet {
	public void init() throws ServletException{
		// Do required initialization
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		String assignmentName = request.getParameter("assignmentName");
		int typeID = Integer.parseInt(request.getParameter("typeID"));
		String tmp = request.getParameter("date");
		SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
		Date date = new Date();
		try {
			date = format.parse(tmp);
		} catch (Exception e) {

		}
		double grade = Double.parseDouble(request.getParameter("grade"));
		GradeBook gb = new GradeBook(courseID, studentID, assignmentName, typeID, date, grade);
		try {
			gb.writeData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/Result.jsp").forward(request, response);
	} 
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	public void destroy(){ 
		// do nothing. 
	} 	
}