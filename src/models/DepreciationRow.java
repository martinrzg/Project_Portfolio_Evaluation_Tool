package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Martín Ruíz on 4/27/2017.
 */
public class DepreciationRow extends RecursiveTreeObject<DepreciationRow> {
    SimpleIntegerProperty year;
    SimpleDoubleProperty depreciatonRate;
    SimpleDoubleProperty annualDepreciation;
    SimpleDoubleProperty cumulativeDepreciation;
    SimpleDoubleProperty valueInLedgers;
    SimpleDoubleProperty taxPerYear;

    public DepreciationRow (int year, double depreciatioRate, double annualDepreciation, double cumulativeDepreciation,double valueInLedgers, double taxPerYear){
        this.year = new SimpleIntegerProperty(year);
        this.depreciatonRate = new SimpleDoubleProperty(depreciatioRate);
        this.annualDepreciation = new SimpleDoubleProperty(annualDepreciation);
        this.cumulativeDepreciation = new SimpleDoubleProperty(cumulativeDepreciation);
        this.cumulativeDepreciation = new SimpleDoubleProperty(cumulativeDepreciation);
        this.valueInLedgers = new SimpleDoubleProperty(valueInLedgers);
        this.taxPerYear = new SimpleDoubleProperty(taxPerYear);
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public double getDepreciatonRate() {
        return depreciatonRate.get();
    }

    public SimpleDoubleProperty depreciatonRateProperty() {
        return depreciatonRate;
    }

    public void setDepreciatonRate(double depreciatonRate) {
        this.depreciatonRate.set(depreciatonRate);
    }

    public double getAnnualDepreciation() {
        return annualDepreciation.get();
    }

    public SimpleDoubleProperty annualDepreciationProperty() {
        return annualDepreciation;
    }

    public void setAnnualDepreciation(double annualDepreciation) {
        this.annualDepreciation.set(annualDepreciation);
    }

    public double getCumulativeDepreciation() {
        return cumulativeDepreciation.get();
    }

    public SimpleDoubleProperty cumulativeDepreciationProperty() {
        return cumulativeDepreciation;
    }

    public void setCumulativeDepreciation(double cumulativeDepreciation) {
        this.cumulativeDepreciation.set(cumulativeDepreciation);
    }

    public double getValueInLedgers() {
        return valueInLedgers.get();
    }

    public SimpleDoubleProperty valueInLedgersProperty() {
        return valueInLedgers;
    }

    public void setValueInLedgers(double valueInLedgers) {
        this.valueInLedgers.set(valueInLedgers);
    }

    public double getTaxPerYear() {
        return taxPerYear.get();
    }

    public SimpleDoubleProperty taxPerYearProperty() {
        return taxPerYear;
    }

    public void setTaxPerYear(double taxPerYear) {
        this.taxPerYear.set(taxPerYear);
    }
}
