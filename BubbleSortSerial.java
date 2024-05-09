import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BubbleSortSerial {
    public static void main(String[] args) {
        // Get the size of the array from user input
        int size = 150000; // Set array size to 1,000,000

        // Generate an array of random integers
        int[] array = generateRandomArray(size);

        // Track the start time
        long startTime = System.nanoTime();

        // Perform bubble sort
        bubbleSort(array);

        // Track the end time
        long endTime = System.nanoTime();

        // Calculate the time taken for sorting in milliseconds
        long timeTaken = (endTime - startTime) / 1000000;

        // Print the time taken
        System.out.println("Time taken for sorting: " + timeTaken + " ms");

        // Export time taken to CSV file
        exportToCSV(size, timeTaken);
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100); // Generate random integers between 0 and 99
        }
        return array;
    }

    private static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap array[j] and array[j+1]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private static void exportToCSV(int size, long timeTaken) {
        String csvFile = "sort_results_serial.csv";
        File file = new File(csvFile);
        boolean append = file.exists(); // Check if file already exists
        
        try (FileWriter writer = new FileWriter(csvFile, append)) {
            if (!append) {
                writer.write("Array Size,Time taken (ms)\n"); // Write header if file doesn't exist
            }
            writer.write(size + "," + timeTaken + "\n");
            System.out.println("Results exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
