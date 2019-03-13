package com.starterkit.springboot.brs.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Arpit Khandelwal.
 */
@Data
@Accessors(chain = true)
public class BusFormCommand {
    @NotBlank
    @Size(min = 4, max = 8)
    private String code;

    @Min(value = 10, message = "Cannot enroll a bus with capacity smaller than 10")
    private int capacity;

    @NotBlank
    private String make;
}
