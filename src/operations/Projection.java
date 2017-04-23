package operations;

import javafx.collections.ObservableList;
import models.PaybackRow;

/**
 * Created by Martín Ruíz on 4/19/2017.
 */
public class Projection {

    public static ObservableList<PaybackRow> calculatePayback(ObservableList<PaybackRow> data, double principal, double interestRate){//recibir parametros

        interestRate = (interestRate/100)+1;                            //Interest rate
        int p = data.size()-1;                                        //Periods
        double accumulated = 0.0;                                     //Accumulated
        double netFlow = 0;

        accumulated = data.get(0).getInflow() - data.get(0).getOutflow();
        data.get(0).setCumulativeCashFlow(accumulated);
        data.get(0).setNetCashFlow(accumulated);

        for (int i=1; i<=p; i++) {
            netFlow = data.get(i).getInflow() - data.get(i).getOutflow();
            data.get(i).setNetCashFlow(netFlow);
            accumulated =(netFlow+(data.get(i-1).getCumulativeCashFlow()*interestRate));
            data.get(i).setCumulativeCashFlow((float) accumulated);
        }
        return data;
    }
    public static int paybackGetROIPeriod(ObservableList<PaybackRow> data){
        for (int i = 0; i < data.size(); i++) {
            PaybackRow temp = data.get(i);
            if(temp.getCumulativeCashFlow()>0){
                return i;
            }
        }
        return -1;
    }

}
