package Calculadora;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class nodo{
	public static void main(String[] args) {
		int [] puertosServidores = {9995,9996,9997,9998,9999};  //Lista de posibles puertos de servidores
		int [] puertosNodos = {9800,9801,9802,9803,9804}; //Lista de posibles puertos de servidores
		boolean conectado = false;
		ServerSocket miServidor = null;
		int puerto_utilizado=0;
		try{
			
			//Inicializamos el nodo a un puerto
			for(int i=0;i<5;i++) {
				if(available(puertosNodos[i])) {
					 miServidor = new ServerSocket(puertosNodos[i]);
					 System.out.println("\nConexion inicial a nodo");
					 puerto_utilizado = puertosNodos[i];
					 conectado = true;
					 break;
				}
				
			}

			if(conectado) { //Si se logro inicializar el nodo
				Socket miSocket = null;
				
				while(true) {
					miSocket = miServidor.accept(); //Acepta todas las conexiones
					System.out.println("Se ha aceptado un nuevo cliente");
				
					//Creacion del Flujo de datos
					DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());
					DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());
					
					System.out.println("Local Socket Address: "+miSocket.getLocalSocketAddress());
					System.out.println("Asignando un nuevo Thread al cliente\n");	
					//Asignamos un hilo a el nodo
					Thread t = new ClientHandler(miSocket, flujoEntrada, flujoSalida,puerto_utilizado);
					t.start();		

				}
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
			
		}

	public static boolean available(int port) {//Funcion que revisa si un puerto esta en uso
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



class ClientHandler extends Thread {

	final DataInputStream dataInputStream; 
    final DataOutputStream dataOutputStream; 
    final Socket sock; 
    int [] puertosServidores = {9995,9996,9997,9998,9999};
	int [] puertosNodos = {9800,9801,9802,9803,9804};
	int used_port;
    
	 public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,int up)  
	    { 
	        this.sock = s; 
	        this.dataInputStream = dis; 
	        this.dataOutputStream = dos; 
	        this.used_port = up;
	    } 
	 
	 @Override
	    public void run()  
	    { 
		 String received; 
	       Socket s1 = null;
	       Socket s2 = null;
	       Socket s3 = null;
	       Socket s4 = null;
	       Socket s5 = null;
	       String respuesta = "";
	       String[] origen;
	       boolean connected = false;
	            try { 
	                  
	                
	            	System.out.println("\nIntentando leer mensaje del cliente");
	                received = dataInputStream.readUTF(); 
	                System.out.println("\nMensaje del cliente: "+received+"\n");
	                //Nos intentamos conectar a los servidores
	                for(int i=0;i<5;i++) {
	    				if(!nodo.available(puertosServidores[i])) {
	    					s1 = new Socket("192.168.1.146",puertosServidores[i]);
	    					System.out.println("\nConectado a puerto: "+puertosServidores[i]+"\n");
	    					connected = true;
	    					break;
	    				};
	    			}
	                //Intenta conectar a otros nodos
	                for(int i=0;i<5;i++) {
	                	if(puertosNodos[i]==used_port) {
	                		continue;
	                	}
	                	//Comprobamos si viene de un nodo
	                	try {
	                		origen = received.split(",");
	                		if(origen[3].contains("98")) {
	                			break;
	                		}
	                	}catch(IndexOutOfBoundsException e) {
	                		System.out.println("Mensaje no viene de un nodo");
	                	}
	                	//Si no viene de un nodo lo reenviamos a los otros nodos
	    				if(!nodo.available(puertosNodos[i])) {
	    					System.out.println("\n\nProbando si hay otros nodos");
	    					switch(i) {
	    					case 2:{
	    						s2= new Socket("192.168.1.146",puertosNodos[i]);
		    					System.out.println("\nConectado a puerto: "+puertosNodos[i]+"\n");
		    					DataOutputStream flujoSalida_s2 = new DataOutputStream(s2.getOutputStream());
			    				DataInputStream flujoEntrada_s2 = new DataInputStream(s2.getInputStream());
			    				flujoSalida_s2.writeUTF(received+","+used_port); 
	    						break;
	    					}
	    					case 3:{
	    						s3= new Socket("192.168.1.146",puertosNodos[i]);
		    					System.out.println("\nConectado a puerto: "+puertosNodos[i]+"\n");
		    					DataOutputStream flujoSalida_s3 = new DataOutputStream(s3.getOutputStream());
			    				DataInputStream flujoEntrada_s3 = new DataInputStream(s3.getInputStream());
			    				flujoSalida_s3.writeUTF(received+","+used_port); 
	    						break;
	    					}
	    					case 4:{
	    						s4= new Socket("192.168.1.146",puertosNodos[i]);
		    					System.out.println("\nConectado a puerto: "+puertosNodos[i]+"\n");
		    					DataOutputStream flujoSalida_s4 = new DataOutputStream(s4.getOutputStream());
			    				DataInputStream flujoEntrada_s4 = new DataInputStream(s4.getInputStream());
			    				flujoSalida_s4.writeUTF(received+","+used_port); 
	    						break;
	    					}
	    					case 1:{
	    						s5= new Socket("192.168.1.146",puertosNodos[i]);
		    					System.out.println("\nConectado a puerto: "+puertosNodos[i]+"\n");
		    					DataOutputStream flujoSalida_s5 = new DataOutputStream(s5.getOutputStream());
			    				DataInputStream flujoEntrada_s5 = new DataInputStream(s5.getInputStream());
			    				flujoSalida_s5.writeUTF(received+","+used_port); 
	    						break;
	    					}
	    					default:{
		    					System.out.println("\nNO EXISTE ESTE NODO\n");
	    					}
	    					}
	    					 

	    				};
	    			}
	                //Si logramos conectarnos a un servidor le mandamos el mensaje y lo recibimos
	    			if(connected) {
	    				DataOutputStream flujoSalida = new DataOutputStream(s1.getOutputStream());
	    				DataInputStream flujoEntrada = new DataInputStream(s1.getInputStream());
	    				flujoSalida.writeUTF(received); 
	    	            System.out.println("\nMensaje del cliente: "+received); 
	    	            respuesta = flujoEntrada.readUTF();
	    			}else {
	    				System.out.println("\nNingun puerto estaba abierto");
	    				return;
	    			}

									
					//Mandamos la respuesta al cliente			
	    			System.out.println("\nSe recibio mensaje del servidor");
	    			dataOutputStream.writeUTF(respuesta);
	    			System.out.println("\nMensaje al cliente enviado \n");
					
                 
                 
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	            } 
	          
	        try
	        { 
	            // cerrando los recursos 
	            this.dataInputStream.close(); 
	            this.dataOutputStream.close(); 
	              
	        }catch(IOException e){ 
	            e.printStackTrace(); 
	        } 
	    } 
}
