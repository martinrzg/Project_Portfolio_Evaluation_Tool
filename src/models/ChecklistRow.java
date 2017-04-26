package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Martín Ruíz on 4/25/2017.
 */
public class ChecklistRow extends RecursiveTreeObject<ChecklistRow>{
    SimpleStringProperty topic;
    SimpleStringProperty question;
    SimpleStringProperty answer;

    public ChecklistRow(String topic, String question, String answer){
        this.topic    = new SimpleStringProperty(topic);
        this.question = new SimpleStringProperty(question);
        this.answer   = new SimpleStringProperty(answer);
    }

    public String getAnswer() {
        return answer.get();
    }

    public SimpleStringProperty answerProperty() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer.set(answer);
    }



    public String getTopic() {
        return topic.get();
    }

    public SimpleStringProperty topicProperty() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public String getQuestion() {
        return question.get();
    }

    public SimpleStringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }
}
