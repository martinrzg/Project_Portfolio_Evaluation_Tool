package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Martín Ruíz on 4/20/2017.
 */
public class PaybackRow extends RecursiveTreeObject<PaybackRow> {
    SimpleIntegerProperty period;
    SimpleDoubleProperty outflow;
    SimpleDoubleProperty inflow;
    SimpleDoubleProperty netCashFlow;
    SimpleDoubleProperty cumulativeCashFlow;

    public PaybackRow(int period, double outflow, double inflow, double netCashFlow, double cumulativeCashFlow){
        this.period  = new SimpleIntegerProperty(period);
        this.outflow = new SimpleDoubleProperty(outflow);
        this.inflow  = new SimpleDoubleProperty(inflow);
        this.netCashFlow = new SimpleDoubleProperty(netCashFlow);
        this.cumulativeCashFlow = new SimpleDoubleProperty(cumulativeCashFlow);
    }

    public int getPeriod() {
        return period.get();
    }

    public SimpleIntegerProperty periodProperty() {
        return period;
    }

    public void setPeriod(int period) {
        this.period.set(period);
    }

    public double getOutflow() {
        return outflow.get();
    }

    public SimpleDoubleProperty outflowProperty() {
        return outflow;
    }

    public void setOutflow(double outflow) {
        this.outflow.set(outflow);
    }

    public double getInflow() {
        return inflow.get();
    }

    public SimpleDoubleProperty inflowProperty() {
        return inflow;
    }

    public void setInflow(double inflow) {
        this.inflow.set(inflow);
    }

    public double getNetCashFlow() {
        return netCashFlow.get();
    }

    public SimpleDoubleProperty netCashFlowProperty() {
        return netCashFlow;
    }

    public void setNetCashFlow(double netCashFlow) {
        this.netCashFlow.set(netCashFlow);
    }

    public double getCumulativeCashFlow() {
        return cumulativeCashFlow.get();
    }

    public SimpleDoubleProperty cumulativeCashFlowProperty() {
        return cumulativeCashFlow;
    }

    public void setCumulativeCashFlow(double cumulativeCashFlow) {
        this.cumulativeCashFlow.set(cumulativeCashFlow);
    }
}

