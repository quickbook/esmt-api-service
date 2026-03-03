package com.esmt.mapper;

import org.springframework.stereotype.Component;

import com.esmt.model.User;
import com.esmt.response.dto.UserResponse;

@Component
public class UserMapper {
	
	public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .gmail(user.getGmail())
                .contactNumber(user.getContactNumber())
                .address(user.getAddress())
                .city(user.getCity())
                .zipCode(user.getZipCode())
                .countryName(user.getCountry() != null ? user.getCountry().getName() : null) 
                .stateName(user.getStateName() != null ? user.getStateName() : null) 
                .roleName(user.getRole() != null ? user.getRole().getName() : null)
                .build();
    }

}
