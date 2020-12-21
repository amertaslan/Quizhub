package application;

public class Quiz {

	private String quizName;
	private String quizLevel;
	public Quiz(String quizName,String quizLevel) {
		this.quizName = quizName;
		this.quizLevel = quizLevel;
	}

	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getQuizLevel() {
		return quizLevel;
	}
	public void setQuizLevel(String quizLevel) {
		this.quizLevel = quizLevel;
	}


}
