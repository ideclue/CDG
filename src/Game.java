package net.mrpaul.cdg;

import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application{
	private double[] traits; //HEXACO
	private String name;
	int width, height;
	List<Question> questions = new ArrayList<Question>();
	BackgroundImage myBI;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage stage) throws IOException {
		stage.setResizable(false);
		width = 600; height = 400;
		
		traits = new double[6];
		for (int i = 0; i < traits.length; i++) {
			traits[i] = (Math.random() * 100) + 1;
		}
		
		//create questions	
		questions.add(new Question("They tend to act humbly about their achievements and accomplishments.", 0));
		questions.add(new Question("They tend to rely on other people for comfort during rough situations.", 1));
		questions.add(new Question("They usually are the one to start a conversation.", 2));
		questions.add(new Question("In a situation where they have been wronged by someone, they would always try to isolate themselves from that person in the future.", 3));
		questions.add(new Question("Before beginning any type of project, they come up with a detailed plan and try to follow it.", 4));
		questions.add(new Question("They are very free-spirited.", 5));
		questions.add(new Question("They would usually not lie, even if in their own benefit.",0));
		questions.add(new Question("They are a very anxious person.",1));
		questions.add(new Question("They are nearly always full of energy.", 2));
		questions.add(new Question("They are an easy person to be around and work with.", 3));
		questions.add(new Question("While working on a project, they try their best to make sure every detail is perfect.", 4));
		questions.add(new Question("They would almost definitely take the opportunity to go on an adventure.", 5));
		
		nameScene(stage);

		stage.show();
		
	}
	
	
	public void nameScene(Stage stage) throws IOException {
		VBox root = new VBox(10);		
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, width, height);
				
		String musicFile = "music.mp3";     // For example

		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setVolume(0.4);
		mediaPlayer.play();
		
		TextField nameField = new TextField("Name");
		nameField.setMaxWidth(200);
		root.getChildren().add(nameField);
		
		Button submitBtn = new Button("Submit");
		submitBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				String clickFile = "click.mp3";
				Media click = new Media(new File(clickFile).toURI().toString());
				MediaPlayer clicker = new MediaPlayer(click);
				clicker.setVolume(0.9);
				clicker.play();
			}
		});
		submitBtn.setOnAction(e -> {
			name = nameField.getText();
			instructionScene(stage);
		});
		

		root.getChildren().add(submitBtn);
		
		scene.getStylesheets().add("net/mrpaul/cdg/skin.css");
		stage.setScene(scene);
	}
	
	public void instructionScene(Stage stage) {
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, width, height);
		
		Text instructions = new Text("Instructions: Answer these questions to find out more about " + name + "'s traits!");
		instructions.setId("text");
		root.getChildren().add(instructions);
		
		Button contBtn = new Button("Continue");
		contBtn.setOnAction(e -> {
			statScene(stage);
		});
		
		root.getChildren().add(contBtn);

		scene.getStylesheets().add("net/mrpaul/cdg/skin.css");
		stage.setScene(scene);
	}
	
	public void statScene(Stage stage) {
		VBox root = new VBox(10);
		
		Scene scene = new Scene(root, width, height);
		
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10.0);
		
		Label title = new Label(name+"'s Traits!");
		title.setId("title");
		title.setUnderline(true);

		Button contBtn = new Button("Start");
		
		contBtn.setOnAction(e -> {
			gameScene(questions.remove((int) (Math.random() * questions.size())), 0, stage);
		});
		
		
		
		VBox sliders = new VBox();				
		Slider[] sTraits = new Slider[6];
		for(int i = 0; i < traits.length;i++){
			sTraits[i] = new Slider(0,100,traits[i]);
			sTraits[i].setMouseTransparent(true);
			sTraits[i].setShowTickMarks(true);
			sTraits[i].setMajorTickUnit(50);
			sTraits[i].setMinorTickCount(2);
			sTraits[i].setShowTickLabels(true);
			sliders.getChildren().add(sTraits[i]);
		}
	
	
		//text labels
		Label h = new Label("Honesty-Humility:");
		Label e = new Label("Emotionality:");
		Label x = new Label("Extraversion:");
		Label a = new Label("Agreeableness:");
		Label c = new Label("Conscientiousness:");
		Label o = new Label("Openness to Experience:");
				
		VBox labels = new VBox();
		labels.setSpacing(20);
		labels.getChildren().addAll(h,e,x,a,c,o);
		for (Node n : labels.getChildren()) {
			n.setId("text");
		}
		
		HBox com = new HBox();
		com.setSpacing(20);
		com.setAlignment(Pos.CENTER);
		com.getChildren().addAll(labels,sliders);
	
		root.getChildren().addAll(title,com,contBtn);
		contBtn.requestFocus();		

		scene.getStylesheets().add("net/mrpaul/cdg/skin.css");
		stage.setScene(scene);
	}
	
	public void gameScene(Question currentQ, int qCount, Stage stage) {
		
		StackPane root = new StackPane();
		VBox top = new VBox(10);
		top.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, width, height);
		DoubleWrapper delta = new DoubleWrapper(0);
	
		//set up scene
		HBox questionBox = new HBox(25);
		questionBox.setAlignment(Pos.CENTER);
	
		Label topTitle = new Label("Question " + (qCount + 1));
		topTitle.setId("title");
		top.getChildren().addAll(topTitle,questionBox);
		
		//set up question
		Text question = new Text(currentQ.getQuestion());	//set to question text
		question.setWrappingWidth(250);
		question.setId("text");
		//set up answers
		VBox answers = new VBox();
		
		//create radio buttons
		RadioButton rb0 = new RadioButton("Strongly Disagree");
		rb0.setUserData(0.6);
		RadioButton rb1 = new RadioButton("Disagree");
		rb1.setUserData(0.8);
		RadioButton rb2 = new RadioButton("Agree");
		rb2.setUserData(1.2);
		RadioButton rb3 = new RadioButton("Strongly Agree");
		rb3.setUserData(1.4);
		
		//group radio buttons
		ToggleGroup ansGroup = new ToggleGroup();
		rb0.setToggleGroup(ansGroup);
		rb1.setToggleGroup(ansGroup);
		rb2.setToggleGroup(ansGroup);
		rb3.setToggleGroup(ansGroup);
		
		rb3.setToggleGroup(ansGroup);
		
		rb0.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				String clickFile = "click.mp3";
				Media click = new Media(new File(clickFile).toURI().toString());
				MediaPlayer clicker = new MediaPlayer(click);
				clicker.setVolume(1.0);
				clicker.play();
			}
		});
		
		rb1.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				String clickFile = "click.mp3";
				Media click = new Media(new File(clickFile).toURI().toString());
				MediaPlayer clicker = new MediaPlayer(click);
				clicker.setVolume(1.0);
				clicker.play();
			}
		});
		
		rb2.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				String clickFile = "click.mp3";
				Media click = new Media(new File(clickFile).toURI().toString());
				MediaPlayer clicker = new MediaPlayer(click);
				clicker.setVolume(1.0);
				clicker.play();
			}
		});
		
		rb3.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				String clickFile = "click.mp3";
				Media click = new Media(new File(clickFile).toURI().toString());
				MediaPlayer clicker = new MediaPlayer(click);
				clicker.setVolume(1.0);
				clicker.play();
			}
		});
		
		
		ansGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				delta.change( (double) ansGroup.getSelectedToggle().getUserData());		
				
		}});
		
		//add radio buttons to scene
		answers.getChildren().addAll(rb0, rb1, rb2, rb3);
		
		//add to content box
		questionBox.getChildren().addAll(question, answers);
		
		HBox bottomBox = new HBox();
		bottomBox.setAlignment(Pos.CENTER);
		top.getChildren().add(bottomBox);
		
		//create, add continue button
		Button contBtn = new Button("Continue");
		contBtn.setOnAction(e -> {
			//counts to 4 questions, then moves to next scene
			if (qCount < 7 && ansGroup.getSelectedToggle() != null) {
				
				//change values here
				traits[currentQ.getTrait()] *= delta.getVal();
				if (traits[currentQ.getTrait()] > 100) {
					traits[currentQ.getTrait()] = 100;
				}
				

				gameScene(questions.remove((int) (Math.random() * questions.size())), qCount + 1, stage);
				
			} else if(ansGroup.getSelectedToggle() != null) {
				endScene(stage);
			}
		});
		bottomBox.getChildren().add(contBtn);
		
		
		Label title = new Label(name + "'s stats");
		title.setUnderline(true);
	
		Label h = new Label("Honesty-Humility: " + (int) traits[0]);
		Label e = new Label("Emotionality: " + (int) traits[1]);
		Label x = new Label("Extraversion: " + (int) traits[2]);
		Label a = new Label("Agreeableness: " + (int) traits[3]);
		Label c = new Label("Conscientiousness: " + (int) traits[4]);
		Label o = new Label("Openness to Experience: " + (int) traits[5]);
		VBox statBox = new VBox(title,h,e,x,a,c,o);
		for (Node n : statBox.getChildren()) {
			n.setId("text");
		}
		statBox.setStyle("-fx-padding: 0 10 10 0");
		statBox.setAlignment(Pos.BOTTOM_RIGHT);
		statBox.setLayoutY(height - 150);
			
		root.setMinHeight(height);

		root.getChildren().add(statBox);
		root.getChildren().add(top);
		
		contBtn.requestFocus();
		
		scene.getStylesheets().add("net/mrpaul/cdg/skin.css");
		stage.setScene(scene);
	}
	
	public void endScene(Stage stage) {
		VBox root = new VBox(10);
		Scene scene = new Scene(root, width, height);
		
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10.0);
		
		Label title = new Label(name+"'s Final Traits!");
		title.setId("title");
		title.setUnderline(true);
		
		VBox sliders = new VBox();				
		Slider[] sTraits = new Slider[6];
		for(int i = 0; i < traits.length;i++){
			sTraits[i] = new Slider(0,100,traits[i]);
			sTraits[i].setMouseTransparent(true);
			sTraits[i].setShowTickMarks(true);
			sTraits[i].setMajorTickUnit(50);
			sTraits[i].setMinorTickCount(2);
			sTraits[i].setShowTickLabels(true);
			sliders.getChildren().add(sTraits[i]);
		}
	
	
		//text labels
		Label h = new Label("Honesty-Humility:");
		Label e = new Label("Emotionality:");
		Label x = new Label("Extraversion:");
		Label a = new Label("Agreeableness:");
		Label c = new Label("Conscientiousness:");
		Label o = new Label("Openness to Experience:");
				
		VBox labels = new VBox();
		labels.setSpacing(20);
		labels.getChildren().addAll(h,e,x,a,c,o);
		for (Node n: labels.getChildren()) {
			n.setId("text");
		};
		
		HBox com = new HBox();
		com.setSpacing(20);
		com.setAlignment(Pos.CENTER);
		com.getChildren().addAll(labels,sliders);
	
		root.getChildren().addAll(title,com);
		title.requestFocus();

		scene.getStylesheets().add("net/mrpaul/cdg/skin.css");
		stage.setScene(scene);
	}
	
	
	
	//double wrapper class for use in button
	private class DoubleWrapper {
		double val;
		
		public DoubleWrapper(double n) {
			val = n;
		}
		
		public void change(double n) {
			val = n;
		}
		
		public double getVal() {
			return val;
		}
	}

}
