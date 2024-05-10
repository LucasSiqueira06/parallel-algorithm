import java.io.*;
import java.util.*;

class QuickSortMultiThread {

    private static int[] array;

    public static void main(String[] args) {
        int size = 10000;
        int numThreads = 4;

        array = generateRandomArray(size);

        long startTime = System.currentTimeMillis();
        quickSortMultiThread(array, numThreads);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Sorted array: " + Arrays.toString(array));
        System.out.println("Time taken: " + elapsedTime + " ms");

        updateCsvFile(size, numThreads, elapsedTime);
    }

    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000);
        }
        return arr;
    }

    private static void updateCsvFile(int size, int numThreads, long time) {
        String filename = "quicksort_parallel_results.csv";
        File file = new File(filename);
        boolean fileExists = file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (!fileExists) {
                writer.println("Array Size,Threads,Time (ms)");
            }
            writer.println(size + "," + numThreads + "," + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void quickSortMultiThread(int[] arr, int numThreads) {
        quickSort(arr, 0, arr.length - 1, numThreads);
    }

    private static void quickSort(int[] arr, int low, int high, int numThreads) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            if (numThreads > 1) {
                Thread leftThread = new Thread(() -> quickSort(arr, low, pivotIndex - 1, numThreads / 2));
                Thread rightThread = new Thread(() -> quickSort(arr, pivotIndex + 1, high, numThreads - numThreads / 2));
                leftThread.start();
                rightThread.start();
                try {
                    leftThread.join();
                    rightThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                quickSort(arr, low, pivotIndex - 1, numThreads);
                quickSort(arr, pivotIndex + 1, high, numThreads);
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
