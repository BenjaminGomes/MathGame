package com.bgomes.mathgame;

/*
 * 	The Score class defines an object that can store the data from the Score table
 */
public class Score {
	private int scoreId;
	private int finalScore;
	private int correctCount;
	private int wrongCount;
	private String userName;
	
	public Score() {
		userName = "";
		finalScore = 0;
		correctCount = 0;
		wrongCount = 0;
	}
	
	public Score(String userName, int finalScore, int correctCount, int wrongCount) {
		this.userName = userName;
		this.finalScore = finalScore;
		this.correctCount = correctCount;
		this.wrongCount = wrongCount;
	}
	
	public Score(int scoreId, String userName, int finalScore, int correctCount, int wrongCount) {
		this.scoreId = scoreId;
		this.userName = userName;
		this.finalScore = finalScore;
		this.correctCount = correctCount;
		this.wrongCount = wrongCount;
	}
	
	public int getId() {
		return scoreId;
	}
	
	public void setId(int scoreId) {
		this.scoreId = scoreId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getFinalScore() {
		return finalScore;
	}
	
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	
	public int getCorrectCount() {
		return correctCount;
	}
	
	public void setCorrectCount(int correctCount) {
		this.correctCount = correctCount;
	}
	
	public int getWrongCount() {
		return wrongCount;
	}
	
	public void setWrongCount(int wrongCount) {
		this.wrongCount = wrongCount;
	}
}
