package com.globallogic.store.assembler.user;

import com.globallogic.store.domain.user.User;
import com.globallogic.store.dto.user.UserDTO;
import com.globallogic.store.rest.UserRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class UserAssembler extends ResourceAssemblerSupport<User, UserDTO> {

    public UserAssembler() {
        super(UserRestController.class, UserDTO.class);
    }

    @Override
    public UserDTO toResource(User user) {
        UserDTO dto = createResourceWithId(user.getId(), user);
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        return dto;
    }

    public User toResource(UserDTO dto) {
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
}
