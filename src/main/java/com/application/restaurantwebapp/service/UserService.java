package com.application.restaurantwebapp.service;

import com.application.restaurantwebapp.entity.Authorities;
import com.application.restaurantwebapp.entity.DbUsers;
import com.application.restaurantwebapp.entity.User;
import com.application.restaurantwebapp.repository.AuthoritiesRepository;
import com.application.restaurantwebapp.repository.DbUsersRepository;
import com.application.restaurantwebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public static final String ROLE_USER = "ROLE_USER";
    @Autowired
    UserRepository userRepository;
    @Autowired
    DbUsersRepository dbUsersRepository;
    @Autowired
    AuthoritiesRepository authoritiesRepository;

    public User createUser(String name, String password, String address) {
        User user = User.builder().name(name).password(password).address(address).build();
        user = userRepository.save(user);
        DbUsers dbUsers = new DbUsers(user.getUser_id().toString(), user.getPassword(), Boolean.TRUE);
        Authorities authorities= new Authorities(user.getUser_id().toString(), ROLE_USER);
        dbUsersRepository.save(dbUsers);
        authoritiesRepository.save(authorities);
        return user;
    }

    public User updateUser(Long id, String name, String password, String address) {
        User user = userRepository.findById(id).orElseThrow();
        if (name!=null) {
            user.setName(name);
        }
        if (password!=null) {
            user.setPassword(password);
        }
        if (address!=null) {
            user.setAddress(address);
        }
        return userRepository.saveAndFlush(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User getUser(Long id){
        return userRepository.getById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
