package Calculadora;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;


public class calculadoraservidor  {

	public static void main(String[] args){
			
		System.out.print("Bienvenido");		

		int [] puertosServidores = {9995,9996,9997,9998,9999};

		boolean conectado = false;
		ServerSocket miServidor = null;
		Date fecha= new Date();	 
		Timestamp ts= null;
		String huella="";
		try{
			
			//Inicializamos el servidor
			for(int i=0;i<5;i++) {
				if(available(puertosServidores[i])) {
					 miServidor = new ServerSocket(puertosServidores[i]);
					 System.out.println("Conexion");
					 conectado = true;
					 //Obtienes la TimeStamp
					 long time = fecha.getTime();
					 ts = new Timestamp(time);
					 //Usamos SHA1 para conseguir la huella del servidor
					 System.out.println("\nIntentando obtener huella");
					 huella= DigestUtils.sha1Hex(ts.toString());
					 System.out.println("\nHuella del servidor: "+huella);
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

	        while(true) {
	            try { 
					//Recibiendo mensaje y separandolo en variables
					String mensaje = dataInputStream.readUTF();
					System.out.println("Mensaje del cliente: "+mensaje);
					
					String [] operandos = mensaje.split(",");
					int op = Integer.parseInt(operandos[0]);
					if(op==100) {
						//100 es el codigo de contenido para un acuse
						dataOutputStream.writeUTF("101,0,0,0,0"); //Regresa 101, que es el contentCode para decir que esta en linea
					}else {
						String path = "Calculadora";
						String classname = "";
						float operando1 = Float.parseFloat(operandos[1]);
						float operando2 = Float.parseFloat(operandos[2]);
						Class cls = null;
						String respuesta = "";
						Object obj = null;
						ClassLoader clsloader = ClassLoader.getSystemClassLoader();
						System.out.print("\nTratando de usar el ClassLoader");
						char operacion=' ';
						
						//Determinas la operacion segun el content code
						switch(op) {
						case 1:
							operacion = '+';
							classname = "suma";
							break;
						case 2:
							operacion = '-';
							classname = "resta";
							break;
						case 3:
							operacion = '*';
							classname = "multiplicacion";
							break;
						case 4:
							operacion = '/';
							classname = "division";
							break;
						default:
							operacion = ' ';
							break;
						}
						//Usamos el class loader para cargar dinamicamente las clases
						try {
							cls=clsloader.loadClass(path+"."+classname);
						} catch (ClassNotFoundException e) {
							
							e.printStackTrace();
						}
						
						try {
							 obj = cls.newInstance();
						} catch (InstantiationException e) {
							
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							
							e.printStackTrace();
						}
						//Realizamos la operacion
						respuesta=((OperacionesArit)obj).Operar(operando1,operando2);
						System.out.print("\nOperando1: "+operando1);
						System.out.print("\nOperando2: "+operando2);
						System.out.print("\nOperacion: "+operacion);
						System.out.print("\nRespuesta de la clase: "+respuesta);
						dataOutputStream.writeUTF(respuesta); 
						
		                     
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

