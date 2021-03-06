package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.Main;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import FactoryPattern.*;

public class displayStudentsOfClassController implements Initializable {
	private double xOffset = 0;
	private double yOffset = 0;

	//db connection variables
	ResultSet resultSet = null;
	ResultSet resultSetTemp = null;

	@FXML
	private ListView<String> students;

	@SuppressWarnings("rawtypes")
	private ObservableList studentList = FXCollections.observableArrayList(); 

	@FXML
	private ImageView backLabel = new ImageView();

	@FXML
	private Text username;

	@FXML
	private Label exitLabel = new Label();

	@FXML
	private ImageView logoutImage = new ImageView();

	@FXML
	private ScrollPane scrollPane = new ScrollPane();

	@FXML
	private TextField studentName = new TextField();

	@FXML
	private Button addStudentButton = new Button();

	@FXML
	private Button addQuizButton = new Button();

	@FXML
	private Button deleteStudentButton = new Button();


	@SuppressWarnings({ "unchecked", "static-access" })
	public void displayStudents() throws SQLException, ClassNotFoundException {
		String sql = "SELECT studentid FROM classroomstudent where classid = (?)";
		PreparedStatement preparedStmt = Main.connection.getConnection().prepareStatement(sql);
		preparedStmt.setInt(1, AddQuizToClassroomController.ActiveClassID); //hop
		resultSet = preparedStmt.executeQuery();

		String query = "SELECT * FROM student where id = (?)";
		studentList.removeAll(studentList);
		while (resultSet.next()) {
			preparedStmt = Main.connection.getConnection().prepareStatement(query);
			preparedStmt.setInt(1, resultSet.getInt("studentid"));
			resultSetTemp = preparedStmt.executeQuery();
			if (resultSetTemp.next()) {
				if (!studentList.contains(resultSetTemp.getInt("id")+ " - " + resultSetTemp.getString("name") + " " + resultSetTemp.getString("surname"))) {
					studentList.add(resultSetTemp.getInt("id")+ " - " + resultSetTemp.getString("name") + " " + resultSetTemp.getString("surname"));
				}
			}
		}
		students.getItems().addAll(studentList);

	}


	public void addStudentPage(MouseEvent event) throws IOException, ClassNotFoundException, SQLException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/addStudentPage.fxml"));
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


	@SuppressWarnings({ "unchecked", "static-access" })
	@FXML
	public void deleteStudent(MouseEvent event) throws SQLException, ClassNotFoundException, IOException {
		String selectedStudentName = students.getSelectionModel().getSelectedItem();
		try {

			studentName.setText(selectedStudentName);
			String [] studentNameArray = selectedStudentName.split(" ");
			String sql = "delete FROM classroomstudent where studentid = (?) and classid = (?)";
			PreparedStatement preparedStmt = Main.connection.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, Integer.parseInt(studentNameArray[0])); //hop
			preparedStmt.setInt(2, AddQuizToClassroomController.ActiveClassID);
			preparedStmt.execute();
			studentList.removeAll(studentList);

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/displayStudentsOfClass.fxml"));
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
		} catch (Exception e) {
			Alert pwdAlert = new Alert(AlertType.ERROR);
			pwdAlert.setTitle("Error");
			pwdAlert.setHeaderText("Input Error");
			pwdAlert.setContentText("Please choose a student to delete!");
			pwdAlert.showAndWait();
		}
	}





	@FXML
	public void displaySelected(MouseEvent event) {
		String selectedStudentName = students.getSelectionModel().getSelectedItem();
		System.out.println(selectedStudentName);
		studentName.setText(selectedStudentName);

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		username.setText(Main.activeName);
		try {
			displayStudents();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void createClassroomPage(MouseEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/createClassroom.fxml"));
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

	public void addQuizPage(MouseEvent event) throws IOException, ClassNotFoundException, SQLException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/AddQuizToClassroom.fxml"));
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

	public void closePage()
	{
		System.exit(0);
	}

	public void back(MouseEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/displayClassrooms.fxml"));
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


	public void logout(MouseEvent event) throws IOException 
	{
		Main.activeEmail = null;
		Main.activeName = null;
		Main.activePassword = null;
		Main.activeSurname = null;
		Main.activeUserID = 0;
		Teacher.newQuizID = 0;

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
