package br.com.infuse.crudsb.exercise;

public class BubbleSort {

	public static void main(String args[]){

		int arr[] = {5, 3, 2, 4, 7, 1, 0, 6};
		bubbleSort(arr);
		System.out.println("Array Ordenado");
		printArray(arr);
	}

	static void bubbleSort(int arr[]){
		int n = arr.length;
		for (int i = 0; i < n-1; i++)
			for (int j = 0; j < n-i-1; j++)
				if (arr[j] > arr[j+1]){
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
	}
		
	static void printArray(int arr[]){
		int n = arr.length;
		for (int i=0; i<n; ++i)
			System.out.print(arr[i] + " ");
		System.out.println();
	}
	
}
