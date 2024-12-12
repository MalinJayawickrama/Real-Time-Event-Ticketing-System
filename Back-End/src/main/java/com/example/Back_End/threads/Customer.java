package com.example.Back_End.threads;

import com.example.Back_End.core.TicketPool;
import com.example.Back_End.logging.Logger;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final Logger logger;
    private final int retrievalRate;
    private int customerNumber = 1;
    private boolean isRunning = true;

    public Customer(TicketPool ticketPool, int retrievalRate, Logger logger) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                String customerName = "Customer " + customerNumber;
                String ticket = ticketPool.removeTicket(customerName);
                if (ticket != null) {
                    customerNumber++;
                }
                Thread.sleep(1000 / retrievalRate);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        isRunning = false;
    }
}
