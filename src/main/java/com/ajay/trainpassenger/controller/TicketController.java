package com.ajay.trainpassenger.controller;

import com.ajay.trainpassenger.model.Ticket;
import com.ajay.trainpassenger.model.TicketRequest;
import com.ajay.trainpassenger.model.User;
import com.ajay.trainpassenger.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> purchaseTicket(@RequestBody TicketRequest ticketRequest) {
        User user = new User();
        user.setFirstName(ticketRequest.getUserFirstName());
        user.setLastName(ticketRequest.getUserLastName());
        user.setEmail(ticketRequest.getUserEmail());

        Ticket ticket = ticketService.purchaseTicket(ticketRequest.getFrom(), ticketRequest.getTo(), user);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            // Handle the case where no seats are available
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<Ticket> getTicketDetails(@PathVariable String userEmail) {
        Ticket ticket = ticketService.getTicketDetails(userEmail);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{section}")
    public ResponseEntity<List<String>> getUsersBySection(@PathVariable String section) {
        List<String> usersInSection = ticketService.getUsersBySection(section);
        if (usersInSection != null){
            return ResponseEntity.ok(usersInSection);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{userEmail}")
    public ResponseEntity<Void> removeUser(@PathVariable String userEmail) {
        ticketService.removeUser(userEmail);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userEmail}")
    public ResponseEntity<Void> modifyUserSeat(@PathVariable String userEmail, @RequestParam String newSection) {
        ticketService.modifyUserSeat(userEmail, newSection);
        return ResponseEntity.ok().build();
    }


}
