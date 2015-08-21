<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<!-- Jumbotron -->
<div class="jumbotron">
    <div class="container">
        <h1>Welcome Professor Robert!</h1>
        <p>Choose what you want to view below.*</p>
        <p><a href="#" class="btn btn-success btn-lg">University of North Carolina at Chapel Hill »</a></p>
    </div>
</div>


<h3>All assignments by a student by a course</h3>
<form name = "myForm" class="form-horizontal" method="post" action="ResultServlet" onsubmit="return validateForm()">
    <div class="form-group">
        <label for="Assignment" class="col-sm-2 control-label">Student ID</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="studentID" name="studentID" value="">
        </div>
    </div>
    <div class="form-group">
        <label for="Assignment" class="col-sm-2 control-label">Course ID</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="courseID" name="courseID" value="">
            <input type="hidden" id="ttt" name="ttt" value="0">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-4 col-sm-offset-2">
            <input id="submit" name="submit" type="submit" value="View" class="btn btn-primary">
    	</div>
    </div>
</form>

${table}
<br>
<br>

<br>
<h3><a href = "Index.jsp">Return</a></h3>
<br/>
<br/>
<br/>
<br/>
<br/>
</body>
</html>