package AV2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class MergeSortParallel {
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
                    soma += media[j];
                    System.out.println(media[j]);
                }
                threads[contThreads] = numberOfThreads;
                times[contTimes] = (int) soma/10;
                contTimes++;
                contThreads++;
                numberOfThreads += 1;
            }
            MergeSortParalelo mergeSort = new MergeSortParalelo(array, numberOfThreads);
    
            long startTime = System.currentTimeMillis();
            // Inicia a ordenação paralela
            mergeSort.sort();
    
            long totalTime = System.currentTimeMillis() - startTime;
            // System.out.println("Array ordenado com merge sort serial em: " + totalTime + " nanosegundos");
            if (i < 50) { 
                media[i] = (int) totalTime;
            }
            // times[i] = (int) totalTime;
            // threads[i] = numberOfThreads;
        }

        generateCSV( "mergeSortParallel", threads, times, "Threads", "Tempo");

        // Imprime o array ordenado
        // System.out.println("Array ordenado: " + Arrays.toString(mergeSort.getArray()));
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

    // Método para gerar um array de números aleatórios
    public static int[] generateRandomArray(int numberOfElements) {
        int[] array = new int[numberOfElements];
        Random random = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            array[i] = random.nextInt(1000); // Gera números aleatórios de 0 a 99
        }
        return array;
    }
}

class MergeSortParalelo {
    private int[] array;
    private int numberOfThreads;

    public MergeSortParalelo(int[] array, int numberOfThreads) {
        this.array = array;
        this.numberOfThreads = numberOfThreads;
    }

    public int[] getArray() {
        return array;
    }

    public void sort() {
        mergeSort(0, array.length - 1, numberOfThreads);
    }

    private void mergeSort(int start, int end, int numberOfThreads) {
        if (numberOfThreads <= 1) {
            Arrays.sort(array, start, end + 1);
            return;
        }

        int mid = start + (end - start) / 2;

        // Cria as threads para a primeira e segunda metade do array
        Thread leftSorter = mergeSortParallel(start, mid, numberOfThreads);
        Thread rightSorter = mergeSortParallel(mid + 1, end, numberOfThreads);

        // Inicia as threads
        leftSorter.start();
        rightSorter.start();

        // Aguarda a conclusão das threads
        try {
            leftSorter.join();
            rightSorter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Une as duas metades ordenadas
        merge(start, mid, end);
    }

    private Thread mergeSortParallel(int start, int end, int numberOfThreads) {
        return new Thread(() -> mergeSort(start, end, numberOfThreads / 2));
    }

    private void merge(int start, int mid, int end) {
        int[] tempArray = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;

        while (i <= mid && j <= end) {
            if (array[i] <= array[j]) {
                tempArray[k++] = array[i++];
            } else {
                tempArray[k++] = array[j++];
            }
        }

        while (i <= mid) {
            tempArray[k++] = array[i++];
        }

        while (j <= end) {
            tempArray[k++] = array[j++];
        }

        for (i = start; i <= end; i++) {
            array[i] = tempArray[i - start];
        }
    }
}