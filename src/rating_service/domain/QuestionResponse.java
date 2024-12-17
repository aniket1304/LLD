package rating_service.domain;

import java.util.Arrays;

public enum QuestionResponse {
    POOR(0),
    GOOD(1),
    VERY_GOOD(2);

    final Integer responseCode;

    QuestionResponse(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getResponseCode() {
        return this.responseCode;
    }

    public static QuestionResponse getQuestionResponseByCode(Integer responseCode) {
        return Arrays.stream(QuestionResponse.values())
                .filter(questionResponse -> questionResponse.responseCode.equals(responseCode))
                .findFirst()
                .orElse(POOR);
    }
}
