import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVPlotter extends JPanel {
    private int[] xData;
    private int[] yData;

    public CSVPlotter(int[] xData, int[] yData) {
        this.xData = xData;
        this.yData = yData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Set up the drawing area
        int padding = 25;
        int width = getWidth();
        int height = getHeight();
        int xStart = padding;
        int yStart = height - padding;
        int xEnd = width - padding;
        int yEnd = padding;

        // Draw x and y axes
        g2.drawLine(xStart, yStart, xEnd, yStart); // x-axis
        g2.drawLine(xStart, yStart, xStart, yEnd); // y-axis

        // Calculate scaling factors
        int xMin = findMin(xData);
        int xMax = findMax(xData);
        int yMin = findMin(yData);
        int yMax = findMax(yData);

        // Adjust scaling factors to fit data within the plotting area
        int xRange = xMax - xMin;
        int yRange = yMax - yMin;
        double xScale = (double) (xEnd - xStart) / xRange;
        double yScale = (double) (yEnd - yStart) / yRange;

        // Plot the data points
        g2.setColor(Color.RED);
        for (int i = 0; i < xData.length; i++) {
            int x = (int) ((xData[i] - xMin) * xScale + xStart);
            int y = (int) (height - ((yData[i] - yMin) * yScale + yEnd));
            g2.fillOval(x - 2, y - 2, 4, 4);
        }
    }

    private int findMin(int[] array) {
        int min = array[0];
        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        // Path to your CSV file
        String csvFile = "src/sort_results_serial.csv";

        // Read data from CSV
        int[] xData;
        int[] yData;
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            // Skip the first line (header)
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                xList.add(Integer.parseInt(values[0]));
                yList.add(Integer.parseInt(values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        xData = xList.stream().mapToInt(Integer::intValue).toArray();
        yData = yList.stream().mapToInt(Integer::intValue).toArray();

        // Create and configure the JFrame
        JFrame frame = new JFrame("CSV Plotter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Add the CSVPlotter panel to the frame
        CSVPlotter plotter = new CSVPlotter(xData, yData);
        frame.add(plotter);

        // Make the frame visible
        frame.setVisible(true);
    }
}
