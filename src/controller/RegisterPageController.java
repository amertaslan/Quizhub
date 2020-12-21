package controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import FactoryPattern.*;

public class RegisterPageController implements Initializable {
	private double xOffset = 0;
	private double yOffset = 0;

	String st_firstName;
	String st_lastName;
	String st_email;
	String st_date;
	String st_password;
	String st_passwordAgain;
	String st_gender;

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}

	@FXML
	private Label cikis;


	@FXML
	private void closePage(MouseEvent event)
	{
		System.exit(0);
	}

	@FXML
	private ComboBox<String> comboBox = new ComboBox<String>();

	ObservableList<String> genderList = FXCollections.observableArrayList("Male","Female","-");

	@FXML
	private Button signUp;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField email;

	@FXML
	private DatePicker birthdate;

	@FXML
	private PasswordField password;


	@FXML
	private PasswordField passwordAgain;


	public void signUp(MouseEvent event) throws IOException {

		try
		{
			st_firstName = firstName.getText();
			st_lastName = lastName.getText();
			st_email = email.getText();
			st_date = birthdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			st_gender = comboBox.getValue();
			st_password = password.getText();
			st_passwordAgain = passwordAgain.getText();
			if(st_firstName.equals("") || st_lastName.equals("")|| st_email.equals("")|| st_date.equals("")
					|| st_gender.equals("") || st_password.equals("") || st_passwordAgain.equals(""))
			{
				Alert pwdAlert = new Alert(AlertType.ERROR);
				pwdAlert.setTitle("Register Error");
				pwdAlert.setHeaderText("Input Error");
				pwdAlert.setContentText("Re-enter your informations correctly!");
				pwdAlert.showAndWait();
			}
			//Email validation
			else if(!validate(st_email))
			{
				Alert pwdAlert = new Alert(AlertType.INFORMATION);
				pwdAlert.setTitle("Invalid email");
				pwdAlert.setHeaderText("Email Error");
				pwdAlert.setContentText("Enter a valid email");
				pwdAlert.showAndWait();
			}

			//Password validation
			else if(!st_password.equals(st_passwordAgain))
			{
				Alert pwdAlert = new Alert(AlertType.INFORMATION);
				pwdAlert.setTitle("Passwords not match");
				pwdAlert.setHeaderText("Mismatch Error");
				pwdAlert.setContentText("Enter your password again");
				pwdAlert.showAndWait();
			}

			//None error, print the values
			else
			{
				UserFactory factory = new UserFactory();
				Student newStudent = (Student) factory.getPerson("student", st_firstName, st_lastName, st_gender, st_date, st_email,st_password);
				newStudent.insert(newStudent);
				System.out.println(newStudent.toString());
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/gui/LoginPage.fxml"));
				Parent signUp = loader.load();
				Scene signUprez = new Scene(signUp);
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

				signUp.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						xOffset = event.getSceneX();
						yOffset = event.getSceneY();
					}
				});
				//set mouse drag
				signUp.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						window.setX(event.getScreenX() - xOffset);
						window.setY(event.getScreenY() - yOffset);
					}
				});
				window.setScene(signUprez);
				window.show();
			}
		}

		//Input error exception
		catch(Exception e)
		{
			Alert pwdAlert = new Alert(AlertType.ERROR);
			pwdAlert.setTitle("Register Error");
			pwdAlert.setHeaderText("Input Error");
			pwdAlert.setContentText("Re-enter your informations correctly!");
			pwdAlert.showAndWait();
		}

	}

	public void teacherSignUpPage(MouseEvent event) throws IOException 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/RegisterTeacherPage.fxml"));
		Parent signUp = loader.load();
		Scene signUprez = new Scene(signUp);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		signUp.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		//set mouse drag
		signUp.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
		window.setScene(signUprez);
		window.show();
	}


	public void backLoginPage(MouseEvent event) throws IOException 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/LoginPage.fxml"));
		Parent signUp = loader.load();
		Scene signUprez = new Scene(signUp);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		signUp.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		//set mouse drag
		signUp.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
		window.setScene(signUprez);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		comboBox.setItems(genderList);

	}



}
