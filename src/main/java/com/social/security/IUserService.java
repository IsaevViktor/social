package com.social.security;

import com.social.security.model.User;

public interface IUserService {
    User findUserByEmail(String email);

    void saveSimpleUser(User user);
}
