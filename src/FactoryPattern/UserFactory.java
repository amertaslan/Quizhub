package FactoryPattern;

public class UserFactory {
	
	public Person getPerson(String userType, String name, String surname, String gender, String birthday, String email, String password) {
		
		if (userType.equalsIgnoreCase("teacher")) {
			return new Teacher(name,surname,gender,birthday,email, password);
		}
		else if (userType.equalsIgnoreCase("student")) {
			return new Student(name,surname,gender,birthday,email, password);
		}
		return null;
		
	}
}
