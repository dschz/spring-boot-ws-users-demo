package com.dsc.frm.springboot.mapper;

import com.dsc.frm.springboot.dto.UserDto;
import com.dsc.frm.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author DSchneider
 */
@Mapper
public interface UserStructMapper {

    UserStructMapper MAPPER = Mappers.getMapper(UserStructMapper.class);

    UserDto map2UserDto(User user);

    User map2User(UserDto dto);

}
