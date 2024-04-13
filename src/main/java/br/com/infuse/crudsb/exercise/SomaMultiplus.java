package br.com.infuse.crudsb.exercise;

import java.util.Scanner;

public class SomaMultiplus {

	public static void main(String[] args) { 		
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entre com o número: ");
		int val = sc.nextInt();

			int vet[] = new int[val];	
			int count = 0;
			for (int i = 0; i < val; i++) {
				int x = val-i;
				if(x%5==0) {
					vet[count] = x; 
					count = count + 1;				
				}
				if(x%3==0) {
					vet[count] = x; 
					count = count + 1;				
				}
			}
			int total = 0;
			for (int i = 0; i < vet.length; i++) {
				total = total + vet[i];			
			}
			System.out.print("Total = "+total);
		}
	
}
