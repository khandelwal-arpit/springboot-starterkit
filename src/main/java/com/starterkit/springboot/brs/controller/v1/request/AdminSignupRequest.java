package com.starterkit.springboot.brs.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminSignupRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String email;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String password;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String firstName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String lastName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String agencyName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String agencyDetails;

    private String mobileNumber;
}
