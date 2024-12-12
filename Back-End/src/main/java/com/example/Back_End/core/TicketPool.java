package com.example.Back_End.core;

import com.example.Back_End.logging.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final int maxCapacity;
    private final int totalTickets;
    private final Logger logger;
    private int currentTicketNumber = 1;
    private int currentPoolSize = 0;
    private boolean simulationComplete = false;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public TicketPool(int maxCapacity, int totalTickets, Logger logger) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.logger = logger;
    }

    public boolean addTicket(int vendorId) {
        lock.lock();
        try {
            while (currentPoolSize >= maxCapacity) {
                logger.log("Ticket Pool is Full. Vendor " + vendorId + " is waiting...");
                notFull.await();
            }

            if (currentTicketNumber <= totalTickets) {
                currentPoolSize++;
                logger.log("Vendor " + vendorId + " issued Ticket-" + currentTicketNumber);
                currentTicketNumber++;
                notEmpty.signal();
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log("Vendor " + vendorId + " interrupted while waiting to add tickets.");
            return false;
        } finally {
            lock.unlock();
        }
    }

    public String removeTicket(String customerName) {
        lock.lock();
        try {
            while (currentPoolSize <= 0) {
                if (simulationComplete) {
                    logger.log("No tickets left to sell.");
                    return null;
                }
                logger.log("No tickets available. " + customerName + " is waiting...");
                notEmpty.await();
            }

            String ticket = "Ticket-" + (currentTicketNumber - currentPoolSize);
            currentPoolSize--;
            logger.log(customerName + " purchased " + ticket);
            notFull.signal();
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void markSimulationComplete() {
        lock.lock();
        try {
            simulationComplete = true;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return currentPoolSize == 0;
        } finally {
            lock.unlock();
        }
    }
}
