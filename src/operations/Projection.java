package operations;

import javafx.collections.ObservableList;
import models.TableItemPayback;

/**
 * Created by Martín Ruíz on 4/19/2017.
 */
public class Projection {

    public static ObservableList<TableItemPayback> calculatePayback(ObservableList<TableItemPayback> data, double principal, double interestRate){//recibir parametros

        interestRate =+ 1;                                            //Interest rate
        int p = data.size()-1;                                                      //Periods
        double accumulated = 0.0;                                       //Accumulated
        double netFlow = 0;

        data.get(0).setCumulative(data.get(0).getInflow() - data.get(0).getOutflow());
        accumulated = data.get(0).getInflow() - data.get(0).getOutflow();

        for (int i=1; i<=p; i++) {
            netFlow = data.get(i).getInflow() - data.get(i).getOutflow();
            accumulated =(netFlow+(accumulated*interestRate));
            data.get(i).setCumulative((float) accumulated);
        }
        return data;
    }

}
