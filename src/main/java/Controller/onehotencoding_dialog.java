package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import tech.tablesaw.api.CategoricalColumn;
import tech.tablesaw.api.Table;

public class onehotencoding_dialog {

    @FXML
    private Button buttonOK;

    @FXML
    private ListView<CheckBox> column;


    @FXML
    void Submit(MouseEvent event) {

    }

    public  void initialize() {
        // adding checkbox in the choice box

        Table table = Descriptor.get_Table_with_index();

        CategoricalColumn<?>[]  cat_col  = table.categoricalColumns().toArray(new CategoricalColumn[0]);

        //add  all elements  from array d  into  some structure
        //such that if  d array having element d=[name,surname,middle,gender]
        //the user can select  any no. of choices from array d which is in some structure
        //like i want to select name and gender to this structure select the same
        //here

        ObservableList<CheckBox> items = FXCollections.observableArrayList();
        for(CategoricalColumn<?> col : cat_col)
        {
            items.add(new CheckBox(col.name()));
        }
        column.setItems(items);
        column.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

}