import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

class BubbleSortThread extends Thread {
    private int[] array;
    private int start;
    private int end;

    public BubbleSortThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            for (int j = start; j < end - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}

public class BubbleSortParallel {
    public static void main(String[] args) {
        int arraySize = 100000;
        int threadCount = 10;

        int[] array = generateRandomArray(arraySize);

        long startTime = System.currentTimeMillis();

        BubbleSortThread[] threads = new BubbleSortThread[threadCount];
        int chunkSize = arraySize / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = (i == threadCount - 1) ? arraySize : (i + 1) * chunkSize;
            threads[i] = new BubbleSortThread(array, start, end);
            threads[i].start();
        }

        // Joining threads
        for (BubbleSortThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Sorted Array: " + Arrays.toString(array));
        System.out.println("Time taken for sorting with " + threadCount + " threads: " + elapsedTime + " ms");

        writeCsv(threadCount, elapsedTime);
    }

    private static int getUserInput(String message) {
        System.out.print(message);
        return Integer.parseInt(System.console().readLine());
    }

    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }

    private static void writeCsv(int threadCount, long elapsedTime) {
        try {
            FileWriter csvWriter = new FileWriter("bubble_sort_results.csv", true);
            csvWriter.append(threadCount + "," + elapsedTime + "\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
