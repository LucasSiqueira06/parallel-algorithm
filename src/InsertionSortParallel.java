package AV2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import AV2.InsertionSortParallel.InsertionSortTask;

public class InsertionSortParallel {
    public static void main(String[] args) {
        int numberOfReps = 50;
        int numberOfThreads = 1; 
        int numberOfElements = 200000; 

        int[] times = new int[numberOfReps/10];
        int contTimes = 0;
        int[] threads = new int[numberOfReps/10];
        int contThreads = 0;
        int[] media = new int[numberOfReps];

        for (int i = 0; i <= numberOfReps; i++) {
            int[] array = generateRandomArray(numberOfElements);
            if ( i == 10 || i == 20 || i == 30 || i == 40 || i == 50) {
                int soma = 0;
                for (int j = i - 10; j < i; j++){
                    System.out.println(j);
                    System.out.println(media[j]);
                    System.out.println(soma);
                    soma += media[j];
                    System.out.println(soma);
                }

                threads[contThreads] = numberOfThreads;
                times[contTimes] = (int) soma/10;
                contTimes++;
                contThreads++;
                numberOfThreads += 1;
            }
            long startTime = System.currentTimeMillis();
            parallelInsertionSort(array, numberOfThreads);
            long totalTime = System.currentTimeMillis() - startTime;
            if (i < 50) { 
                media[i] = (int) totalTime;
            }
            System.out.println("Array ordenado com insertion sort paralelo em: " + (int) totalTime + " nanosegundos");
        }

        generateCSV( "insertionSortParallel", threads, times, "Threads", "Tempo");
    
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


    public static void parallelInsertionSort(int[] array, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Divide o array em partes iguais para cada thread
        int segmentSize = array.length / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = i * segmentSize;
            int end = (i == numThreads - 1) ? array.length : (i + 1) * segmentSize;
            executor.execute(new InsertionSortTask(array, start, end));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class InsertionSortTask implements Runnable {
        private int[] array;
        private int start;
        private int end;

        public InsertionSortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start + 1; i < end; i++) {
                int key = array[i];
                int j = i - 1;

                while (j >= start && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;
            }
        }
    }
}

