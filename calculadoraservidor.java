package Calculadora;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class calculadoraservidor  {

	public static void main(String[] args){
			
		System.out.print("Bienvenido");		

		int [] puertosServidores = {9995,9996,9997,9998,9999};
		int [] puertosNodos = {9800,9801,9802,9803,9804};
		boolean conectado = false;
		ServerSocket miServidor = null;
		try{
			
			//Inicializamos el servidor
			for(int i=0;i<5;i++) {
				if(available(puertosServidores[i])) {
					 miServidor = new ServerSocket(puertosServidores[i]);
					 System.out.println("Conexion");
					 conectado = true;
					 break;
				}
				
			}
			//Si fue exitosa la inicializacion nos ponemos a la escucha
			if(conectado) {
				Socket miSocket = null;
				while(true) {
					miSocket = miServidor.accept(); //Acepta todas las conexiones
					System.out.println("Se ha aceptado un nuevo cliente");
				
					//Creacion del Flujo de datos
					DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());
					DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());

					System.out.println("Asignando un nuevo Thread al cliente");	
					Thread t = new ClientHandler2(miSocket, flujoEntrada, flujoSalida);
					t.start();		

				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
			
		}

	private static boolean available(int port) { //Funcion que revisa si un puerto esta en uso
	    System.out.println("--------------Probando puerto " + port);
	    Socket s = null;
	    try {
	        s = new Socket("192.168.1.146", port);

	        //Si llega hasta aqui es que hay un puerto aqui

	        System.out.println("--------------Puerto " + port + " no esta disponible");
	        return false;
	    } catch (IOException e) {
	    	System.out.println("--------------Puerto " + port + " esta disponible");
	        return true;
	    } 
	}
	
}

class ClientHandler2 extends Thread {

	DataInputStream dataInputStream; 
    DataOutputStream dataOutputStream; 
    Socket sock; 
    int [] puertosServidores = {9999,9998,9997,9996,9995};
	int [] puertosNodos = {9800,9801,9802,9803,9804};
    
	public ClientHandler2(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.sock = s; 
        this.dataInputStream = dis; 
        this.dataOutputStream = dos; 
    } 
	 
	 @Override
	    public void run()  
	    { 
	        String received; 
	        String toreturn; 
	        boolean division = false;
	        float resultado = 0;
	        float resultado_division = 0;
	        while(true) {
	            try { 
					//Recibiendo mensaje y separandolo en variables
					String mensaje = dataInputStream.readUTF();
					System.out.println("Mensaje del cliente: "+mensaje);
					String [] operandos = mensaje.split(",");
					float operando1 = Float.parseFloat(operandos[1]);
					float operando2 = Float.parseFloat(operandos[2]);
					int op = Integer.parseInt(operandos[0]);
					int codigo_resultado= 0;
					String codigo_resultado_string= "";
					char operacion=' ';
					
					//Determinas la operacion segun el content code
					switch(op) {
					case 1:
						operacion = '+';
						break;
					case 2:
						operacion = '-';
						break;
					case 3:
						operacion = '*';
						break;
					case 4:
						operacion = '/';
						break;
					default:
						operacion = ' ';
						break;
					}
					System.out.print("\nOperando1: "+operando1);
					System.out.print("\nOperando2: "+operando2);
					System.out.print("\nOperacion: "+operacion);

	    					//Realizando operacion deseada
	    					switch(operacion) {
	    					case '+':
	    						division = false;
	    						resultado = operando1+operando2;
	    						codigo_resultado = 11;
	    						break;
	    					case '-':
	    						division = false;
	    						resultado= operando1-operando2;
	    						codigo_resultado = 12;
	    						break;
	    					case '*':
	    						division = false;
	    						resultado = operando1*operando2;
	    						codigo_resultado = 13;
	    						break;
	    					case'/':
	    						division = true;
	    						resultado_division= operando1/operando2;
	    						codigo_resultado = 14;
	    						break;
	    					default:
	    						division = false;
	    						System.out.println("No existe la operacion");
	    						break;
	    					}
	    					
	    					if(!division) {
	    						mensaje = String.valueOf(resultado);
	    						System.out.print("\nResultado operacion: "+mensaje);
	    						//Devuelve resultado

	    						toreturn = String.valueOf(resultado);
	    						codigo_resultado_string = String.valueOf(codigo_resultado);
	    						dataOutputStream.writeUTF(codigo_resultado_string+","+toreturn); 
	    					}else {
	    						mensaje = String.valueOf(resultado_division);
	    						System.out.print("\nResultado operacion: "+mensaje);
	    						//Devuelve resultado

	    						toreturn = String.valueOf(resultado_division);
	    						codigo_resultado_string = String.valueOf(codigo_resultado);
	    						dataOutputStream.writeUTF(codigo_resultado_string+","+toreturn); 
	    					}
	                     
	                        

	            } catch (IOException e) { 
	                e.printStackTrace(); 
	            } 
    
//	        try
//	        { 
//	            // closing resources 
//	            this.dataInputStream.close(); 
//	            this.dataOutputStream.close(); 
//	              
//	        }catch(IOException e){ 
//	            e.printStackTrace(); 
//	        } 
	    }
	    }
}

