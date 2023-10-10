package com.spring.restapi.restapi.converter;

import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class UserConverter extends CommonConverter<User,UserDto>{
    @Override
    public User convertToEntity(UserDto dto){
        return this.copyConvertToEntity(dto, new User());
    }

    @Override
    public UserDto convertToDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setUserId(entity.getUserId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setMobile(entity.getMobile());
        dto.setGender(entity.getGender());
        dto.setAge(entity.getAge());
        dto.setNationality(entity.getNationality());
        return dto;
    }

    @Override
    public User copyConvertToEntity(UserDto dto, User entity) {
        if (dto == null || entity == null){
            return null;
        }
        entity.setUserId(dto.getUserId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMobile(dto.getMobile());
        entity.setGender(dto.getGender());
        entity.setAge(dto.getAge());
        entity.setNationality(dto.getNationality());
        return entity;
    }

    @Override
    public List<UserDto> convertToDtoList(List<User> entities) {
        return super.convertToDtoList(entities);
    }

    @Override
    public List<User> convertToEntityList(List<UserDto> dtoList) {
        return super.convertToEntityList(dtoList);
    }
}
