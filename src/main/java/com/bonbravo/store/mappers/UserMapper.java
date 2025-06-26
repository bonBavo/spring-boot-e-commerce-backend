package com.bonbravo.store.mappers;

import com.bonbravo.store.dtos.RegisterUserRequest;
import com.bonbravo.store.dtos.UserDto;
import com.bonbravo.store.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);


}
