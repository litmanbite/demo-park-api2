package com.mballem.demo_park_api.web.dto.mapper;

import com.mballem.demo_park_api.entity.User;
import com.mballem.demo_park_api.web.dto.UserCreaterDTO;
import com.mballem.demo_park_api.web.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;


    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toUser(UserCreaterDTO userCreaterDTO) {
        return modelMapper.map(userCreaterDTO, User.class);
    }

    public UserResponseDTO toDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
