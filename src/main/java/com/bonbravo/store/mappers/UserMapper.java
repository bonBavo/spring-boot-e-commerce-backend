package com.bonbravo.store.mappers;

import com.bonbravo.store.dto.RegisterUserRequest;
import com.bonbravo.store.dto.UpdateUserRequest;
import com.bonbravo.store.dto.UserDto;
import com.bonbravo.store.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);

}
