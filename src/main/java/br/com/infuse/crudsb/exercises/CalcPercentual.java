package br.com.infuse.crudsb.exercises;

public class CalcPercentual {

	private static Double TOTAL_ELEITORES = (double) 1000;	
	private static Double VALIDOS = (double) 800;
	private static Double BRANCOS = (double) 150;
	private static Double NULOS = (double) 50;
	
	
	public static void main(String[] args) { 		

		System.out.println("Percentual de Votos Válidos: " + calcPercentual(VALIDOS, TOTAL_ELEITORES) +"%");
		System.out.println("Percentual de Votos Brancos: " + calcPercentual(BRANCOS, TOTAL_ELEITORES) +"%");
		System.out.println("Percentual de Votos Nulos: " + calcPercentual(NULOS, TOTAL_ELEITORES) +"%");
	}
	
	/**
	 * Calculo o Valor Percentual dos valores informados.
	 */
	static Double calcPercentual(Double val1, Double val2) {
		
		return (val1/val2)*100;
	}
}
