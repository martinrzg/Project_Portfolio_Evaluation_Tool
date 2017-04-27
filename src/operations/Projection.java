package operations;

import com.sun.istack.internal.Nullable;
import javafx.collections.ObservableList;
import models.DepreciationRow;
import models.NPVRow;
import models.PaybackRow;

import java.text.DecimalFormat;

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

            data.get(i).setCumulativeCashFlow(formatDecimals(accumulated));
        }
        return data;
    }

    public static double formatDecimals(double toFormat){
        double x = toFormat;
        DecimalFormat df = new DecimalFormat("#.###");
        String dx=df.format(x);
        x=Double.valueOf(dx);
        return x;
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

    public static ObservableList<NPVRow> calculateNPV(ObservableList<NPVRow> data, double interestRate, double taxRate){
        //System.out.println("ANTES     interestR: "+interestRate+ "  TaxRate: "+taxRate );
        double specialTaxRate = 1+ (taxRate/100);
        taxRate = (1) - (taxRate/100);
        interestRate = 1 + (interestRate/100);
        //System.out.println("BEFORE    interestR: "+interestRate+ "  TaxRate: "+taxRate );
        double netCashFlow = data.get(0).getInflow()-data.get(0).getOutflow();
        data.get(0).setNetCashFlow(netCashFlow);
        double npv = ((data.get(0).getNetCashFlow())*specialTaxRate)/1;
        //System.out.println("NPV"+npv);
        data.get(0).setNetPresentValue(npv);
        data.get(0).setCumulativeNPV(npv);

        for (int i = 1; i < data.size(); i++) {
            NPVRow temp = data.get(i);
            netCashFlow = temp.getInflow()-temp.getOutflow();
            temp.setNetCashFlow(formatDecimals(netCashFlow));
            npv = (temp.getNetCashFlow()*taxRate)/(Math.pow(interestRate,temp.getPeriod()));
            temp.setNetPresentValue(formatDecimals(npv));
            double cumulativeNPV =npv+ data.get(i-1).getCumulativeNPV();
            temp.setCumulativeNPV(formatDecimals(cumulativeNPV));
        }

        return data;
    }


    private static double[] macrs3 = {0, 33.33,44.45,14.81,7.41};
    private static double[] macrs5 = {0, 20, 32, 19.20, 11.52, 11.52, 5.76};
    private  static double[] macrs7 = {0, 14.29, 24.49, 17.49, 12.49, 8.93, 8.92, 8.93, 4.46};
    private static double[] macrs10 = {0, 10, 18, 14.4, 11.52, 9.22, 7.37, 6.55, 6.55, 6.56, 6.55, 3.28};
    private static double[] macrs15 = {0, 5, 9.50, 8.55, 7.70, 6.93, 6.23, 5.90, 5.90, 5.91, 5.90, 5.91, 5.90, 5.91, 5.90, 5.91, 2.95};
    private static double[] macrs20 = {0, 3.750, 7.219, 6.677, 5.713, 5.285, 4.888, 4.522, 4.462, 4.461, 4.462, 4.461, 4.462, 4.461, 4.462, 4.461, 4.462, 4.461, 4.462, 4.461, 2.231};

    private static double[] getCategory(int cat){
        double [] porsiAcaso = {0};
        switch (cat){
            case 3:
                return macrs3;
            case 5:
                return macrs5;
            case 7:
                return macrs7;
            case 10:
                return macrs10;
            case 15:
                return macrs15;
            case 20:
                return macrs20;
        }
        return porsiAcaso;
    }
    //TODO adapt to observable list
    public static void calculateDepreciation(ObservableList<DepreciationRow> data, int periods, double tax, double principal, double sv, int cat, int year0, int svPeriod){

        double cate[] = getCategory(cat);
        tax = tax/100;
        //Ledger value 0
        data.get(0).setValueInLedgers(formatDecimals(principal));

        if(cate.length >= 5)                                        //MACRS
        {
            System.out.println("Enters MACRS");
            if(periods <= cate.length) {
                //Dep rate segun categoria y años
                for (int i = 0; i < periods; i++) {
                    data.get(i).setDepreciatonRate(cate[i]);
                    data.get(i).setYear(year0 + i);
                }
                //Depreciation & Ledger value
                data.get(0).setAnnualDepreciation(0);
                data.get(0).setValueInLedgers(formatDecimals(principal));
                for (int i = 1; i < periods; i++) {
                    data.get(i).setAnnualDepreciation(formatDecimals(data.get(i-1).getValueInLedgers() * (data.get(i).getDepreciatonRate()/100)));
                    data.get(i).setValueInLedgers(formatDecimals(data.get(i-1).getValueInLedgers() - data.get(i).getAnnualDepreciation()));
                    data.get(i).setTaxPerYear(formatDecimals( (tax * data.get(i).getValueInLedgers())));
                }
                //Accumulated Depr
                for (int i = 0; i < periods; i++) {
                    data.get(i).setCumulativeDepreciation(formatDecimals(getAcc(i, data)));
                }
            }
            else {
                System.out.println("Mas periodos que macrs");
            }
        }
        else {                                                      //LINEAL
            System.out.println("Enters Lineal");
            // años
            for (int i = 0; i < periods; i++) {
                data.get(i).setYear(year0 + i);
            }
            //Depreciation & Ledger value
            data.get(0).setAnnualDepreciation(0);
            data.get(0).setValueInLedgers(formatDecimals(principal));
            for (int i = 1; i < periods; i++) {
                data.get(i).setAnnualDepreciation((formatDecimals(data.get(0).getValueInLedgers() - sv)/(svPeriod)));
                data.get(i).setValueInLedgers(formatDecimals(data.get(i-1).getValueInLedgers() - data.get(i).getAnnualDepreciation()));
                data.get(i).setTaxPerYear((formatDecimals(tax * data.get(i).getValueInLedgers())));
            }
            //Accumulated Depr
            for (int i = 0; i < periods; i++) {
                data.get(i).setCumulativeDepreciation(formatDecimals(getAcc(i, data)));
            }
        }
    }

    public static double getAcc(int index, ObservableList<DepreciationRow> data) {
        double accumulated = 0;
        if(index == 0) {
            return accumulated;
        }
        else {
            return data.get(index).getAnnualDepreciation() + getAcc((index - 1), data);
        }
    }

}
