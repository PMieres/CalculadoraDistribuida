package operaciones;




public class suma extends OperacionesArit{
	public float op1;
	public float op2;
	public String resultado;
	public suma(float operando1, float operando2) {
		this.op1 =operando1;
		this.op2= operando2;
		this.resultado="";
	}
	public static void main(String[] args) {
		System.out.print("\nhola");
		suma a = new suma(2,2);
		a.resultado = a.Operar();
		System.out.print("\nres: "+a.resultado);
	}
	public String Operar() {
		
        String toreturn; 

        float resultado = 0;


		float operando1 = this.op1;
		float operando2 = this.op2;
		int codigo_resultado= 0;
		String codigo_resultado_string= "";
		char operacion='+';

		System.out.print("\nOperando1: "+operando1);
		System.out.print("\nOperando2: "+operando2);
		System.out.print("\nOperacion: "+operacion);

				//Realizando operacion deseada

				resultado = operando1+operando2;
				codigo_resultado = 11;

				//Devuelve resultado
				toreturn = String.valueOf(resultado);
				System.out.print("\nResultado operacion: "+toreturn);
				codigo_resultado_string = String.valueOf(codigo_resultado);
				return codigo_resultado_string+","+toreturn;
}
		
}


