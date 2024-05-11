package AV2;

import java.util.Arrays;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InsertionSortSerial {
    public static void main(String[] args) {
        int numberOfElements = 1000; 
        int numberOfReps = 50;

        int[] times = new int[numberOfReps/10];
        int contTimes = 0;
        int[] elements = new int[numberOfReps/10];
        int contElements = 0;
        int[] media = new int[numberOfReps];
        
        for (int i = 0; i <= numberOfReps; i++) {
            int[] array = generateRandomArray(numberOfElements);

            if ( i == 10 || i == 20 || i == 30 || i == 40 || i == 50) {
                int soma = 0;
                for (int j = i - 10; j < i; j++){
                    System.out.println(j);
                    soma += media[j];
                    System.out.println(media[j]);
                }
                elements[contElements] = numberOfElements;
                times[contTimes] = (int) soma/10;
                contTimes++;
                contElements++;
                if (i == 10) {
                    numberOfElements = 10000;
                }else if (i == 20) {
                    numberOfElements = 50000;
                }else if (i == 30) {
                    numberOfElements = 100000;
                }else if (i == 40) {
                    numberOfElements = 200000;
                }
                
            }

            long startTime = System.currentTimeMillis();
            insertionSort(array);
            long totalTime = System.currentTimeMillis() - startTime;
            if (i < 50) { 
                media[i] = (int) totalTime;
            }
            // System.out.println("Array ordenado com insertion sort serial em: " + totalTime + " nanosegundos");
        }

        generateCSV( "insertionSortSerial", elements, times, "Numeros", "Tempo");
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

    public static int[] generateRandomArray(int numberOfElements) {
        int[] array = new int[numberOfElements];
        Random random = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            array[i] = random.nextInt(1000); // Gera números aleatórios de 0 a 99
        }
        return array;
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
