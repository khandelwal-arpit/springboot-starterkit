package com.starterkit.springboot.brs.dto.model.bus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDto {
    private String id;

    private String busCode;

    private int seatNumber;

    private boolean cancellable;

    private String journeyDate;

    private String sourceStop;

    private String destinationStop;

    private String passengerName;

    private String passengerMobileNumber;
}
