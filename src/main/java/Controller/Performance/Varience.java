package Controller.Performance;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;

public class Varience {
    public double variance_calculate(Table data) {
        double[] mean = new double[data.columnCount()];
        double[] varience = new double[data.columnCount()];
        // skip the coloumn name index
        // skipe the column of type string
        for (int i = 0; i < data.columnCount(); i++) {
          //
            ColumnType type = data.column(i).type();
            // column name is index skip it
            if(data.column(i).name().equals("Index")){
                continue;
            }
            // if column type is string skip it
            if(type==ColumnType.STRING){
                continue;
            }
            if(type==ColumnType.DOUBLE || type==ColumnType.INTEGER){
                mean[i] = data.nCol(i).mean();
                varience[i] = data.nCol(i).variance();
            }



        }
        // calucate the mean of varience
        // skipe the varience which is 0
        int count = 0;
        double temp_mean = 0;
      for(int i=0;i< varience.length;i++)
      {

          if(varience[i]!=0){
              // add the valence in the temp_mean
             temp_mean   = mean[i];
                count++;
          }
      }

        return (temp_mean/count);




    }

}
