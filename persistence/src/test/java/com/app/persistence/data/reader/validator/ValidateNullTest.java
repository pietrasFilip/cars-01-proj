package com.app.persistence.data.reader.validator;

import com.app.persistence.data.reader.validator.exception.DataValidatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.persistence.data.reader.validator.DataValidator.validateNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateNullTest {
    @Test
    @DisplayName("When is null")
    void test1() {
        assertThatThrownBy(() -> validateNull(null))
                .hasMessage("Is null")
                .isInstanceOf(DataValidatorException.class);
    }
}
