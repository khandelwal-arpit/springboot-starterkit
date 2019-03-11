package com.starterkit.springboot.brs.repository.bus;

import com.starterkit.springboot.brs.model.bus.Stop;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface StopRepository extends MongoRepository<Stop, String> {
    Stop findByCode(String code);
}
