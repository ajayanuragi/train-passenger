package com.ajay.trainpassenger;

import com.ajay.trainpassenger.model.Seat;
import com.ajay.trainpassenger.model.SeatSection;
import com.ajay.trainpassenger.model.Ticket;
import com.ajay.trainpassenger.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketTest {
    @Test
    void testTicketCreation() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        Seat seat = new Seat(SeatSection.A,1);
        Ticket ticket = new Ticket();
        ticket.setFrom("London");
        ticket.setTo("France");
        ticket.setUser(user);
        ticket.setPricePaid(20.0);
        ticket.setSeat(seat);

        assertEquals("London", ticket.getFrom());
        assertEquals("France", ticket.getTo());
        assertEquals(user, ticket.getUser());
        assertEquals(20.0, ticket.getPricePaid());
        assertEquals(SeatSection.A, ticket.getSeat().getSection());
    }
}
