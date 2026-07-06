package com.example.hxce.api.data;

import java.util.List;

public record QuizResult(
        String knowledge,
        List<QuestionItem> questions
) {
}
