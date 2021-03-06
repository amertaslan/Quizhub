package controller;

import application.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import FactoryPattern.*;

public class studentGradesController implements Initializable {

	public static String quizName = "";

	//screen offsets
	private double xOffset = 0;
	private double yOffset = 0;

	//db connection variables
	ResultSet resultSet = null;
	ResultSet resultSetTemp = null;


	@FXML
	ListView<String> quizzes;

	@FXML
	Text username;

	@SuppressWarnings("rawtypes")
	ObservableList quizList = FXCollections.observableArrayList(); 


	@FXML
	ImageView backLabel = new ImageView();

	@FXML
	Label exitLabel = new Label();

	@FXML
	ImageView logoutImage = new ImageView();


	public void closePage()
	{
		System.exit(0);
	}

	public void back(MouseEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/studentMainMenu.fxml"));
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



	@SuppressWarnings({ "unchecked", "static-access" })
	public void listQuiz() throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * FROM quizstudent where studentid = (?)";
		PreparedStatement preparedStmt = Main.connection.getConnection().prepareStatement(sql);
		preparedStmt.setInt(1, Main.activeUserID);
		resultSet = preparedStmt.executeQuery();

		String query = "SELECT * FROM quiz where id = (?)";
		quizList.removeAll(quizList);
		while (resultSet.next()) {
			preparedStmt = Main.connection.getConnection().prepareStatement(query);
			preparedStmt.setInt(1, resultSet.getInt("quizid"));
			System.out.println(resultSet.getInt("quizid"));
			resultSetTemp = preparedStmt.executeQuery();
			if (resultSetTemp.next()) {
				if (resultSet.getInt("score") != -1) {
					quizList.add(resultSetTemp.getString("title") + "               Grade: " + resultSet.getInt("score"));
				}
			}
		}

		quizzes.getItems().addAll(quizList);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		username.setText(Main.activeName);
		try {
			listQuiz();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
