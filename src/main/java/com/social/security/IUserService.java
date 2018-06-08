package com.social.security;

import com.social.model.security.User;

public interface IUserService {
    User findUserByEmail(String email);

    void saveSimpleUser(User user);
}
