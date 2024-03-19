package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DialogAddReport {

    @FXML
    private CheckBox checked_histogram_plot;

    @FXML
    private CheckBox checked_outlier_plot;

    @FXML
    private CheckBox checked_sample_table;

    @FXML
    private CheckBox checked_scatter_plot;

    @FXML
    private CheckBox checked_summary_table;

    @FXML
    private TextArea histogram_plot_area;

    @FXML
    private TextArea outlier_plot_area;

    @FXML
    private Button print;

    @FXML
    private TextArea sample_table_area;

    @FXML
    private TextArea scatter_plot_area;

    @FXML
    private TextArea summary_table_area;

    private   String sample_table,summary_table,histogram_plot,outlier_plot,scatter_plot;


    public  void  initialize()
    {
        sample_table_area.setText("The sample data provided by the user offers a snapshot of their dataset, providing a glimpse into the structure and content of their information \n ");
        summary_table_area.setText("In the summary table, users can quickly grasp essential information such as total records, mean values, median values, and standard deviations, providing a comprehensive snapshot of their dataset.");
        histogram_plot_area.setText("""
                1)Visualizes the distribution of data across all combinations of column names.\s
                2)Each column is represented on both the X and Y axes.\s
                3)Provides insights into the relationships between different variables within the dataset.""");
        outlier_plot_area.setText("""
                1)Specifically designed for outlier detection.\s
                2)Visualizes the distribution of data points against their respective quartiles.\s
                3)Identification of potential outliers and anomalies within the dataset.""");
        scatter_plot_area.setText("1)Illustrates relationships and correlations between variables. \n" +
                "2)Facilitates deeper insights and exploration of the dataset.");


        //checkbox on check operations
        checked_sample_table.setOnMouseClicked(event -> {
            if(checked_sample_table.isSelected())
            {
                sample_table_area.setDisable(true);
            }
            else {
                sample_table_area.setDisable(false);
            }
        });

        checked_summary_table.setOnMouseClicked(event -> {
            if(checked_summary_table.isSelected())
            {
                summary_table_area.setDisable(true);
            }
            else {
                summary_table_area.setDisable(false);
            }
        });

        checked_histogram_plot.setOnMouseClicked(event -> {
            if(checked_histogram_plot.isSelected())
            {
                histogram_plot_area.setDisable(true);
            }
            else {
                histogram_plot_area.setDisable(false);
            }
        });

        checked_outlier_plot.setOnMouseClicked(event -> {
            if(checked_outlier_plot.isSelected())
            {
                outlier_plot_area.setDisable(true);
            }
            else {
                outlier_plot_area.setDisable(false);
            }
        });

        checked_scatter_plot.setOnMouseClicked(event -> {
            if(checked_scatter_plot.isSelected())
            {
                scatter_plot_area.setDisable(true);
            }
            else {
                scatter_plot_area.setDisable(false);
            }
        });

    }

    @FXML
    void Submit(MouseEvent event) {

        if(checked_sample_table.isSelected())
        {
            sample_table=sample_table_area.getText();
        }
        if(checked_summary_table.isSelected())
        {
            summary_table=summary_table_area.getText();
        }
        if(checked_histogram_plot.isSelected())
        {
            histogram_plot=histogram_plot_area.getText();
        }
        if(checked_outlier_plot.isSelected())
        {
            outlier_plot=outlier_plot_area.getText();
        }
        if(checked_scatter_plot.isSelected())
        {
            scatter_plot=scatter_plot_area.getText();
        }

        Stage stage = (Stage) print.getScene().getWindow();
        stage.close();

    }

    public  String getSample_table()
    {
        return sample_table;
    }

    public  String getSummary_table()
    {
        return summary_table;
    }

    public  String getHistogram_plot()
    {
        return histogram_plot;
    }

    public  String getOutlier_plot()
    {
        return outlier_plot;
    }

    public  String getScatter_plot()
    {
        return scatter_plot;
    }


}
