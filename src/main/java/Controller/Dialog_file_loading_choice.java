package Controller;

import Table_Formation.File_to_table;
import Table_Formation.connection_mysql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tech.tablesaw.api.Table;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dialog_file_loading_choice {

    @FXML
    private ChoiceBox<String> db_choice;

    @FXML
    private TextField db_textfield;

    @FXML
    private Button ok;

    @FXML
    private Button open;

    @FXML
    private PasswordField password_textfield;

    @FXML
    private TextField server_textfield;

    @FXML
    private TextField user_textfield;

    private  Table table;

   public void initialize()
    {
        db_choice.getItems().add("MariaDb / MySQL");
    }

    @FXML
    void file_load(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");

        File csvFile = fileChooser.showOpenDialog(new Stage());
        if (csvFile != null) {
            String location_of_file = csvFile.getPath();

            table = new File_to_table().file_to_table(location_of_file);

        }
        else {
            //error pop up window
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UTILITY);
            popupStage.setTitle("Error");

            // Create a label with the error message
            Label label = new Label("Please select a csv file");

            // Set up the layout for the pop-up
            StackPane layout = new StackPane();
            layout.getChildren().add(label);

            // Set up the scene and attach it to the stage
            Scene scene = new Scene(layout, 250, 150);
            popupStage.setScene(scene);

            // Show the pop-up window
            popupStage.showAndWait();

        }
        Stage stage = (Stage) open.getScene().getWindow();
        stage.close();

    }

    @FXML
    void submit(MouseEvent event) throws IOException, SQLException {
       if(server_textfield.getText().equals("") && user_textfield.getText().equals("") && password_textfield.getText().equals("") && db_textfield.getText().equals(""))
       {
              //error pop up window
              Stage popupStage = new Stage();
              popupStage.initModality(Modality.APPLICATION_MODAL);
              popupStage.initStyle(StageStyle.UTILITY);
              popupStage.setTitle("Error");

              // Create a label with the error message
              Label label = new Label("Please fill all the fields");

              // Set up the layout for the pop-up
              StackPane layout = new StackPane();
              layout.getChildren().add(label);

              // Set up the scene and attach it to the stage
              Scene scene = new Scene(layout, 250, 150);
              popupStage.setScene(scene);

              // Show the pop-up window
              popupStage.showAndWait();
       }
       else {
           String server = server_textfield.getText();
           String user = user_textfield.getText();
           String password = password_textfield.getText();
           String db = db_textfield.getText();

              int i = connection_mysql.connection_status(server, user, password, db);
           Stage popupStage = new Stage();
           popupStage.initModality(Modality.APPLICATION_MODAL);
           popupStage.initStyle(StageStyle.UTILITY);
           popupStage.setTitle("Error");
           Label label;
           StackPane layout = new StackPane();
           if(i==1)
              {
                  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dialog_database_tables.fxml"));
                  Parent root=  fxmlLoader.load();
                  Stage stage = new Stage();
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.initStyle(StageStyle.UTILITY);
                  stage.setTitle("Dataset Chooser ");
                  stage.setScene(new Scene(root));
                  stage.showAndWait();

                    DialogDatabaseTables dialogDatabaseTables = fxmlLoader.getController();
                  List<String> table_names = dialogDatabaseTables.getSelectedColumns();
                  System.out.println(table_names);
                  //
                  connection_mysql sq = new connection_mysql();
                  for(int j=0;j<table_names.size();j++)
                  {
                        for (int k=0;k<table_names.size() && j!=k;k++)
                        {
                            ResultSet rs = sq.execute_query("SELECT column_name\n" +
                                    "FROM information_schema.columns\n" +
                                    "WHERE table_name = '"+table_names.get(j)+"'\n" +
                                    "INTERSECT\n" +
                                    "SELECT column_name\n" +
                                    "FROM information_schema.columns\n" +
                                    "WHERE table_name = '"+table_names.get(k)+"';");

                            if(rs!=null)
                            {
                               rs.next();
                                String column_name = rs.getString(1);
                                        //get the join of the two tables
                                ResultSet rs1 = sq.execute_query("SELECT * FROM "+table_names.get(j)+" JOIN "+table_names.get(k)+" ON "+table_names.get(j)+"."+column_name+"= "+table_names.get(k)+"."+column_name+";");
                                //get the result set and convert it to a table
                                Table table = Table.read().db(rs1,"school");
                                System.out.println(table);
                            }


                        }
                  }
              }else {
                     label = new Label("Connection failed");
                    layout.getChildren().add(label);
                    Scene scene = new Scene(layout, 250, 150);
                     popupStage.setScene(scene);

           }
           popupStage.showAndWait();

       }
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    public  Table getTable()
    {
        return  table;
    }

}
