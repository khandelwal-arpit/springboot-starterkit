package com.starterkit.springboot.brs.service;

import com.starterkit.springboot.brs.dto.model.user.UserDto;

/**
 * Created by Arpit Khandelwal.
 */
public interface UserService {
    /**
     * Register a new user
     *
     * @param userDto
     * @return
     */
    UserDto signup(UserDto userDto);

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    UserDto findUserByEmail(String email);

    /**
     * Update profile of the user
     *
     * @param userDto
     * @return
     */
    UserDto updateProfile(UserDto userDto);

    /**
     * Update password
     *
     * @param newPassword
     * @return
     */
    UserDto changePassword(UserDto userDto, String newPassword);
}
