package rating_service.interfaces;

import rating_service.domain.Question;

public interface ISurvey {
    void createSurvey(String id, String name);
    void addQuestionToSurvey(String surveyId, Question question);
    void answerQuestion(String surveyId, String questionId, String userId, int responseCode);
    int getAverageRatingOfQuestion(String surveyId, String questionId);
}
