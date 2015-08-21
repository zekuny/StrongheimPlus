create table gradebookplus(
  studentID int,
  assignmentName VARCHAR2(100),
  typeID int,
  "date" Date,
  grade BINARY_DOUBLE,
  courseID int
)




create table course(
  courseID int,
  courseName VARCHAR2(100),
  homeWorkWeight int,
  quizWeight int,
  testWeight int,
  projectWeight int
);