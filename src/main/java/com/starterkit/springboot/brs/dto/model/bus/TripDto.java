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
public class TripDto {

    private String id;

    private int fare;

    private int journeyTime;

    private String sourceStopCode;

    private String sourceStopName;

    private String destinationStopCode;

    private String destinationStopName;

    private String busCode;

    private String agencyCode;
}
