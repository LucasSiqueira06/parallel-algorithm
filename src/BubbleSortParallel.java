import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class BubbleSortParallel {
    public static void main(String[] args) {
        int size = 150000;
        int numThreads = 6;

        int[] array = generateRandomArray(size);

        long startTime = System.nanoTime();

        parallelBubbleSort(array, numThreads);

        long endTime = System.nanoTime();

        long timeTaken = (endTime - startTime) / 1000000;

        System.out.println("Time taken for sorting: " + timeTaken + " ms");

        exportToCSV(size, numThreads, timeTaken);
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }

    private static void parallelBubbleSort(int[] array, int numThreads) {
        int n = array.length;
        CountDownLatch latch = new CountDownLatch(numThreads);

        int chunkSize = (int) Math.ceil((double) n / numThreads);

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min((i + 1) * chunkSize, n);
            Thread thread = new Thread(new BubbleSortTask(array, startIndex, endIndex, latch));
            thread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class BubbleSortTask implements Runnable {
        private final int[] array;
        private final int startIndex;
        private final int endIndex;
        private final CountDownLatch latch;

        public BubbleSortTask(int[] array, int startIndex, int endIndex, CountDownLatch latch) {
            this.array = array;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.latch = latch;
        }

        @Override
        public void run() {
            for (int i = startIndex; i < endIndex; i++) {
                for (int j = startIndex; j < endIndex - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
            latch.countDown();
        }
    }

    private static void exportToCSV(int size, int numThreads, long timeTaken) {
        String csvFile = "bubblesort_parallel_results.csv";
        File file = new File(csvFile);
        boolean append = file.exists();
        
        try (FileWriter writer = new FileWriter(csvFile, append)) {
            if (!append) {
                writer.write("Array Size,Threads,Time taken (ms)\n");
            }
            writer.write(size + "," + numThreads + "," + timeTaken + "\n");
            System.out.println("Results exported to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
