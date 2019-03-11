package com.starterkit.springboot.brs.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddTripRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String sourceStop;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String destinationStop;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String busCode;

    @NotNull
    private int tripDuration;

    @NotNull
    private int tripFare;
}
