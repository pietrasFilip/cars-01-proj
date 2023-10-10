package com.app.persistence.data.reader.validator;

import com.app.persistence.data.reader.validator.exception.DataValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.app.persistence.data.reader.validator.DataValidator.validatePositiveDecimal;

class ValidatePositiveDecimalTest {
    @Test
    @DisplayName("When price is zero")
    void test1() {
        var price = BigDecimal.valueOf(0);
        Assertions.assertThatThrownBy(() -> validatePositiveDecimal(price))
                .isInstanceOf(DataValidatorException.class)
                .hasMessage("Price value is zero or less");
    }

    @Test
    @DisplayName("When price is less than zero")
    void test2() {
        var price = BigDecimal.valueOf(-20);
        Assertions.assertThatThrownBy(() -> validatePositiveDecimal(price))
                .isInstanceOf(DataValidatorException.class)
                .hasMessage("Price value is zero or less");
    }
}
