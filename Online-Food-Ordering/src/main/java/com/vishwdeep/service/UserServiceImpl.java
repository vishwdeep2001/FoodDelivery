package com.vishwdeep.service;

import com.vishwdeep.config.JwtProvider;
import com.vishwdeep.model.User;
import com.vishwdeep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
       String email= jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        System.err.println(user);
        System.err.println(user);
        System.err.println(user);
        if(user ==  null) throw new Exception("User not found");
        else{
            return user;
        }

    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user ==  null) throw new Exception("User not found");
        else{
            return user;
        }

    }
}
