import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BubbleSortSerial {
    public static void main(String[] args) {
        int size = 200000;

        int[] array = generateRandomArray(size);

        long startTime = System.currentTimeMillis();

        bubbleSort(array);

        long endTime = System.currentTimeMillis();

        long timeTaken = endTime - startTime;

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

    private static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private static void exportToCSV(int size, long timeTaken) {
        String csvFile = "bubblesort_serial_results.csv";
        File file = new File(csvFile);
        boolean append = file.exists();
        
        try (FileWriter writer = new FileWriter(csvFile, append)) {
            if (!append) {
                writer.write("Tamanho,Time");
            }
            writer.write(size + "," + timeTaken + "\n");
            System.out.println("Results exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
