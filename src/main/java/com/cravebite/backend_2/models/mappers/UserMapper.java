package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;

import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.models.response.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(User user);

}
