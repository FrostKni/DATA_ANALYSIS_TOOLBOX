package Controller.Algorithms.Normalization;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;

public class Normalization {

    private NumericColumn<?>[] numericColumns;
    private double minDouble;
    private double maxDouble;
    private double meanDouble;
    private int minInt;
    private int maxInt;
    private int meanInt;
    private double stdDouble;
    private double q1Double;
    private double q3Double;
    private double q1Int;
    private double q3Int;
    public Table minMaxScaler(Table data) {
        numericColumns = data.numericColumns().toArray(new NumericColumn[0]);

        for (NumericColumn<?> column : numericColumns) {
            if (column.type().toString().equals("DOUBLE") && !column.name().equals("Index")) {
                DoubleColumn temp = (DoubleColumn) data.column(column.name());

                minDouble = column.min();
                maxDouble = column.max();
                meanDouble = column.mean();


                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the normalized value
                for (int i = 0; i < temp.size(); i++) {
                    double x = temp.getDouble(i);
                    double res = (x - minDouble) / (maxDouble - minDouble);
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }

            if (column.type().toString().equals("INTEGER")&& !column.name().equals("Index")) {
                IntColumn temp = (IntColumn) data.column(column.name());

                minInt = (int) column.min();
                maxInt = (int) column.max();
//                meanInt = (int) column.mean();

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the normalized value
                for (int i = 0; i < temp.size(); i++) {
                    int x = temp.getInt(i);
                    double res =  ((double) (x - minInt) / (maxInt - minInt));
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }
        }
        return data;
    }



    public Table standardized(Table data) {
        numericColumns = data.numericColumns().toArray(new NumericColumn[0]);

        for (NumericColumn<?> column : numericColumns) {
            if (column.type().toString().equals("DOUBLE")&& !column.name().equals("Index")) {
                DoubleColumn temp = (DoubleColumn) data.column(column.name());

                meanDouble = column.mean();
                stdDouble = column.standardDeviation();

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the standardized value
                for (int i = 0; i < temp.size(); i++) {
                    double x = temp.getDouble(i);
                    double res = (x - meanDouble) / stdDouble;
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }

            if (column.type().toString().equals("INTEGER")&& !column.name().equals("Index")) {
                IntColumn temp = (IntColumn) data.column(column.name());

                meanDouble = column.mean();
                stdDouble = column.standardDeviation();

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the standardized value
                for (int i = 0; i < temp.size(); i++) {
                    int x = temp.getInt(i);
                    double res = ((double) x - meanDouble) / stdDouble;
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }
        }
        return data;
    }


    //



    public Table robustScaling(Table data) {
        numericColumns = data.numericColumns().toArray(new NumericColumn[0]);

        for (NumericColumn<?> column : numericColumns) {
            if (column.type().toString().equals("DOUBLE")&& !column.name().equals("Index")) {
                DoubleColumn temp = (DoubleColumn) data.column(column.name());

                q1Double = temp.percentile(25);
                q3Double = temp.percentile(75);

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the robust scaled value
                for (int i = 0; i < temp.size(); i++) {
                    double x = temp.getDouble(i);
                    double res = (x - q1Double) / (q3Double - q1Double);
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }

            if (column.type().toString().equals("INTEGER")&& !column.name().equals("Index")) {
                IntColumn temp = (IntColumn) data.column(column.name());

                q1Int = temp.percentile(25);
                q3Int = temp.percentile(75);

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the robust scaled value
                for (int i = 0; i < temp.size(); i++) {
                    int x = temp.getInt(i);
                    double res = ((double) x - q1Int) / (q3Int - q1Int);
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }
        }
        return data;
    }

    public Table logTransformation(Table data) {
        numericColumns = data.numericColumns().toArray(new NumericColumn[0]);

        for (NumericColumn<?> column : numericColumns) {
            if (column.type().toString().equals("DOUBLE")&& !column.name().equals("Index")) {
                DoubleColumn temp = (DoubleColumn) data.column(column.name());

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the log(1 + Y - min(Y))
                for (int i = 0; i < temp.size(); i++) {
                    double x = temp.getDouble(i);
                    double res = Math.log1p(x - temp.min());
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }

            if (column.type().toString().equals("INTEGER")&& !column.name().equals("Index")) {
                IntColumn temp = (IntColumn) data.column(column.name());

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the log(1 + Y - min(Y))
                for (int i = 0; i < temp.size(); i++) {
                    int x = temp.getInt(i);
                    double res = Math.log1p(x - temp.min());
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }
        }
        return data;
    }


    ///Box-Cox Transformation
    public Table box_cox_transformation(Table data) {
        numericColumns = data.numericColumns().toArray(new NumericColumn[0]);

        for (NumericColumn<?> column : numericColumns) {
            if (column.type().toString().equals("DOUBLE")&& !column.name().equals("Index")) {
                DoubleColumn temp = (DoubleColumn) data.column(column.name());

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the log(1 + Y - min(Y))
                for (int i = 0; i < temp.size(); i++) {
                    double x = temp.getDouble(i);
                    double res = Math.log1p(x - temp.min());
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }

            if (column.type().toString().equals("INTEGER")&& !column.name().equals("Index")) {
                IntColumn temp = (IntColumn) data.column(column.name());

                // Create a new column with the same name as temp and the same size
                DoubleColumn tempCol = DoubleColumn.create(temp.name(), temp.size());

                // Set all values in the new column to the log(1 + Y - min(Y))
                for (int i = 0; i < temp.size(); i++) {
                    int x = temp.getInt(i);
                    double res = Math.log1p(x - temp.min());
                    tempCol.set(i, res);
                }

                // Replace the original column with the new column
                data.replaceColumn(column.name(), tempCol);
            }
        }
        return data;
    }

}
