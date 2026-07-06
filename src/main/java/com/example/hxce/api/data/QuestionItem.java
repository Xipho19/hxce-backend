package com.example.hxce.api.data;

import java.util.List;

public record QuestionItem(
        String question,
        List<Option> options,
        String answer,
        String explanation
) {
}
