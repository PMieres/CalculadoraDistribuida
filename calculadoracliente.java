package Calculadora;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
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
	int [] puertosNodos = {9800,9801,9802,9803,9804};
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
	Button btnConectar = new Button("Conectar");
	Button btnAcuse = new Button("Acuses");
	Label resultado = new Label("");
	Label resultado_resta = new Label("Resultado resta: ");
	Label resultado_suma = new Label("Resultado suma: ");
	Label resultado_multi = new Label("Resultado multi: ");
	Label resultado_div = new Label("Resultado div: ");
	Label huellaLabel = new Label("Huella: ");
	Date fecha= new Date();	 
	Timestamp ts= null;
	String huella="";
	Socket socket = null;
	DataOutputStream flujoSalida=null;
	DataInputStream flujoEntrada=null;
	boolean conectado = false;
	int acusesSuma = 0;
	int acusesResta= 0;
	int acusesMulti = 0;
	int acusesDivision = 0;
	Operacion [] listaSumas = new Operacion[20];
	Operacion [] listaRestas = new Operacion[20];
	Operacion [] listaMultis = new Operacion[20];
	Operacion [] listaDivs = new Operacion[20];
	int numSuma=0;
	int numResta=0;
	int numMulti=0;
	int numDiv=0;
	boolean acusesSuficientes =false;
	
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
				
				System.out.println("\nRealizando acuses de recibido");
				acusar();
				
				if(!acusesSuficientes) {
					System.out.println("---------ERROR---------");
					System.out.println("NO HAY SUFICIENTES SERVIDORES");
					System.out.println("-----------------------");
				}else {
					
				}
				
				if(acusesSuficientes) {
					conectar();
					repuesta_servidor=peticion(input_usuario);
					resultado.setText(resultado.getText()+"\n"+input.getText()+" = "+repuesta_servidor);
					input.setText("");	
					
				}else {
					 DuplicarServidores();
				}
				
					

			}
			
		});
		btnAcuse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado el boton de Acuse");
				for(int i=0;i<3;i++) {
					System.out.println("Intento "+(i+1)+"de obtener los acuses");
					System.out.println("acuses suma "+acusesSuma);
					
					if(acusesSuma >= 3) {
						break;
						
					}else {
						conectar();
						acusesSuma =0;
						acusesRecibidos();
					}
					
				}
				if(acusesSuma <3) {
					System.out.println("---------ERROR---------");
					System.out.println("NO HAY SUFICIENTES SERVIDORES");
					System.out.println("-----------------------");
				}
				
				

			}
			
		});
		btnConectar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("\nHas pulsado Conectar");
				
				conectar();
				
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
		grid.add(huellaLabel, 5, 0,15,1);
		grid.add(input,1,1,8,1);
		grid.addRow(2, seven,eight,nine,suma);
		grid.addRow(3, four,five,six,resta);
		grid.addRow(4, one,two,three,multiplicacion);
		grid.addRow(5, zero,punto,division,ac);
		grid.add(miBoton,1,6,8,1);
//		grid.add(btnConectar,3,6,8,1);
//		grid.add(btnAcuse,1,7,8,1);
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
		 //Obtienes la TimeStamp
		 long time = fecha.getTime();
		 ts = new Timestamp(time);
		 //Usamos SHA1 para conseguir la huella del cliente
		 huella= DigestUtils.sha1Hex(ts.toString());
		 huellaLabel.setText(huellaLabel.getText()+huella);
		 System.out.println("\nHuella del nodo: "+huella);
	}
	
	
	public void acusesRecibidos() {
		String received ="";
		int contador =0;
		int contentCode = 0;
		try {
			
		
				while(contador<3) {
					flujoSalida = new DataOutputStream(socket.getOutputStream());
					flujoEntrada = new DataInputStream(socket.getInputStream());
					
					//Enviamos la peticion para el acuse de recibido
					flujoSalida.writeUTF("100,0,0,0");
					received = flujoEntrada.readUTF(); 
					System.out.println("Mensaje servidor: "+received);
					contentCode = Integer.parseInt(received.split(",")[0]);
					System.out.println("Content Code: "+contentCode);
					if(contentCode==101) {
						acusesSuma++;
						System.out.println("Acuse recibido");

					}
					contador++;
				}
				contador=0;	
				
			
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Acuses = " + acusesSuma);
		
	}
	public String peticion(String userInput) {
		//int [] puertosNodos = {9800,9801,9802,9803,9804};
		//boolean conectado = false;
		String[] operaciones = userInput.split("\\+|\\-|\\*|\\/");
		float operando1 = Float.parseFloat(operaciones[0]);
		float operando2 = Float.parseFloat(operaciones[1]);
		String received ="";
		String resultado_recibido ="";
		//Socket socket = null;
		int codigo_Operacion = 0;

		String evento="";
		
		//Checamos que tipo de operacion es 
		if(userInput.contains("+")) {
			codigo_Operacion = 1;
			Operacion op= new Operacion(numSuma,operando1,operando2,huella);
			listaSumas[numSuma] = op;

			evento = op.evento;
			numSuma++;

		}else if(userInput.contains("-")) {
			codigo_Operacion = 2;
			
			Operacion op= new Operacion(numResta,operando1,operando2,huella);
			listaRestas[numResta] = op;
			evento = op.evento;
			numResta++;
		}else if(userInput.contains("*")) {
			codigo_Operacion = 3;
			Operacion op= new Operacion(numMulti,operando1,operando2,huella);
			listaMultis[numMulti] = op;
			evento = op.evento;
			numMulti++;

		}else if(userInput.contains("/")) {
			codigo_Operacion = 4;
			Operacion op= new Operacion(numDiv,operando1,operando2,huella);
			listaDivs[numDiv] = op;
			evento = op.evento;
			numDiv++;

		}
		
		if(acusesSuma <3) {
			System.out.println("No hay suficientes servidores para la operacion");
			return "";
		}
		acusesSuma=0;
		System.out.println("Bienvenido cliente");
		try {
			String datos = String.format("%d,%.2f,%.2f,%s",codigo_Operacion,operando1, operando2,evento);
			System.out.println("Se quiere enviar "+datos+" al servidor");
			if(conectado) {

				//Creacion del Flujo de datos
				while(true) {
					flujoSalida = new DataOutputStream(socket.getOutputStream());
					flujoEntrada = new DataInputStream(socket.getInputStream());
					
							
							//Enviamos la operacion
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

				}

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
	public void DuplicarServidores() {
		String programa = "";
		int contador = 0;
		String path = "C:\\Users\\Mieres\\eclipse-workspace\\TestExercises\\src\\";
		String folder = "Calculadora";
		File temp = new File(path+folder);
		while(temp.exists()) {
			temp = new File(path+folder+contador);
			contador++;
		}
		
		try {
			FileUtils.copyDirectory(new File(path+folder),temp,false);
			programa = path+folder+"\\calculadoraservidor.java";
			System.out.println("Programa a ejecutar: "+programa);
			Runtime.getRuntime().exec("java -cp .;commons-codec-1.15.jar Calculadora.calculadoraservidor");
			
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error duplicando el servidor");
		}
		
		
		
	}
	
	public void conectar() {
		//Creacion de Socket
		 for(int i=0;i<5;i++) {
			 //Se conecta si esta abierto
			if(!calculadoracliente.available(puertosNodos[i])) {
				try {
					socket = new Socket("192.168.1.146",puertosNodos[i]);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				System.out.println("Conectado a puerto: "+puertosNodos[i]);
				conectado = true;
				break;
			};
		}
		
	}
	public void acusar() {
		
		for(int i=0;i<3;i++) {
			System.out.println("Intento "+(i+1)+"de obtener los acuses");
			System.out.println("acuses suma "+acusesSuma);
			
			if(acusesSuma >= 3) {
				acusesSuficientes = true;
				break;
			}else {
				conectar();
				acusesSuma =0;
				acusesRecibidos();
			}
			
		}
		

		
	}
}

class Operacion{
	int indice;
	float operando1;
	float operando2;
	String evento;
	
	public Operacion(int ind, float op1,float op2,String huella) {
		indice = ind;
		operando1 = op1;
		operando2 = op2;
		String temporal = ""+indice+","+huella+","+operando1+","+operando2;
		evento = DigestUtils.sha1Hex(temporal);
	}
	
}

