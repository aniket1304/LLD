package rating_service.domain;

import java.util.HashMap;
import java.util.Map;


public class Question {
    String id;
    String surveyId;
    String description;
    Map<String, QuestionResponse> userResponse;
    int totalResponse;
    /*
    public Question(String id, String surveyId, String description, Map<String, QuestionResponse> userResponse, int totalResponse) {
        this.id = id;
        this.surveyId = surveyId;
        this.description = description;
        this.userResponse = userResponse;
        this.totalResponse = totalResponse;
    }
    
     */

    public Question() {
        this.userResponse = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, QuestionResponse> getUserResponse() {
        return this.userResponse;
    }

    public void setTotalResponse(int totalResponse) {
        this.totalResponse = totalResponse;
    }

    public int getTotalResponse() {
        return this.totalResponse;
    }
}
