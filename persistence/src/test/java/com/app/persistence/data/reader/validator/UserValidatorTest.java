package com.app.persistence.data.reader.validator;

import com.app.persistence.model.user.dto.exception.UserDtoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.persistence.model.user.validator.generic.UserValidator.validateNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserValidatorTest {

    @Test
    @DisplayName("When is null")
    void test1() {
        assertThatThrownBy(() -> validateNull(null, ""))
                .hasMessage("Is null or empty")
                .isInstanceOf(UserDtoException.class);
    }

    @Test
    @DisplayName("When is empty")
    void test2() {
        assertThatThrownBy(() -> validateNull("", ""))
                .hasMessage("Is null or empty")
                .isInstanceOf(UserDtoException.class);
    }
}
