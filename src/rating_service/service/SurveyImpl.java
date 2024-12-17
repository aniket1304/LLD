package rating_service.service;

import rating_service.domain.Question;
import rating_service.domain.QuestionResponse;
import rating_service.domain.Survey;
import rating_service.interfaces.ISurvey;

import java.util.HashMap;
import java.util.Map;

public class SurveyImpl implements ISurvey {

    Map<String, Survey> surveyMap = new HashMap<>();

    @Override
    public void createSurvey(String id, String name) {
        Survey survey = new Survey();
        survey.setId(id);
        survey.setSurveyName(name);
        survey.setQuestions(new HashMap<>());
        surveyMap.put(id, survey);
    }

    @Override
    public void addQuestionToSurvey(String surveyId, Question question) {
        if (surveyMap.containsKey(surveyId))
            surveyMap.get(surveyId).getQuestions().put(question.getId(), question);
    }

    @Override
    public void answerQuestion(String surveyId, String questionId, String userId, int responseCode) {
        if (!surveyMap.containsKey(surveyId))
            return;
        if (!surveyMap.get(surveyId).getQuestions().containsKey(questionId))
            return;
        Question question = surveyMap.get(surveyId).getQuestions().get(questionId);
        int currAvgResponse = 0;
        if (!question.getUserResponse().isEmpty())
            currAvgResponse = Math.divideExact(question.getTotalResponse(), question.getUserResponse().size());
        if (question.getUserResponse().containsKey(userId))
            question.setTotalResponse(question.getTotalResponse() - question.getUserResponse().get(userId).getResponseCode());
        question.setTotalResponse(question.getTotalResponse() + responseCode);
        question.getUserResponse().put(userId, QuestionResponse.getQuestionResponseByCode(responseCode));
        int newAvgResponse = Math.divideExact(question.getTotalResponse(), question.getUserResponse().size());
        surveyMap.get(surveyId).setTotalResponse(surveyMap.get(surveyId).getTotalResponse()-currAvgResponse+newAvgResponse);
    }

    @Override
    public int getAverageRatingOfQuestion(String surveyId, String questionId) {
        if (!surveyMap.containsKey(surveyId))
            throw new RuntimeException("No survey found");
        if (!surveyMap.get(surveyId).getQuestions().containsKey(questionId))
            throw new RuntimeException("No question found");
        return Math.divideExact(surveyMap.get(surveyId).getQuestions().get(questionId).getTotalResponse(),
                surveyMap.get(surveyId).getQuestions().get(questionId).getUserResponse().size());
    }
}
