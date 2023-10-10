package com.app.persistence.data.reader.validator;


import com.app.persistence.data.reader.validator.exception.DataValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.persistence.data.reader.validator.DataValidator.validatePositiveInt;

class ValidatePositiveIntTest {
    @Test
    @DisplayName("When mileage is less than zero")
    void test1() {
        var mileage = -100;
        Assertions.assertThatThrownBy(() -> validatePositiveInt(mileage))
                .isInstanceOf(DataValidatorException.class)
                .hasMessage("Mileage less than zero");
    }
}
