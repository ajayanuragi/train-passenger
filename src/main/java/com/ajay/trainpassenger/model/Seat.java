package com.ajay.trainpassenger.model;

public class Seat {
    private SeatSection section;
    private int seatNumber;

    public Seat(SeatSection section, int seatNumber) {
        this.section = section;
        this.seatNumber = seatNumber;
    }

    public Seat() {
    }

    public SeatSection getSection() {
        return section;
    }

    public void setSection(SeatSection section) {
        this.section = section;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
