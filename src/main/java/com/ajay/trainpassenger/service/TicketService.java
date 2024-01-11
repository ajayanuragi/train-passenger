package com.ajay.trainpassenger.service;

import com.ajay.trainpassenger.model.Ticket;
import com.ajay.trainpassenger.model.User;

import java.util.List;

public interface TicketService {
    Ticket purchaseTicket(String from, String to, User user);

    Ticket getTicketDetails(String userEmail);

    List<String> getUsersBySection(String section);

    void removeUser(String userEmail);

    void modifyUserSeat(String userEmail, String newSection);
}
