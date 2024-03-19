package org.analyze.main;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TableViewImageSaver extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a VBox with some content
        VBox content = new VBox();
        for (int i = 0; i < 20; i++) {
            content.getChildren().add(new Group()); // Adding some placeholder content
        }

        // Put the content into a ScrollPane
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setPrefSize(300, 200); // Set preferred size for demonstration

        // Create a Scene with the ScrollPane
        Scene scene = new Scene(scrollPane);

        // Set up the Stage
        primaryStage.setScene(scene);
        primaryStage.show();

        // Capture the scrollable snapshot
        captureScrollableSnapshot(scrollPane, "scrollable_snapshot.png");
    }

    private void captureScrollableSnapshot(ScrollPane scrollPane, String filename) {
        Bounds bounds = scrollPane.getContent().getBoundsInLocal();
        WritableImage image=null;
        if(bounds.getWidth() < 0&& bounds.getHeight() < 0) {
            image = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
        }


        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight()));

        // Capture the snapshot
        WritableImage snapshot = scrollPane.getContent().snapshot(parameters, image);

        // Save the image
        File file = new File(filename);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
            System.out.println("Snapshot saved as " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save snapshot: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

