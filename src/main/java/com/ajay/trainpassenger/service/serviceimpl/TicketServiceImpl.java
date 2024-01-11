package com.ajay.trainpassenger.service.serviceimpl;

import com.ajay.trainpassenger.model.Seat;
import com.ajay.trainpassenger.model.SeatSection;
import com.ajay.trainpassenger.model.Ticket;
import com.ajay.trainpassenger.model.User;
import com.ajay.trainpassenger.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {
    private final Map<String, Ticket> tickets = new HashMap<>();
    private final Map<SeatSection, List<Seat>> availableSeats = new HashMap<>();

    public TicketServiceImpl() {
        initializeSeats();
    }

    private void initializeSeats() {
        for (SeatSection section : SeatSection.values()) {
            availableSeats.put(section, new ArrayList<>());
            for (int i = 1; i <= 10; i++) {
                availableSeats.get(section).add(new Seat(section, i));
            }
        }
    }
    @Override
    public Ticket purchaseTicket(String from, String to, User user) {
        Ticket ticket = new Ticket();
        ticket.setFrom(from);
        ticket.setTo(to);
        ticket.setUser(user);
        ticket.setPricePaid(20.0);

        SeatSection requestedSection = tickets.size() % 2 == 0 ? SeatSection.A : SeatSection.B;
        List<Seat> sectionSeats = availableSeats.get(requestedSection);

        if (!sectionSeats.isEmpty()) {
            Seat assignedSeat = sectionSeats.remove(0); // Get the first available seat
            ticket.setSeat(assignedSeat);
            tickets.put(user.getEmail(), ticket);
            return ticket;
        } else {
            // Handle the case where no seats are available in the requested section
            System.err.println("No available seats in section " + requestedSection);
            return null;
        }
    }

    @Override
    public Ticket getTicketDetails(String userEmail) {
        return tickets.get(userEmail);
    }

    @Override
    public List<String> getUsersBySection(String section) {
        List<String> usersInSection = new ArrayList<>();
        SeatSection requestedSection = SeatSection.valueOf(section.toUpperCase());  // Convert input to uppercase for case-insensitivity
        for (Ticket ticket : tickets.values()) {
            if (ticket.getSeat().getSection() == requestedSection) {
                usersInSection.add(ticket.getUser().getEmail());
            }
        }
        return usersInSection;
    }

    @Override
    public void removeUser(String userEmail) {
        tickets.remove(userEmail);
    }

    @Override
    public void modifyUserSeat(String userEmail, String newSection) {
        Ticket ticket = tickets.get(userEmail);
        if (ticket != null) {
            try {
                Seat userSeat = ticket.getSeat();
                SeatSection requestedSection = SeatSection.valueOf(newSection.toUpperCase());
                userSeat.setSection(requestedSection);
                ticket.setSeat(userSeat);
            } catch (IllegalArgumentException e) {
                // Handle the case where the input section is not a valid SeatSection
                // You may log an error or throw a specific exception depending on your requirements
                System.err.println("Invalid seat section: " + newSection);
            }
        }
    }
}
