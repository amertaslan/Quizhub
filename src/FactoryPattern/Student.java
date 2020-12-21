package FactoryPattern;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import application.*;

public class Student extends Person implements User<Student>{

	public Student(String name, String surname, String gender, String birthday, String email,String password) {
		super(name,surname,gender,birthday,email,password);

	}

	@SuppressWarnings("static-access")
	@Override
	public void insert(Student newStudent) throws ClassNotFoundException, SQLException
	{	
		try
		{
			String query = "insert into Student (name,surname,password,email,gender,birthdate) values (?,?,?,?,?,?)";
			PreparedStatement preparedStmt = Main.connection.getConnection().prepareStatement(query);
			preparedStmt.setString(1,newStudent.getName());
			preparedStmt.setString(2,newStudent.getSurname());
			preparedStmt.setString(3,newStudent.getPassword());
			preparedStmt.setString(4,newStudent.getEmail());
			preparedStmt.setString(5,newStudent.getGender());
			preparedStmt.setString(6,newStudent.getBirthday());
			preparedStmt.execute();
			Alert pwdAlert = new Alert(AlertType.CONFIRMATION);
			pwdAlert.setTitle("Creating Account");
			pwdAlert.setHeaderText("Done");
			pwdAlert.setContentText("Your account created succesfully");
			pwdAlert.showAndWait();
		}
		catch(Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
			Alert pwdAlert = new Alert(AlertType.ERROR);
			pwdAlert.setTitle("Database Error");
			pwdAlert.setHeaderText("Sorry for that");
			pwdAlert.setContentText("There is a database connection error, please try again later.");
			pwdAlert.showAndWait();
		}
	}


	public static boolean login(String email , String password) throws ClassNotFoundException, SQLException
	{
		return User.login(email, password, "student");
	}


	@Override
	public void delete() {}



}
