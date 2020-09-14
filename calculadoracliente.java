package Calculadora;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class calculadoracliente extends Application {
	
	String repuesta_servidor ="";
	boolean bandera = false;
	BorderPane raiz = new BorderPane();
	GridPane grid = new GridPane();
	Label titulo = new Label("Calculadora especial");
	TextField input = new TextField();
	Button one = new Button("1");
	Button two = new Button("2");
	Button three = new Button("3");
	Button four = new Button("4");
	Button five = new Button("5");
	Button six = new Button("6");
	Button seven = new Button("7");
	Button eight = new Button("8");
	Button nine = new Button("9");
	Button zero = new Button("0");
	Button suma = new Button("+");
	Button resta = new Button("-");
	Button ac = new Button("AC");
	Button multiplicacion = new Button("*");
	Button division = new Button("/");
	Button punto = new Button(".");
	Button miBoton = new Button("Calcular");
	Label resultado = new Label("");
	Label resultado_resta = new Label("Resultado resta: ");
	Label resultado_suma = new Label("Resultado suma: ");
	Label resultado_multi = new Label("Resultado multi: ");
	Label resultado_div = new Label("Resultado div: ");
	
	public static void main(String[] args) {		
		launch(); //Lanzamos la app
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Calculadora");
		
	
		resultado.setText("Resultado: ");
		
		miBoton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado el boton");
				String input_usuario = input.getText();
				
				repuesta_servidor=peticion(input_usuario);
				resultado.setText(resultado.getText()+"\n"+input.getText()+" = "+repuesta_servidor);
				input.setText("");

			}
			
		});
		one.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 1");
				input.appendText("1");
			}	
		});
		two.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 2");
				input.appendText("2");
			}	
		});
		three.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 3");
				input.appendText("3");
			}	
		});
		four.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 4");
				input.appendText("4");
			}	
		});
		five.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 5");
				input.appendText("5");
			}	
		});
		six.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 6");
				input.appendText("6");
			}	
		});
		seven.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 7");
				input.appendText("7");
			}	
		});
		eight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 8");
				input.appendText("8");
			}	
		});
		nine.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 9");
				input.appendText("9");
			}	
		});
		zero.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado 0");
				input.appendText("0");
			}	
		});
		suma.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado +");
				input.appendText("+");
			}	
		});
		resta.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado -");
				input.appendText("-");
			}	
		});
		multiplicacion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado *");
				input.appendText("*");
			}	
		});
		division.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado /");
				input.appendText("/");
			}	
		});
		punto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado .");
				input.appendText(".");
			}	
		});
		ac.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado AC");
				input.setText("");
			}	
		});
		ac.setMinWidth(50);
		input.maxWidth(100);
		input.maxHeight(20);
		grid.maxWidth(300);
		grid.setMaxWidth(300);
		grid.add(titulo, 1, 0,8,1);
		grid.add(input,1,1,8,1);
		grid.addRow(2, seven,eight,nine,suma);
		grid.addRow(3, four,five,six,resta);
		grid.addRow(4, one,two,three,multiplicacion);
		grid.addRow(5, zero,punto,division,ac);
		grid.add(miBoton,1,6,8,1);
		grid.add(resultado_suma,5,0,8,8);
		grid.add(resultado_resta,5,4,8,8);
		grid.add(resultado_multi,5,6,8,8);
		grid.add(resultado_div,5,8,8,8);
		grid.setPadding(new Insets(25,25,25,25));
		grid.setHgap(25);
		grid.setVgap(30);
		raiz.getChildren().addAll(grid);
		raiz.setBottom(resultado);
		
		Scene miEscena = new Scene(raiz, 450,650);
		

		primaryStage.setScene(miEscena);
		primaryStage.show();
		
	}
	
	
	
	public String peticion(String userInput) {
		int [] puertosNodos = {9800,9801,9802,9803,9804};
		boolean conectado = false;
		String[] operaciones = userInput.split("\\+|\\-|\\*|\\/");
		float operando1 = Float.parseFloat(operaciones[0]);
		float operando2 = Float.parseFloat(operaciones[1]);
		String received ="";
		String resultado_recibido ="";
		Socket socket = null;
		int codigo_Operacion = 0;
		
		
		//Checamos que tipo de operacion es 
		if(userInput.contains("+")) {
			codigo_Operacion = 1;
		}else if(userInput.contains("-")) {
			codigo_Operacion = 2;
		}else if(userInput.contains("*")) {
			codigo_Operacion = 3;
		}else if(userInput.contains("/")) {
			codigo_Operacion = 4;
		}
		
		System.out.println("Bienvenido cliente");
		try {
			String datos = String.format("%d,%.2f,%.2f",codigo_Operacion,operando1, operando2);
			
			//Creacion de Socket
			 for(int i=0;i<5;i++) {
				 //Se conecta si esta abierto
 				if(!calculadoracliente.available(puertosNodos[i])) {
 					socket = new Socket("192.168.1.146",puertosNodos[i]);
 					System.out.println("Conectado a puerto: "+puertosNodos[i]);
 					conectado = true;
 					break;
 				};
 			}
			if(conectado) {

				//Creacion del Flujo de datos
				
				DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream());
				DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());
				

		                flujoSalida.writeUTF(datos); 
		                System.out.println("Se envio "+datos+" al servidor exitosamente");
		                  
		                received = flujoEntrada.readUTF(); 
		                System.out.println("Se recibio "+received+" del servidor exitosamente");

		                String [] resultado_string = received.split(",");
		                
		                //Clasificamos la respuesta segun su codigo de contenido
		              
		                int contentCode= Integer.parseInt(resultado_string[0]);
		                resultado_recibido = resultado_string[1];
		                switch(contentCode) {
		                case 11:{
			                System.out.println("Respuesta del servidor: \nResultado suma= "+resultado_recibido); 
							resultado_suma.setText(resultado_suma.getText()+"\n"+input.getText()+" = "+resultado_recibido);
		                	break;
		                }
		                case 12:{
			                System.out.println("Respuesta del servidor: \nResultado resta= "+resultado_recibido); 
							resultado_resta.setText(resultado_resta.getText()+"\n"+input.getText()+" = "+resultado_recibido);

		                	break;
		                }
		                case 13:{
		                	 System.out.println("Respuesta del servidor: \nResultado multiplicacion= "+resultado_recibido); 
		                	 resultado_multi.setText(resultado_multi.getText()+"\n"+input.getText()+" = "+resultado_recibido);
		                	break;
		                }
		                case 14:{
		                	 System.out.println("Respuesta del servidor: \nResultado division= "+resultado_recibido); 
		                	 resultado_div.setText(resultado_div.getText()+"\n"+input.getText()+" = "+resultado_recibido);
		                	break;
		                }
		                default:{
		                	 System.out.println("No se reconocio el resultado"); 
		                }
		                }

		            flujoEntrada.close(); 
		            flujoSalida.close(); 
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultado_recibido;
	}
	public static boolean available(int port) { //Funcion que revisa si un puerto esta en uso
	    System.out.println("--------------Probando puerto " + port);
	    Socket s = null;
	    try {
	        s = new Socket("192.168.1.146", port);

	        //Si llega hasta aqui es que hay un puerto aqui
	        System.out.println("--------------Puerto " + port + " tiene un servidor en linea");
	        return false;
	    } catch (IOException e) {
	    	System.out.println("--------------Puerto " + port + " no tiene un servidor en linea");
	        return true;
	    } 
	}
}

