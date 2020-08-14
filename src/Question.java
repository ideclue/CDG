package net.mrpaul.cdg;

public class Question {
	private String question;
	private int trait; //0-5 representing hexaco trait
	
	public Question(String q, int t){
		question = q;
		trait = t;
	}
	
	public String getQuestion(){
		return question;
	}
	
	public int getTrait() {
		return trait;
	}
	
	public void updateQuestion(Question q) {
		question = q.getQuestion();
		trait = q.getTrait();
	}
}

