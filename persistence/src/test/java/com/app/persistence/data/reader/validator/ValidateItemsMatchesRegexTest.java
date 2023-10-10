package com.app.persistence.data.reader.validator;

import com.app.persistence.data.reader.model.ComponentData;
import com.app.persistence.data.reader.validator.exception.DataValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.app.persistence.data.reader.validator.DataValidator.validateItemsMatchesRegex;

class ValidateItemsMatchesRegexTest {
    @Test
    @DisplayName("When component does not match regex")
    void test1() {
        var regex = "[a-z]";
        var components = Set.of(
                ComponentData.of(1L, "wheel"),
                ComponentData.of(2L, "SPOILER")
        );
        Assertions.assertThatThrownBy(() -> validateItemsMatchesRegex(regex ,components))
                .isInstanceOf(DataValidatorException.class)
                .hasMessage("Wrong component format");
    }
}
