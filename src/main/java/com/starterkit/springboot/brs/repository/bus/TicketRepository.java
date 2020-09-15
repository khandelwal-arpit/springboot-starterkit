package com.starterkit.springboot.brs.repository.bus;

import com.starterkit.springboot.brs.model.bus.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface TicketRepository extends MongoRepository<Ticket, String> {
}
