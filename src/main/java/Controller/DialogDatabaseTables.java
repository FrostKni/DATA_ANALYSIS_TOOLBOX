package Controller;

import Table_Formation.connection_mysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class DialogDatabaseTables {
    private List<String> selectedTables;

    @FXML
    private Button ok;

    @FXML
    private ListView<CheckBox> table_choice;

    public void initialize()
    {
        // get the list of tables from the database
        connection_mysql cm = new connection_mysql();
        ResultSet rs = cm.getRs();

        // add the tables to the listview
        String [] tables_name = new String[100];
        int i=0;
        try {
            while (rs.next()) {
                tables_name[i] = rs.getString(1);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<CheckBox> items = FXCollections.observableArrayList();
        for (String s : tables_name) {
            items.add(new CheckBox(s));
        }
        table_choice.setItems(items);
        table_choice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }



    @FXML
    void submit(MouseEvent event) {
        selectedTables = new java.util.ArrayList<>();
        for (CheckBox cb : table_choice.getItems()) {
            if (cb.isSelected()) {
                selectedTables.add(cb.getText());
            }
        }
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    public List<String> getSelectedColumns() {
        return selectedTables;
    }

}
