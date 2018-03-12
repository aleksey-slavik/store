package com.globallogic.store.converter.user;

import com.globallogic.store.converter.Convertible;
import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.user.UserDTO;

public class UserConverter implements Convertible<User, UserDTO> {

    @Override
    public User toOrigin(UserDTO dto) {
        User user = new User();
        user.setId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setEnabled(dto.isEnabled());
        return user;
    }

    @Override
    public UserDTO toResource(User origin) {
        UserDTO dto = new UserDTO();
        dto.setUserId(origin.getId());
        dto.setUsername(origin.getUsername());
        dto.setPassword(origin.getPassword());
        dto.setFirstName(origin.getFirstName());
        dto.setLastName(origin.getLastName());
        dto.setEmail(origin.getEmail());
        dto.setEnabled(origin.isEnabled());
        return dto;
    }
}
