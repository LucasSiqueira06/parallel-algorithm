package AV2;

import java.util.Arrays;
import java.util.Random;

public class InsertionSortSerial {
    public static void main(String[] args) {
         int numberOfElements = 100000; // Defina o número de elementos desejado
        int[] array = new int[numberOfElements];
        Random random = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            array[i] = random.nextInt(1000); // Gera números aleatórios de 0 a 99
        }

        // Imprime o array antes da ordenação
        // System.out.println("Array antes da ordenação: " + Arrays.toString(array));

        long startTime = System.nanoTime();
        // Chama o método de ordenação
        insertionSort(array);
        long totalTime = System.nanoTime() - startTime;
        System.out.println("Array ordenado com insertion sort serial em: " + totalTime + " nanosegundos");
        // Imprime o array ordenado
        // System.out.println("Array ordenado: " + Arrays.toString(array));
    }

    public static void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            // Move os elementos do array[0..i-1] que são maiores que a chave
            // para uma posição à frente de sua posição atual
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }
}
