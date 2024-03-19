package org.analyze.main;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDocxWithTextAndImage extends Application {

    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        Button createDocButton = new Button("Create Word Document");
        createDocButton.setOnAction(event -> createWordDocument());

        imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Button loadImageBtn = new Button("Load Image");
        loadImageBtn.setOnAction(event -> loadImage());

        VBox root = new VBox(10, createDocButton, loadImageBtn, imageView);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Word Document Example");
        primaryStage.show();
    }

    private void createWordDocument() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Document (*.docx)", "*.docx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (XWPFDocument document = new XWPFDocument()) {
                // Add text to the document
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setBold(true);
                run.setText("This is a sample Word document created using JavaFX and Apache POI.");
                run.setCapitalized(false);
                run.addBreak();
                run.addTab();
                run.addTab();
                Table  table1= Table.read().csv("/home/frostknight/Cluster -  Chinese Prefecture copy.csv");
                String[]  columnNames  =  table1.columnNames().toArray(new String[0]);
                //convert all columns to string column type
                XWPFTable table = document.createTable(table1.rowCount()+1, table1.columnCount());
                for (int row = 0; row <=table1.rowCount(); row++) {
                    XWPFTableRow tableRow = table.getRow(row);
                    for (int col = 0; col < table1.columnCount(); col++) {
                        XWPFTableCell cell = tableRow.getCell(col);
                        if(row==0)
                        {
                            cell.setText(columnNames[col]);
                        }
                        else {
                            if (table1.column(col).type().toString().equals("DOUBLE")) {
                                cell.setText(String.valueOf(table1.get(row-1, col)));
                            } else if (table1.column(col).type().toString().equals("INTEGER")) {
                                cell.setText(String.valueOf(table1.get(row-1, col)));
                            } else {
                                cell.setText((String) table1.get(row-1, col));
                            }
                        }
                    }
                }


                // Add image to the document
                if (imageView.getImage() != null) {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", os);
                    InputStream is = new ByteArrayInputStream(os.toByteArray());
                    run.addPicture(is, Document.PICTURE_TYPE_PNG, "image.png", Units.toEMU(200), Units.toEMU(200));
                }

                // Write the document to file
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    document.write(fos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
