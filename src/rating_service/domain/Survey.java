package rating_service.domain;

import java.util.List;
import java.util.Map;

public class Survey {
    String id;
    String surveyName;
    Map<String, Question> questions;
    int totalResponse;

    public void setId(String id) {
        this.id = id;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public void setQuestions(Map<String, Question> questions) {
        this.questions = questions;
    }

    public String getId() {
        return this.id;
    }

    public String getSurveyName() {
        return this.surveyName;
    }

    public Map<String, Question> getQuestions() {
        return this.questions;
    }

    public void setTotalResponse(int totalResponse) {
        this.totalResponse = totalResponse;
    }

    public int getTotalResponse() {
        return this.totalResponse;
    }
}
