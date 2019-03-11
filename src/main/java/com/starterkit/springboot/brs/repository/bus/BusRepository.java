package com.starterkit.springboot.brs.repository.bus;

import com.starterkit.springboot.brs.model.bus.Agency;
import com.starterkit.springboot.brs.model.bus.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface BusRepository extends MongoRepository<Bus, String> {
    Bus findByCode(String busCode);

    Bus findByCodeAndAgency(String busCode, Agency agency);
}
