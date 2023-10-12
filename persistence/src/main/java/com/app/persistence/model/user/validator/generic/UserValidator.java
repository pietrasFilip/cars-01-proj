package com.app.persistence.model.user.validator.generic;

import com.app.persistence.model.user.dto.exception.UserDtoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface UserValidator <T> {
    Logger logger = LogManager.getRootLogger();
    void validate(T t);

    static <T> T validateNull(T t, String logMessage) {
        if (t == null || t.toString().isEmpty()) {
            logger.error(logMessage);
            throw new UserDtoException("Is null or empty");
        }
        return t;
    }
}
