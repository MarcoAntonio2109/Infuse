package br.com.infuse.crudsb.exercise;

import java.util.Scanner;

public class Fatorial {

public static void main(String[] args) { 		
		
		Scanner sc = new Scanner(System.in);
		int fatorial = 1;
		
		System.out.print("Entre com o número: ");
		int entrada = sc.nextInt();
		System.out.println("");
						
		for (int i = 1; i <= entrada; i++) {			
			fatorial *= i;			
			System.out.println( i + "!= " + fatorial);	
		}		
	}		

}
