package com.ajay.trainpassenger;

import com.ajay.trainpassenger.model.Seat;
import com.ajay.trainpassenger.model.SeatSection;
import com.ajay.trainpassenger.model.Ticket;
import com.ajay.trainpassenger.model.User;
import com.ajay.trainpassenger.service.TicketService;
import com.ajay.trainpassenger.service.serviceimpl.TicketServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceImplTest {
    @Test
    void testPurchaseTicket() {
        TicketService ticketService = new TicketServiceImpl();

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        Seat seat = new Seat(SeatSection.A, 1);
        Ticket ticket = ticketService.purchaseTicket("London", "France", user);
        ticket.setSeat(seat);

        assertNotNull(ticket);
        assertEquals("London", ticket.getFrom());
        assertEquals("France", ticket.getTo());
        assertEquals(user, ticket.getUser());
        assertEquals(20.0, ticket.getPricePaid());
        assertNotNull(ticket.getSeat());
    }

    @Test
    void testGetTicketDetails() {
        TicketService ticketService = new TicketServiceImpl();

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        Seat seat = new Seat(SeatSection.A, 2);

        ticketService.purchaseTicket("London", "France", user);

        Ticket ticket = ticketService.getTicketDetails("john.doe@example.com");

        assertNotNull(ticket);
        assertEquals("London", ticket.getFrom());
        assertEquals("France", ticket.getTo());
        assertEquals(user, ticket.getUser());
        assertEquals(20.0, ticket.getPricePaid());
        assertNotNull(ticket.getSeat());
    }

    @Test
    void testGetUsersBySection() {
        TicketService ticketService = new TicketServiceImpl();

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");

        ticketService.purchaseTicket("London", "France", user1);
        ticketService.purchaseTicket("London", "France", user2);

        List<String> usersInSection = ticketService.getUsersBySection("A");

        assertNotNull(usersInSection);
        assertEquals(1, usersInSection.size());
        assertTrue(usersInSection.contains("john.doe@example.com"));
        assertFalse(usersInSection.contains("jane.doe@example.com"));
    }

    @Test
    void testRemoveUser() {
        TicketService ticketService = new TicketServiceImpl();

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        ticketService.purchaseTicket("London", "France", user);

        assertNotNull(ticketService.getTicketDetails("john.doe@example.com"));

        ticketService.removeUser("john.doe@example.com");

        assertNull(ticketService.getTicketDetails("john.doe@example.com"));
    }

    @Test
    void testModifyUserSeat() {
        TicketService ticketService = new TicketServiceImpl();

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        Ticket ticket = ticketService.purchaseTicket("London", "France", user);

        assertEquals(SeatSection.A, ticket.getSeat().getSection());

        ticketService.modifyUserSeat("john.doe@example.com", "B");

        assertEquals(SeatSection.B, ticket.getSeat().getSection());
    }
}
