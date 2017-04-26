package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Martín Ruíz on 4/22/2017.
 */
public class NPVRow extends RecursiveTreeObject<NPVRow> {
    SimpleIntegerProperty period;
    SimpleDoubleProperty outflow;
    SimpleDoubleProperty inflow;
    SimpleDoubleProperty netCashFlow;
    SimpleDoubleProperty netPresentValue;
    SimpleDoubleProperty cumulativeNPV;

    public NPVRow(int period, double outflow, double inflow, double netCashFlow, double netPresentValie, double cumulativeNVP){
        this.period = new SimpleIntegerProperty(period);
        this.outflow = new SimpleDoubleProperty(outflow);
        this.inflow = new SimpleDoubleProperty(inflow);
        this.netCashFlow = new SimpleDoubleProperty(netCashFlow);
        this.netPresentValue = new SimpleDoubleProperty(netPresentValie);
        this.cumulativeNPV = new SimpleDoubleProperty(cumulativeNVP);
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

    public double getNetPresentValue() {
        return netPresentValue.get();
    }

    public SimpleDoubleProperty netPresentValueProperty() {
        return netPresentValue;
    }

    public void setNetPresentValue(double netPresentValue) {
        this.netPresentValue.set(netPresentValue);
    }

    public double getCumulativeNPV() {
        return cumulativeNPV.get();
    }

    public SimpleDoubleProperty cumulativeNPVProperty() {
        return cumulativeNPV;
    }

    public void setCumulativeNPV(double cumulativeNPV) {
        this.cumulativeNPV.set(cumulativeNPV);
    }
}
