package models;

/**
 * Created by gluqu on 23/04/2017.
 */
public class MatrixRow {

    private int rowNum;
    private int consideration;
    private int rating;
    private int weighting;
    private int weighted;

    public MatrixRow(int rowNum, int consideration, int rating, int weighting, int weighted) {
        this.rowNum = rowNum;
        this.consideration = consideration;
        this.rating = rating;
        this.weighting = weighting;
        this.weighted = weighted;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getConsideration() {
        return consideration;
    }

    public void setConsideration(int consideration) {
        this.consideration = consideration;
    }

    public int getRatind() {
        return rating;
    }

    public void setRatind(int ratind) {
        this.rating = ratind;
    }

    public int getWeighting() {
        return weighting;
    }

    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }

    public int getWeighted() {
        return weighted;
    }
    public String getWeightedString() {
        return weighted + "";
    }

    public void setWeighted(int weighted) {
        this.weighted = weighted;
    }
}
