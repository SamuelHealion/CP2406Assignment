package rainfallanalyser.cp2406assignment;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This file can be used to draw a chart that effectively represents rainfall data.  Just fill in
 * the definition of drawPicture with the code that draws your picture.
 */
public class RainfallVisualiser extends Application {

    /**
     * Draws a picture.  The parameters width and height give the size
     * of the drawing area, in pixels.
     */
    public void drawPicture(GraphicsContext g, int width, int height) {

        // Create the x-axis and y-axis
        int border_width = 10;
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeLine(border_width, border_width, border_width, height - border_width);
        g.strokeLine(border_width, height - border_width, width - border_width, height - border_width);

        TextIO.getln(); // Remove the first line

        ArrayList<Double> allMonthlyTotals = new ArrayList<>();

        double maxMonthlyTotal = Double.NEGATIVE_INFINITY;
        // Read the file first and create an array of the monthly totals
        while (!TextIO.eof()) {
            String[] line = TextIO.getln().trim().strip().split(",");
            double monthlyTotal = Double.parseDouble(line[2]);
            allMonthlyTotals.add(monthlyTotal);
            if (monthlyTotal > maxMonthlyTotal)
                maxMonthlyTotal = monthlyTotal;
        }

        double xAxisLength = width - 2 * border_width;
        double yAxisLength = height - 2 * border_width;
        double currentXPos = border_width;
        double barWidth = xAxisLength / allMonthlyTotals.size();

        g.setFill(Color.BLUE);
        g.setLineWidth(0.5);

        for (Double monthlyTotal : allMonthlyTotals) {
            // Get the height of the column relative to the maximum height
            double columnHeight = (monthlyTotal / maxMonthlyTotal) * yAxisLength;

            // Draw the rectangle representing the rainfall and draw a black outline
            g.fillRect(currentXPos, height - border_width - columnHeight, barWidth, columnHeight);
            g.strokeRect(currentXPos, height - border_width - columnHeight, barWidth, columnHeight);

            currentXPos += barWidth;
        }
    } // end drawPicture()


    //------ Implementation details: DO NOT EDIT THIS ------
    public void start(Stage stage) {
        int width = 218 * 6 + 40;
        int height = 500;
        Canvas canvas = new Canvas(width, height);
        drawPicture(canvas.getGraphicsContext2D(), width, height);
        BorderPane root = new BorderPane(canvas);
        root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Rainfall Visualiser");
        stage.show();
        stage.setResizable(false);
    }
    //------ End of Implementation details ------


    public static void main(String[] args) {
//        System.out.print("Enter path: ");
//        var path = TextIO.getln();

//        var path = "rainfalldata_analysed/MountSheridanStationCNS_analysed.csv";
        var path = "rainfalldata_analysed/IDCJAC0009_031205_1800_Data_analysed.csv";
        TextIO.readFile(path);
        launch();
    }

} // end SimpleGraphicsStarter
