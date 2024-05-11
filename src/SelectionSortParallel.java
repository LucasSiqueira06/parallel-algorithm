import java.io.*;
import java.util.*;

class SelectionSortMultiThread {

    private static int[] array;

    public static void main(String[] args) {
        int size = 200000;
        int numThreads = 5;

        array = generateRandomArray(size);

        long startTime = System.currentTimeMillis();
        selectionSortMultiThread(array, numThreads);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Sorted array: " + Arrays.toString(array));
        System.out.println("Time taken: " + elapsedTime + " ms");

        updateCsvFile(numThreads, elapsedTime);
    }

    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000); // Generating random integers between 0 and 999
        }
        return arr;
    }

    private static void updateCsvFile(int numThreads, long time) {
        String filename = "selectionsort_parallel_results.csv";
        File file = new File(filename);
        boolean fileExists = file.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (!fileExists) {
                writer.println("Threads,Time");
            }
            writer.println(numThreads + "," + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void selectionSortMultiThread(int[] arr, int numThreads) {
        selectionSort(arr, 0, arr.length - 1, numThreads);
    }

    private static void selectionSort(int[] arr, int left, int right, int numThreads) {
        if (left < right) {
            if (numThreads > 1 && right - left >= numThreads) {
                List<Thread> threads = new ArrayList<>();
                int batchSize = (right - left + 1) / numThreads;
                for (int i = 0; i < numThreads; i++) {
                    final int start = left + i * batchSize;
                    final int end = i == numThreads - 1 ? right : start + batchSize - 1;
                    Thread thread = new Thread(() -> {
                        selectionSortSequential(arr, start, end);
                    });
                    threads.add(thread);
                    thread.start();
                }
                for (Thread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                selectionSortSequential(arr, left, right);
            }
        }
    }

    private static void selectionSortSequential(int[] arr, int left, int right) {
        for (int i = left; i <= right; i++) {
            int minIndex = i;
            for (int j = i + 1; j <= right; j++) {
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
