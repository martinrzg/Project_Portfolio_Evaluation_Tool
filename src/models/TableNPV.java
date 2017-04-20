package models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by gluqu on 18/04/2017.
 */
public class TableNPV {

    private SimpleIntegerProperty period;
    private SimpleFloatProperty outflow;
    private SimpleFloatProperty inflow;
    private SimpleFloatProperty ncf;
    private SimpleFloatProperty ccf;

    public TableNPV(int period, float outflow, float inflow, float ncf, float ccf) {
        this.period = new SimpleIntegerProperty(period);
        this.outflow = new SimpleFloatProperty(outflow);
        this.inflow = new SimpleFloatProperty(inflow);
        this.ncf = new SimpleFloatProperty(ncf);
        this.ccf = new SimpleFloatProperty(ccf);
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

    public float getOutflow() {
        return outflow.get();
    }

    public SimpleFloatProperty outflowProperty() {
        return outflow;
    }

    public void setOutflow(float outflow) {
        this.outflow.set(outflow);
    }

    public float getInflow() {
        return inflow.get();
    }

    public SimpleFloatProperty inflowProperty() {
        return inflow;
    }

    public void setInflow(float inflow) {
        this.inflow.set(inflow);
    }

    public float getNcf() {
        return ncf.get();
    }

    public SimpleFloatProperty ncfProperty() {
        return ncf;
    }

    public void setNcf(float ncf) {
        this.ncf.set(ncf);
    }

    public float getCcf() {
        return ccf.get();
    }

    public SimpleFloatProperty ccfProperty() {
        return ccf;
    }

    public void setCcf(float ccf) {
        this.ccf.set(ccf);
    }
}
