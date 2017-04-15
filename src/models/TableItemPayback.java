package models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Martín Ruíz on 4/14/2017.
 */
public class TableItemPayback {
    private SimpleIntegerProperty period;
    private SimpleFloatProperty outflow;
    private SimpleFloatProperty inflow;
    private SimpleFloatProperty cumulative;

    public TableItemPayback(int period, float outflow, float inflow, float cumulative) {
        this.period      = new SimpleIntegerProperty(period);
        this.outflow     = new SimpleFloatProperty(outflow);
        this.inflow      = new SimpleFloatProperty(inflow);
        this.cumulative = new SimpleFloatProperty(cumulative);
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

    public float getCumulative() {
        return cumulative.get();
    }

    public SimpleFloatProperty cumulativeProperty() {
        return cumulative;
    }

    public void setCumulative(float cumulative) {
        this.cumulative.set(cumulative);
    }
}
