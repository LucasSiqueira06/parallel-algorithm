import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class QuickSortSerial {
    public static void main(String[] args) {
        int size = 1000000;

        int[] array = generateRandomArray(size);

        long startTime = System.nanoTime();

        quickSort(array, 0, array.length - 1);

        long endTime = System.nanoTime();

        long timeTaken = (endTime - startTime) / 1000000;

        System.out.println("Time taken for sorting: " + timeTaken + " ms");

        exportToCSV(size, timeTaken);
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    private static void exportToCSV(int size, long timeTaken) {
        String csvFile = "quicksort_serial_results.csv";
        File file = new File(csvFile);
        boolean append = file.exists();
        
        try (FileWriter writer = new FileWriter(csvFile, append)) {
            if (!append) {
                writer.write("Array Size,Time taken (ms)\n");
            }
            writer.write(size + "," + timeTaken + "\n");
            System.out.println("Results exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}