package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import helpers.DialogCreator;
import helpers.ExceptionHandler;
import helpers.HashingHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import models.users.Admin;
import models.users.CurrentUser;
import models.users.Student;
import models.users.Teacher;
import models.users.User;
import models.users.UserType;

public class DBModel {
	private static final String DB_URL = "jdbc:sqlserver://DESKTOP-V673UK2\\SQLSERVER;user=sa;password=123;databaseName=EXAMINATION";
	private static DBModel db = null;
	private Connection conn;

	private DBModel() {
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			conn = DriverManager.getConnection(DB_URL);

		} catch (Exception e) {
			DialogCreator.showDialog(AlertType.ERROR, "Error", null, "Can't Connect to database");
			ExceptionHandler.handleException(e);
		}
	}

	public static DBModel getInstance() {
		if (db == null)
			db = new DBModel();
		return db;
	}

	public void initializeUser(ResultSet r) {
		try {
			if (r.getString("UserType").charAt(0) == 'T')
				if (r.getBoolean("IsAdmin"))
					CurrentUser.setUser(new Admin(r.getInt("UserID"), r.getString("Username"),
							r.getString("Gender").charAt(0), r.getString("Email"), r.getString("Password")));
				else
					CurrentUser.setUser(new Teacher(r.getInt("UserID"), r.getString("Username"),
							r.getString("Gender").charAt(0), r.getString("Email"), r.getString("Password")));
			else
				CurrentUser.setUser(new Student(r.getInt("UserID"), r.getString("Username"),
						r.getString("Gender").charAt(0), r.getString("Email"), r.getString("Password")));
			CurrentUser.getUser().initializeCoursesFromDB();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	public Teacher getTeacherByTID(int tid) {
		try {
			String st = "select * from Teachers where tid=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, tid);
			ResultSet r = p.executeQuery();
			if (r.next())
				return new Teacher(r.getInt("TID"), r.getString("TNAME"), r.getString("TGENDER").charAt(0),
						r.getString("TEMAIL"), r.getString("TPASS"));
			return null;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return null;
		}

	}

	public boolean login(String email, String pass) {
		try {
			String st = "select * from Users where EMAIL=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setString(1, email);
			ResultSet r = p.executeQuery();
			if (r.next() && HashingHelper.validatePassword(pass, r.getString("Password"))) {
				initializeUser(r);
				return true;
			}
			return false;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean addNewUser(User user) {
		try {
			String sql;
			if (user.getUserType() == UserType.TEACHER)
				sql = "INSERT INTO TEACHERS(TNAME,TGENDER,TEMAIL,TPASS,ISADMIN) VALUES (?,?,?,?,?)";
			else
				sql = "INSERT INTO STUDENTS(SNAME,SGENDER,SEMAIL,SPASS) VALUES (?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getGender() + "");
			stmt.setString(3, user.getEmail().toLowerCase());
			stmt.setString(4, HashingHelper.hashNewPassword(user.getPass()));
			if (user.getUserType() == UserType.TEACHER)
				stmt.setBoolean(5, ((Teacher) user).isAdmin());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleSQLUpdateException(e, "Email");
			return false;
		}
	}

	public boolean updateUser(User user, boolean updatePassword, String name, String email, String password) {
		try {
			String sql;
			if (user.getUserType() == UserType.TEACHER)
				sql = "UPDATE TEACHERS SET TNAME = (?), TEMAIL= (?),TPASS=(?) WHERE TID =(?)";
			else
				sql = "UPDATE STUDENTS SET SNAME = (?), SEMAIL= (?),SPASS=(?) WHERE SID =(?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, email.toLowerCase());
			if (updatePassword)
				stmt.setString(3, HashingHelper.hashNewPassword(password));
			else
				stmt.setString(3, user.getPass());
			stmt.setInt(4, user.getId());
			stmt.executeUpdate();
			if (updatePassword)
				user.setPass(password);
			user.setName(name);
			user.setEmail(email);
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleSQLUpdateException(e, "Email");
			return false;
		}

	}

	public ArrayList<Question> getExamQuestions(Exam exam) {

		try {
			ArrayList<Question> questions = new ArrayList<Question>();
			String st = "select QID,QUESTION,CHOICEA,CHOICEB,CHOICEC,CHOICED,CORRECT from QUESTIONS where EID=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, exam.getId());
			ResultSet r = p.executeQuery();
			while (r.next()) {
				questions.add(new Question(r.getInt("QID"), r.getString("QUESTION"), r.getString("CHOICEA"),
						r.getString("CHOICEB"), r.getString("CHOICEC"), r.getString("CHOICED"),
						r.getString("CORRECT").charAt(0), exam));
			}
			return questions;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return new ArrayList<Question>();
		}
	}

	public HashSet<Exam> getCourseExams(Course c) {
		try {
			HashSet<Exam> exams = new HashSet<Exam>();
			String st = "select EID,TITLE from EXAMS where CID=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, c.getId());
			ResultSet r = p.executeQuery();
			while (r.next()) {
				exams.add(new Exam(r.getInt("EID"), r.getString("TITLE"), c, null));
			}
			return exams;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return new HashSet<Exam>();
		}
	}

	// no need to fill teacher
	public HashSet<Course> getAvailableCourses(Student std) {
		try {
			String st = "select CID,CNAME,CREDITS,DESCRIPTION from Courses except select CID,CNAME,CREDITS,DESCRIPTION from StudentsCourses where SID=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, std.getId());
			ResultSet r = p.executeQuery();
			HashSet<Course> courses = new HashSet<Course>();
			while (r.next()) {
				Course course = new Course(r.getInt("CID"), r.getInt("CREDITS"), r.getString("CNAME"),
						r.getString("DESCRIPTION"), null);
				course.initializeExamsFromDB();
				courses.add(course);
			}
			return courses;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return new HashSet<Course>();
		}

	}

	public HashSet<Course> getUserCourses(User user) {
		try {
			HashSet<Course> courses = new HashSet<Course>();
			String st;
			if (user.getUserType() == UserType.STUDENT)
				st = "select CID,CNAME,CREDITS,DESCRIPTION ,TID from StudentsCourses where SID=(?)";
			else
				st = "select CID,CNAME,CREDITS,DESCRIPTION from COURSES where TID=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, user.getId());
			ResultSet r = p.executeQuery();
			while (r.next()) {
				Course course;
				if (user.getUserType() == UserType.TEACHER)
					course = new Course(r.getInt("CID"), r.getInt("CREDITS"), r.getString("CNAME"),
							r.getString("DESCRIPTION"), (Teacher) user);
				// TODO
				else
					course = new Course(r.getInt("CID"), r.getInt("CREDITS"), r.getString("CNAME"),
							r.getString("DESCRIPTION"), TeachersFlyWeightFactory.getTeacher(r.getInt("TID")));
				course.initializeExamsFromDB();
				courses.add(course);
			}
			return courses;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return new HashSet<Course>();
		}
	}

	public ObservableList<SResultsTableModel> getStudentGrades(User user) {
		try {
			ObservableList<SResultsTableModel> oblist = FXCollections.observableArrayList();
			String st = "select CNAME,CREDITS,TITLE,GRADE from StudentsGrades where SID=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, user.getId());
			ResultSet r = p.executeQuery();
			while (r.next())
				oblist.add(new SResultsTableModel(r.getString("CNAME"), r.getString("TITLE"), r.getFloat("GRADE"),
						r.getInt("CREDITS")));
			return oblist;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return FXCollections.observableArrayList();
		}
	}

	public ObservableList<TResultsTableModel> getTeacherGrades(User user) {
		try {
			ObservableList<TResultsTableModel> oblist = FXCollections.observableArrayList();
			String st = "select SNAME,SEMAIL,CNAME,TITLE,GRADE from TeacherStudentsGrades where TID=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, user.getId());
			ResultSet r = p.executeQuery();
			while (r.next())
				oblist.add(new TResultsTableModel(r.getString("SNAME"), r.getString("SEMAIL"), r.getString("CNAME"),
						r.getString("TITLE"), r.getFloat("GRADE")));
			return oblist;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return FXCollections.observableArrayList();
		}
	}

	public boolean addNewCourse(Course course) {
		try {
			String sql = "INSERT INTO COURSES(TID,CNAME,CREDITS,DESCRIPTION) VALUES (?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, course.getTeacher().getId());
			stmt.setString(2, course.getName().toUpperCase());
			stmt.setInt(3, course.getCredits());
			stmt.setString(4, course.getDesc());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				course.setId(rs.getInt(1));
			} else {
				DialogCreator.showDialog(AlertType.ERROR, "Error", "Unexpected Error Occurred",
						"Application should restart to apply changes");
				System.exit(0);
			}
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleSQLUpdateException(e, "Course Name");
			return false;
		}
	}

	public boolean updateCourse(Course course, String newName, String newDesc, int newCredits) {
		try {

			String sql = "UPDATE COURSES SET CNAME = (?), CREDITS= (?),DESCRIPTION=(?) WHERE CID =(?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newName);
			stmt.setInt(2, newCredits);
			stmt.setString(3, newDesc);
			stmt.setInt(4, course.getId());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleSQLUpdateException(e, "Course");
			return false;
		}
	}

	public boolean deleteCourse(Course course) {
		try {
			String query = "delete from COURSES where CID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, course.getId());
			stmt.execute();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean addNewExam(Exam exam) {
		try {
			String sql = "INSERT INTO EXAMS(CID,TITLE) VALUES (?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, exam.getCourse().getId());
			stmt.setString(2, exam.getTitle());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				exam.setId(rs.getInt(1));
			} else {
				DialogCreator.showDialog(AlertType.ERROR, "Error", "Unexpected Error Occurred",
						"Application should restart to apply changes");
				System.exit(0);
			}
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean updateExam(Exam exam, String newTitle) {
		try {

			String sql = "UPDATE EXAMS SET TITLE= (?) WHERE EID =(?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newTitle);
			stmt.setInt(2, exam.getId());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean deleteExam(Exam exam) {
		try {
			String query = "delete from EXAMS where EID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, exam.getId());
			stmt.execute();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean addNewQuestion(Question question) {
		try {
			String sql = "INSERT INTO QUESTIONS(EID,QUESTION,CHOICEA,CHOICEB,CHOICEC,CHOICED,CORRECT) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, question.getExam().getId());
			stmt.setString(2, question.getQuestion());
			stmt.setString(3, question.getChoiceA());
			stmt.setString(4, question.getChoiceB());
			stmt.setString(5, question.getChoiceC());
			stmt.setString(6, question.getChoiceD());
			stmt.setString(7, (question.getCorrect() + "").toUpperCase());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				question.setId(rs.getInt(1));
			} else {
				DialogCreator.showDialog(AlertType.ERROR, "Error", "Unexpected Error Occurred",
						"Application should restart to apply changes");
				System.exit(0);
			}
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean updateQuestion(Question question, String newQuestion, String newChoiceA, String newChoiceB,
			String newChoiceC, String newChoiceD, char newCorrect) {
		try {
			String sql = "UPDATE QUESTIONS SET QUESTION=(?),CHOICEA=(?),CHOICEB=(?),CHOICEC=(?),CHOICED=(?),CORRECT=(?) where QID=(?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newQuestion);
			stmt.setString(2, newChoiceA);
			stmt.setString(3, newChoiceB);
			stmt.setString(4, newChoiceC);
			stmt.setString(5, newChoiceD);
			stmt.setString(6, newCorrect + "");
			stmt.setInt(7, question.getId());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean deleteQuestion(Question question) {
		try {
			String query = "delete from QUESTIONS where QID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, question.getId());
			stmt.execute();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean enrollStudent(Student student, Course course) {
		try {
			String sql = "INSERT INTO ENROLLMENT(SID,CID) VALUES (?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getId());
			stmt.setInt(2, course.getId());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}
	}

	public boolean unEnrollStudent(Student student, Course course) {
		try {
			String query = "delete from ENROLLMENT where SID = (?) and CID=(?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, student.getId());
			stmt.setInt(2, course.getId());
			stmt.execute();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return false;
		}

	}

	public boolean checkIfExamDone(Exam exam, Student student) {
		try {
			String query = "select * from grades where eid=(?) and sid=(?)";
			PreparedStatement p = conn.prepareStatement(query);
			p.setInt(1, exam.getId());
			p.setInt(2, student.getId());
			ResultSet r = p.executeQuery();
			if (r.next())
				return true;
			return false;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return true;
		}
	}

	public boolean registerNewGrade(Student student, Exam exam, float grade) {
		try {
			String sql;
			sql = "INSERT INTO GRADES(SID,EID,GRADE) VALUES (?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getId());
			stmt.setInt(2, exam.getId());
			stmt.setFloat(3, grade);
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			ExceptionHandler.handleSQLUpdateException(e, "Grade");
			return false;
		}

	}

	public int getNumOfEnrolledStudentsInCourse(Course course) {
		try {
			String st = "select  dbo.std_enrolled_in_course(?) as result";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, course.getId());
			ResultSet r = p.executeQuery();
			if (r.next())
				return r.getInt("result");
			return 0;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return 0;
		}
	}

	public int getNumOfExamSubmissions(Exam exam) {
		try {
			String st = "select count(sid) as result from grades where eid=(?)";
			PreparedStatement p = conn.prepareStatement(st);
			p.setInt(1, exam.getId());
			ResultSet r = p.executeQuery();
			if (r.next())
				return r.getInt("result");
			return 0;
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
			return 0;
		}
	}
}
