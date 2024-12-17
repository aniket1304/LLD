package rating_service;

import rating_service.domain.Question;
import rating_service.interfaces.ISurvey;
import rating_service.service.SurveyImpl;

public class RatingDemo {

    ISurvey iSurvey;

    public RatingDemo() {
        this.iSurvey = new SurveyImpl();
    }

    public static void main(String[] args) {
        RatingDemo ratingDemo = new RatingDemo();
        ratingDemo.iSurvey.createSurvey("1", "survey1");
        Question question = new Question();
        question.setId("q1");
        ratingDemo.iSurvey.addQuestionToSurvey("1", question);
        ratingDemo.iSurvey.answerQuestion("1", "q1", "u1", 1);
        ratingDemo.iSurvey.answerQuestion("1", "q1", "u2", 0);
        ratingDemo.iSurvey.answerQuestion("1", "q1", "u1", 2);
        ratingDemo.iSurvey.answerQuestion("1", "q1", "u1", 2);
        ratingDemo.iSurvey.answerQuestion("1", "q1", "u1", 2);
        int qRating = ratingDemo.iSurvey.getAverageRatingOfQuestion("1", "q1");
        System.out.println(qRating);
    }
}
