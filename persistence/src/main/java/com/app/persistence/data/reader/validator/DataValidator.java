package com.app.persistence.data.reader.validator;

import com.app.persistence.data.reader.model.ComponentData;
import com.app.persistence.data.reader.validator.exception.DataValidatorException;

import java.math.BigDecimal;
import java.util.Set;

public interface DataValidator<T> {
    T validate(T t);

    static String validateMatchesRegex(String regex, String model) {
        if (model == null || model.isEmpty()) {
            throw new DataValidatorException("Model is null or empty");
        }

        if (!model.matches(regex)) {
            throw new DataValidatorException("Wrong input format - only uppercase!");
        }
        return model;
    }

    static BigDecimal validatePositiveDecimal(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DataValidatorException("Price value is zero or less");
        }
        return value;
    }

    static int validatePositiveInt(int value) {
        if (value < 0) {
            throw new DataValidatorException("Mileage less than zero");
        }
        return value;
    }

    static Set<ComponentData> validateItemsMatchesRegex(String regex, Set<ComponentData> items) {
        if (items != null) {
            items
                    .forEach(component -> {
                        if (!component.getName().matches(regex)) {
                            throw new DataValidatorException("Wrong component format");
                        }
                    });
        }

        return items;
    }

    static <T> T validateNull(T t) {
        if (t == null) {
            throw new DataValidatorException("Is null");
        }
        return t;
    }
}
