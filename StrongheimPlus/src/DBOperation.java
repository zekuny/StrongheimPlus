import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperation {
	// getAllAssignmentByCourseIDByStudentID
	public static ResultSet getAllAssignmentByCourseIDByStudentID(int cid, int id, Connection conn) throws SQLException{
		String sql = "select studentID, assignmentName, typeID, TO_CHAR(\"date\",\'YYYY-MM-DD\') as tDate, grade, courseID from gradebookplus where studentID = " + id + " and courseID = " + cid;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		return result;
	}
	// All assignment weight by courseID
	public static int[] getAssignmentWeightByCourseID(int cid, Connection conn) throws SQLException{
		String sql = "select * from course where courseID = " + cid;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		int[] res = new int[4];
		if(result.next()){
			res[0] = Integer.parseInt(result.getString("homeWorkWeight"));
			res[1] = Integer.parseInt(result.getString("quizWeight"));
			res[2] = Integer.parseInt(result.getString("testWeight"));
			res[3] = Integer.parseInt(result.getString("projectWeight"));
		}
		return res;
	}
	// update course by courseID
	public static void updateCourseByCourseID(int cid, int h, int q, int t, int p, Connection conn) throws SQLException{
		String sql = "select * from course where courseID = " + cid;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		
		if(result.next()){
			sql = "update course set homeWorkWeight = " + h + ", quizWeight = " + q + ", testWeight = " + t + ", projectWeight = " + p + "where courseID = " + cid;
			preStatement = conn.prepareStatement(sql);
			preStatement.executeQuery();
		}else{
			sql = "INSERT INTO COURSE (COURSEID, HOMEWORKWEIGHT, QUIZWEIGHT, TESTWEIGHT, PROJECTWEIGHT) VALUES (" + cid + ", " + h + ", " + q + ", " + t + ", " + p + ")";
			preStatement = conn.prepareStatement(sql);
			preStatement.executeQuery();			
		}
	}
	
	// count GPA by studentID and courseID
	public static double getGPAByStudentIDByCourseID(int cid, int id, int h, int q, int t, int p, Connection conn) throws SQLException{
		String sql = "select grade, typeID from gradebookplus where studentID = " + id + " and CourseID = " + cid;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		double gpa = 0.0;
		int count = 0;
		double t1 = 0.0;
		double c1 = 0;
		double t2 = 0.0;
		double c2 = 0;
		double t3 = 0.0;
		double c3 = 0;
		double t4 = 0.0;
		double c4 = 0;
		while(result.next()){
			String type = result.getString("typeID");
			double tmp = Double.parseDouble(result.getString("grade"));
			if(type.equals("1")){
				t1 += tmp;
				c1++;
			}else if(type.equals("2")){
				t2 += tmp;
				c2++;
			}else if(type.equals("3")){
				t3 += tmp;
				c3++;
			}else if(type.equals("4")){
				t4 += tmp;
				c4++;
			}
			gpa = c1 > 0 ? (t1 / c1) * h / 100 : 0 + c2 > 0 ? (t2 / c2) *q / 100 : 0 + c3 > 0 ? (t3 / c3) * t / 100 : c3 + c4 > 0 ? (t4 / c4) * p / 100 : 0;
		}
		return gpa;
	}	
	
	

	// All assignments by a student
	public static ResultSet getAllAssignmentByStudentID(int id, Connection conn) throws SQLException{
		String sql = "select assignmentName from gradebook where studentID = " + id;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		return result;
	}
	// All assignments of a particular type by anyone
	public static ResultSet getAssignmentByType(int tid, Connection conn) throws SQLException{
		String sql = "select assignmentName from gradebook where typeID = " + tid;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		return result;
	}
	// All assignments of a particular type by a particular student
	public static ResultSet getAssignmentByTypeAndStudentID(int tid, int id, Connection conn) throws SQLException{
		String sql = "select assignmentName from gradebook where studentID = " + id + " and typeID = " + tid;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		return result;
	}
	// The average for a student
	public static double getGPAByStudentID(int id, Connection conn) throws SQLException{
		String sql = "select grade from gradebook where studentID = " + id;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		double gpa = 0.0;
		int count = 0;
		while(result.next()){
			gpa += Double.parseDouble(result.getString("grade"));
			count++;
		}
		return gpa / count;
	}
	// The average for a student by assignment type
	public static double getGPAByStudentIDByType(int id, int tid, Connection conn) throws SQLException{
		String sql = "select grade from gradebook where studentID = " + id + " and typeID = " + tid;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		double gpa = 0.0;
		int count = 0;
		while(result.next()){
			gpa += Double.parseDouble(result.getString("grade"));
			count++;
		}
		return gpa / count;
	}
	
	// The highest and lowest grade for a particular assignment type (highest quiz grade, lowest project grade, etc)
	public static double[] getGPARangeByStudentID(int id, Connection conn) throws SQLException{
		String sql = "select grade from gradebook where studentID = " + id;
		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		double max = 0.0;
		double min = 100.0;
		while(result.next()){
			double grade = Double.parseDouble(result.getString("grade"));
			if(grade > max){
				max = grade;
			}
			if(grade < min){
				min = grade;
			}
		}
		return new double[]{min, max};
	}
}
