package FactoryPattern;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.*;


public interface User <P> {

	//INSERT METHOD TO BE OVERRODE.
	void insert(P person)  throws ClassNotFoundException, SQLException;

	//LOGIN METHOD.
	@SuppressWarnings("static-access")
	public static boolean login(String email , String password, String table)  throws ClassNotFoundException, SQLException {
		ResultSet resultSet = null;


		String sql = "SELECT * FROM " +  table +" Where email = ? and password =?";
		try
		{
			PreparedStatement preparedStmt = Main.connection.getConnection().prepareStatement(sql);
			preparedStmt.setString(1,email);
			preparedStmt.setString(2,password);
			resultSet = preparedStmt.executeQuery();
			if(!resultSet.next())
			{
				return false;
			}
			else 
			{
				//change active user informations.
				Main.activeName = resultSet.getString("name");
				Main.activeSurname = resultSet.getString("surname");
				Main.activeEmail = resultSet.getString("email");
				Main.activePassword = resultSet.getString("password");
				Main.activeUserID = resultSet.getInt("id");
				return true;
			}

		}
		catch (SQLException e) 
		{

			Logger.getLogger(User.class.getName()).log(Level.SEVERE,null,e);
		}

		return false;
	}

	//DELETE METHOD.
	public  void delete();




}
