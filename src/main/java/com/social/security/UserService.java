package com.social.security;

import com.social.model.security.Role;
import com.social.model.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static java.util.Arrays.asList;

@Service("userService")
public class UserService implements IUserService {
    private IUserDao userDao;
    private IRoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserDao userDao, IRoleDao roleDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void saveSimpleUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleDao.findByRole("USER");
        user.setRoles(new HashSet<>(asList(userRole)));
        userDao.save(user);
    }
}
