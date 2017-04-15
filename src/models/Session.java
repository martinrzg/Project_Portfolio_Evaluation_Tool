package models;

/**
 * Created by Martín Ruíz on 4/12/2017.
 */
public class Session {

    private final static Session instance = new Session();

    private String projectID;
    private String evaluatorName;

    public static Session getInstance() {
        return instance;
    }


    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getEvaluatorName() {
        return evaluatorName;
    }

    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }
}
