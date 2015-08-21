import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class GradeBook {
	private int courseID;
	private int studentID;
	private String assignmentName;
	private int typeID;
	private Date date;
	private double grade;
	Connection conn;
	
	public GradeBook(){
		courseID = 0;
		studentID = 0;
		assignmentName = "";
		typeID = 0;
		date = new Date();
		grade = 0.0;
		try {
			conn = DBConnection.connectDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GradeBook(int cid, int id, String n, int tid, Date d, double g){
		courseID = cid;
		studentID = id;
		assignmentName = n;
		typeID = tid;
		date = d;
		grade = g;
		try {
			conn = DBConnection.connectDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDateString() {
		String s = date.toString();
		String[] tmp = s.split(" ");
		return tmp[2] + " " + tmp[1] + " " + tmp[5];
	}
	
	// Write data into database
	public void writeData() throws SQLException{
		String sql = "insert into gradebook (courseID, studentID, assignmentName, typeID, \"date\", grade) values (" + courseID + ", " + studentID + ", '" + assignmentName + "', " + typeID + ",  to_date('" + getDateString() + "', 'DD Mon yyyy'), " + grade + ")";
		PreparedStatement preStatement = conn.prepareStatement(sql);
		preStatement.executeQuery();
	}


}
