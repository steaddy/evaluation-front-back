package com.example.moscow_price.servis;

import com.example.moscow_price.Entity.UserEntity;
import com.example.moscow_price.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registration(UserEntity userEntity){
        userRepository.save(userEntity);

        return "ok";
    }

    public String signIn(UserEntity userEntity){
        UserEntity userByName = userRepository.findByName(userEntity.getName());
        if (userByName == null){
            return "wrong name";
        } else if (userEntity.getPassword().equals(userByName.getPassword())){
            return "ok";
        } else {
            return "wrong password";
        }
    }
}
