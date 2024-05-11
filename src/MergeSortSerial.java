package AV2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
// import AV2.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MergeSortSerial {

    public static void main(String[] args) {
        int numberOfReps = 50;
        Random random = new Random();
        int[] times = new int[numberOfReps];
        int[] elements = new int[numberOfReps];
        for (int x = 0; x < numberOfReps; x++) {
            int numberOfElements = random.nextInt(100000); // Defina o número de elementos desejado
            int[] array = new int[numberOfElements];
            for (int i = 0; i < numberOfElements; i++) {
                array[i] = random.nextInt(1000); // Gera números aleatórios de 0 a 99
            }
            long startTime = System.nanoTime();
            mergeSort(array);
            long totalTime = System.nanoTime() - startTime;
            times[x] = (int) totalTime;
            elements[x] = (int) numberOfElements;
            System.out.println("Array ordenado com merge sort serial em: " + totalTime + " nanosegundos");
        }

        generateCSV( "mergeSortSerial", times, elements, "Numeros", "Tempo");
    }

    public static void generateCSV( String fileName, int[] axisX, int[] axisY, String nameX, String nameY) {
        String filename = fileName + ".csv";
        
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            // Escreve os cabeçalhos
            writer.println(nameX + "," + nameY);

            for (int i = 0; i < axisX.length; i++) {
                String row = axisX[i] + "," + axisY[i];
                writer.println(row);
            }
            
            System.out.println("Arquivo CSV criado com sucesso: " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }
    
   public static void mergeSort(int[] array) {
       if (array == null || array.length <= 1) {
           return;
       }
       int[] aux = new int[array.length];
       mergeSort(array, aux, 0, array.length - 1);
   }

   private static void mergeSort(int[] array, int[] aux, int low, int high) {
       if (low >= high) {
           return;
       }
       int mid = low + (high - low) / 2;
       mergeSort(array, aux, low, mid);
       mergeSort(array, aux, mid + 1, high);
       merge(array, aux, low, mid, high);
   }

   private static void merge(int[] array, int[] aux, int low, int mid, int high) {
       for (int i = low; i <= high; i++) {
           aux[i] = array[i];
       }
       int left = low;
       int right = mid + 1;
       for (int i = low; i <= high; i++) {
           if (left > mid) {
               array[i] = aux[right++];
           } else if (right > high) {
               array[i] = aux[left++];
           } else if (aux[left] <= aux[right]) {
               array[i] = aux[left++];
           } else {
               array[i] = aux[right++];
           }
       }
   }

   private static void printArray(int[] array) {
       for (int num : array) {
           System.out.print(num + " ");
       }
       System.out.println();
   }
}
