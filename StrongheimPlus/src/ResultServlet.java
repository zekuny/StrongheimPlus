// Import required java libraries
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
 
// Extend HttpServlet class
@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private String table;
	private String ttt;
	private static Connection conn = null;
	public void init() throws ServletException{
		// Do required initialization
		table = "";
		ttt = "";
		try {
			conn = DBConnection.connectDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		double gpa = 0.0;
		if(request.getParameter("gpa") != null && !request.getParameter("gpa").isEmpty()){			
			int homeWorkWeight = Integer.parseInt(request.getParameter("homeWorkWeight"));
			int quizWeight = Integer.parseInt(request.getParameter("quizWeight"));
			int testWeight = Integer.parseInt(request.getParameter("testWeight"));
			int projectWeight = Integer.parseInt(request.getParameter("projectWeight"));
			int studentID = Integer.parseInt(request.getParameter("studentID"));
			int courseID = Integer.parseInt(request.getParameter("courseID"));
			try {
				DBOperation.updateCourseByCourseID(courseID, homeWorkWeight, quizWeight, testWeight, projectWeight, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
			try {
				gpa = DBOperation.getGPAByStudentIDByCourseID(courseID, studentID, homeWorkWeight, quizWeight, testWeight, projectWeight, conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(gpa);
			//response.setContentType("text/html");			
		}
		
		ttt = request.getParameter("ttt");
		if(ttt.equals("0")){
			int studentID = Integer.parseInt(request.getParameter("studentID"));
			int courseID = Integer.parseInt(request.getParameter("courseID"));
			//System.out.println(studentID);
			ResultSet result = null;
			try {
				result = DBOperation.getAllAssignmentByCourseIDByStudentID(courseID, studentID, conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// write to table
			table = "";
			table += "<div class=\"container\">" + "<h2>Result</h2>" + "<table class=\"table table-condensed\">"
				    + "<thead>" + "<tr>" + "<th>Assignment</th>" + "<th>Type</th>" + "<th>Date</th>" + "<th>Grade</th>" + "</tr>"
				    +   "</thead>" + "<tbody>";	    
		    	try {
					while(result.next()){
						table += "<tr>";
						table += "<td>" + result.getString("assignmentName") + "</td>";
						table += "<td>" + result.getString("typeID") + "</td>";
						table += "<td>" + result.getString("tDate") + "</td>";
						table += "<td>" + result.getString("grade") + "</td>";
						table += "</tr>";
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				table += "</tbody>" + "</table>" + "</div>";
				table += "<br/><br/>";
				int[] courseTypes = new int[4];
				try {
					courseTypes = DBOperation.getAssignmentWeightByCourseID(courseID, conn);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				table += "<h3>The current weight is (you can change it at will)</h3>";
				table += "<form name = \"myForm\" class=\"form-horizontal\" method=\"post\" action=\"ResultServlet\" onsubmit=\"return validateForm()\">"
			    + "<div class=\"form-group\">"
			    + "<label for=\"Assignment\" class=\"col-sm-2 control-label\">homeWorkWeight</label>"
			    + "<div class=\"col-sm-4\">"
			    + "<input type=\"text\" class=\"form-control\" id=\"homeWorkWeight\" name=\"homeWorkWeight\" value=\"" + courseTypes[0]  + "\">"
			    + "</div>"
			    + "</div>"
			    + "<div class=\"form-group\">"
			    + "<label for=\"Assignment\" class=\"col-sm-2 control-label\">quizWeight</label>"
			    + "<div class=\"col-sm-4\">"
			    + "<input type=\"text\" class=\"form-control\" id=\"quizWeight\" name=\"quizWeight\" value=\"" + courseTypes[1]  + "\">"
			    + "</div>"
			    + "</div>"
			    + "<div class=\"form-group\">"
			    + "<label for=\"Assignment\" class=\"col-sm-2 control-label\">testWeight</label>"
			    + "<div class=\"col-sm-4\">"
			    + "<input type=\"text\" class=\"form-control\" id=\"testWeight\" name=\"testWeight\" value=\"" + courseTypes[2]  + "\">"
			    + "</div>"
			    + "</div>"
			    + "<div class=\"form-group\">"
			    + "<label for=\"Assignment\" class=\"col-sm-2 control-label\">projectWeight</label>"
			    + "<div class=\"col-sm-4\">"
			    + "<input type=\"text\" class=\"form-control\" id=\"projectWeight\" name=\"projectWeight\" value=\"" + courseTypes[3]  + "\">"
			    + "<input type=\"hidden\" id=\"gpa\" name=\"gpa\" value=\"17\">"
			    + "<input type=\"hidden\" id=\"ttt\" name=\"ttt\" value=\"0\">"
			    + "<input type=\"hidden\" id=\"studentID\" name=\"studentID\" value=\"" + studentID  + "\">"
			    + "<input type=\"hidden\" id=\"courseID\" name=\"courseID\" value=\"" + courseID  + "\">"
			    + "</div>"
			    + "</div>"
			    + "<div class=\"form-group\">"
			    + "<div class=\"col-sm-4 col-sm-offset-2\">"
			    + "<input id=\"submit\" name=\"submit\" type=\"submit\" value=\"Count!\" class=\"btn btn-primary\">"
			    + "</div>"
			    + "</div>"
			    + "</form>";
		
				//request.setAttribute("extraa", extraa);
				
				// the first is the jsp attribute, the second is the servlet attribute
				//getServletContext().getRequestDispatcher("/viewResult.jsp").forward(request, response);	
		}
		 
		if(gpa > 0){
			table += "<h1>GPA is: " + gpa + "</h1>";
		}
		request.setAttribute("table", table); 
		//request.setAttribute("extra", "<h1>extra</h1>"); 
		getServletContext().getRequestDispatcher("/viewResult.jsp").forward(request, response);
	} 
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	public void destroy(){ 
		// do nothing. 
	} 
}