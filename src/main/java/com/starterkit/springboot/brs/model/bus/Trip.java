package com.starterkit.springboot.brs.model.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "trip")
public class Trip {
    @Id
    private String id;

    private int fare;

    private int journeyTime;

    @DBRef
    private Stop sourceStop;

    @DBRef
    private Stop destStop;

    @DBRef
    private Bus bus;

    @DBRef
    private Agency agency;

}
