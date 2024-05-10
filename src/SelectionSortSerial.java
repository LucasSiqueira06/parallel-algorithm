import java.io.*;
import java.util.*;

class SelectionSortSerial {

    private static int[] array;

    public static void main(String[] args) {
        int size = 200000;

        array = generateRandomArray(size);

        long startTime = System.currentTimeMillis();
        selectionSortSerial(array);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Sorted array: " + Arrays.toString(array));
        System.out.println("Time taken: " + elapsedTime + " ms");

        updateCsvFile(size, elapsedTime);
    }

    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000);
        }
        return arr;
    }

    private static void updateCsvFile(int size, long time) {
        String filename = "selectionsort_serial_results.csv";
        File file = new File(filename);
        boolean fileExists = file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (!fileExists) {
                writer.println("Array Size,Time (ms)");
            }
            writer.println(size + "," + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void selectionSortSerial(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}
