package com.example.Back_End.threads;

import com.example.Back_End.core.TicketPool;
import com.example.Back_End.logging.Logger;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final Logger logger;
    private final int vendorId;
    private final int releaseRate;
    private final int totalTicketsToIssue;
    private int ticketsIssued = 0;

    public Vendor(TicketPool ticketPool, int vendorId, int releaseRate, int totalTicketsToIssue, Logger logger) {
        this.ticketPool = ticketPool;
        this.vendorId = vendorId;
        this.releaseRate = releaseRate;
        this.totalTicketsToIssue = totalTicketsToIssue;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            while (ticketsIssued < totalTicketsToIssue) {
                boolean ticketAdded = ticketPool.addTicket(vendorId);
                if (ticketAdded) {
                    ticketsIssued++;
                }
                Thread.sleep(1000 / releaseRate);
            }
            logger.log("Vendor " + vendorId + " has finished issuing tickets.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log("Vendor " + vendorId + " interrupted.");
        }
    }
}
