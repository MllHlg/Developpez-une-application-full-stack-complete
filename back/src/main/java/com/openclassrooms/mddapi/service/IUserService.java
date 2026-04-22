package com.openclassrooms.mddapi.service;

import org.apache.coyote.BadRequestException;

import com.openclassrooms.mddapi.dto.UserCreateDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.model.User;

public interface IUserService {
    void createUser(UserCreateDTO dto);
    User findByUsername(String username);
    User findById(Long id);
    void abonnement(Long theme_id, Long user_id) throws BadRequestException ;
    void desabonnement(Long theme_id, Long user_id) throws BadRequestException ;
    String update(Long id, UserUpdateDTO dto);
}
