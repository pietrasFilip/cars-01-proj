package com.app.persistence.data.reader.validator;

import com.app.persistence.data.reader.validator.exception.DataValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.persistence.data.reader.validator.DataValidator.validateMatchesRegex;

class ValidateMatchesRegexTest {

    @Test
    @DisplayName("When model is null or empty")
    void test1() {
        var regex = "[A-Z]";
        var model = "";
        Assertions.assertThatThrownBy(() -> validateMatchesRegex(regex ,model))
                .isInstanceOf(DataValidatorException.class)
                .hasMessage("Model is null or empty");
    }

    @Test
    @DisplayName("When model does not match regex")
    void test2() {
        var regex = "[A-Z]";
        var model = "Audi";
        Assertions.assertThatThrownBy(() -> validateMatchesRegex(regex ,model))
                .isInstanceOf(DataValidatorException.class)
                .hasMessage("Wrong input format - only uppercase!");
    }
}
