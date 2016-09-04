package com.rombus.evilbones.fwg;


import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author rombus
 *
 * 04/06/2016 03:03:02
 */
public class Main extends Application implements Initializable{
	@FXML private TextField userInputText;
	@FXML private Button generateBtn;
	@FXML private Button clearBtn;
	@FXML private TextArea resultText;
	@FXML private Slider wordLenSlider;
	@FXML private ImageView dinoImg;
		
	private CharactersUsed characters;
	private Modifier modifier;
	private int genWordLen = 4; // Es configurable por slider
	private static final int TEXTFIELD_CHAR_LIMIT = 100;
	private static final int WIDTH = 350, HEIGHT = 380;

	public static void main(String[] args) {
		launch(args);	//Start javafx app
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox page = (VBox) FXMLLoader.load(Main.class.getResource("/assets/gui.fxml"));
		Scene scene = new Scene(page);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fake Words Generator");
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/assets/ricon128.png")));
		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		primaryStage.setMaxWidth(WIDTH);
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.show();
	}
	
	
	private void generateName(char[] chars){
		int i = 0;
		for(; i<chars.length && chars[i] != '\0'; i++);
		int len = i;
		
		String buff = new String("");
		Random ran = new Random();
		Boolean isVowOld = null;
		
		
		
		for(i=0; i<genWordLen; i++){
			int randomIndex = ran.nextInt(len);
			char curChar = chars[randomIndex];
			
			if(isVowOld != null){
				boolean isVowNew = Utils.isVowel(curChar);
				// Si pusiste una consonante y ahora querés volver a poner otra consonante te mando una vocal en el medio.
				if(isVowOld == false && !isVowNew){
					char newChar = Utils.getRandomVowel();
					
					buff = buff.concat(""+newChar);
				}
				// Si pusiste una vocal y ahora querés poner otra vocal, me aseguro que no esté repetida. 
				else if (isVowOld == true && isVowNew){
					char previousChar = buff.charAt(buff.length()-1);
					while((curChar=Utils.getRandomVowel()) == previousChar);	// Cambio el current char con uno distinto al anterior
				}
			}
			
			String s = modifier.transformChar(curChar);
			isVowOld = Utils.isVowel(s);
			
			buff = buff.concat(s);
		}
		
		// No puede terminar con doble L, ni doble R
		int lastChar = buff.length()-1;
		if( (buff.charAt(lastChar-1) == 'l' && buff.charAt(lastChar) == 'l')	||
			(buff.charAt(lastChar-1) == 'r' && buff.charAt(lastChar) == 'r')){
			buff = buff.substring(0, lastChar);
		}
		
		// No puede empezar con doble R
		if(buff.charAt(1) == 'r' && buff.charAt(0) == 'r'){
			buff = buff.substring(1,buff.length());
		}
		
		
		resultText.appendText(buff+"\n");
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Acá va todo el código que queremos que se ejecute despuś de que el FXML haya hecho su mágia.
		modifier = new Modifier();
				
		// Limito el largo del input
		userInputText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				if(newValue.length() > TEXTFIELD_CHAR_LIMIT){
					userInputText.setText(newValue.substring(0, TEXTFIELD_CHAR_LIMIT));
				}
			}
		});
		
		
		// Botón de acción		
		generateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// Compact all words into one, and get characters and occurrence percentage.
				String text = userInputText.getText().replaceAll("\\s+","").toLowerCase();
				characters = new CharactersUsed(text, genWordLen);
				
				char [] pc = characters.getPlainChars();
				int CANT_NAMES = 10;
				for(int i=0; i<CANT_NAMES; i++){
					generateName(pc);
				}
			}
		});
		
		
		// Botón de limpiar resultados		
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				resultText.clear();
			}
		});
		
		// Word length slider
		wordLenSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
				int value = newVal.intValue();
				
				wordLenSlider.setValue(value);
				genWordLen = value;
			}
		});
		
		wordLenSlider.setFocusTraversable(false);
		resultText.setFocusTraversable(false);
	}
}
