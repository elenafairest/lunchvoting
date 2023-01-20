package com.github.elenafairest.lunchvoting.util;

import com.github.elenafairest.lunchvoting.config.SecurityConfiguration;
import com.github.elenafairest.lunchvoting.model.Role;
import com.github.elenafairest.lunchvoting.model.User;
import com.github.elenafairest.lunchvoting.to.UserTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(SecurityConfiguration.PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}