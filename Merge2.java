package MergeSortVisualizer;

import javafx.application.Application;
import java.util.List;
import java.util.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Merge2 extends Application {
    private static final int ARRAY_SIZE = 16;
    private static int counter = 0;
    private static final int RECTANGLE_WIDTH = 30;
    private static final int RECTANGLE_HEIGHT_MULTIPLIER = 200;

    private Rectangle[] rectangles;
    private int count=0;
    private int[] array;
    private List<int[]> ar = new ArrayList<>();
    private Button stepButton;
    private Button hazemsButton;
    private Button resetButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        array = generateRandomArray(ARRAY_SIZE);
        rectangles = createRectangles(array, false);

        HBox root = new HBox(3);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(rectangles);
        sort(array.clone(), 0, array.length-1);

        stepButton = new Button("Next Step");
        resetButton = new Button("Generate New Array");
        hazemsButton = new Button("Go Back");
        stepButton.setOnAction(event -> {
            if(count+1 == ar.size())
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("MergeSort Complete!");
                alert.setContentText("MergeSort has finished sorting the array");

                alert.showAndWait();
            }
            else{
            count += 1;
            array = ar.get(count);
            rectangles = createRectangles(array, false);
            array = ar.get(count);
            root.getChildren().clear();
            root.getChildren().addAll(rectangles);
            if(count+1 == ar.size())
            {
                rectangles = createRectangles(array, true);
                root.getChildren().clear();
                root.getChildren().addAll(rectangles);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("MergeSort Complete!");
                alert.setContentText("MergeSort has finished sorting the array");

                alert.showAndWait();
            }
            }
        });
        resetButton.setOnAction(event -> {
            ar.clear();
            array = generateRandomArray(ARRAY_SIZE);
            count = 0;
            rectangles = createRectangles(array, false);
            root.getChildren().clear();
            root.getChildren().addAll(rectangles);
            sort(array.clone(), 0, array.length-1);
        });
        hazemsButton.setOnAction(event -> {
            if(count-1 == -1 && counter == 0)
            {
                counter++;
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("We're at the beginning of the array you idiot");
                alert.setContentText("What kind of buffoon are you? We just started sorting! Be patient! Jesus!");

                alert.showAndWait();
            }
            else if(count-1 == -1 && counter == 1)
            {
                counter++;
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("I said chill!");
                alert.setContentText("Bro! Stop spamming! I swear if you press that button one more time, I'm closing the app");

                alert.showAndWait();
            }
            else if(count-1 == -1 && counter == 2)
            {
                System.exit(0);
            }
            else
            {
            count--;
            array = ar.get(count);
            rectangles = createRectangles(array, false);
            array = ar.get(count);
            root.getChildren().clear();
            root.getChildren().addAll(rectangles);
            }
        });
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(root, stepButton, hazemsButton, resetButton);

        primaryStage.setTitle("Merge Sort Visualization");
        primaryStage.setScene(new Scene(vbox));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    void merge(int arr[], int l, int m, int r)
    {
        
        int n1 = m - l + 1;
        int n2 = r - m;
 
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        int i = 0, j = 0;
 
        
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    
    
    void sort(int arr[], int l, int r)
    {
        if (l < r) {
 
            int m = l + (r - l) / 2;
 
            sort(arr, l, m);
            sort(arr, m + 1, r);

            merge(arr, l, m, r);
            ar.add(arr.clone());
        }
    }

    private Rectangle[] createRectangles(int[] array, boolean d) {
        Rectangle[] rectangles = new Rectangle[array.length];
        int z = findMax(array);
        for (int i = 0; i < array.length; i++) {
            
            Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT_MULTIPLIER*((double)array[i]/z));
            if (d)
            {
                rectangle.setFill(Color.GREEN);
            }
            else
            {
                rectangle.setFill(Color.RED);
            }
            rectangles[i] = rectangle;
        }
        return rectangles;
    }

    private int findMax(int[] array)
    {
        int max = 0;
        for(int x: array)
        {
            if (x > max)
            max = x;
        }
        return max;
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * size) + 1;
        }
        ar.add(array);
        return array;
    }
}
