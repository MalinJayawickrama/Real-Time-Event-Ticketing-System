package com.example.Back_End.config;

/**
 * Represents the simulation configuration with parameters like total tickets,
 * ticket capacity, vendors, release rate, and retrieval rate.
 */
public class Configuration {
    private int totalTickets; // Total tickets to issue
    private int maxTicketCapacity; // Maximum ticket pool capacity
    private int noOfVendors; // Number of vendors
    private int ticketReleaseRate; // Tickets released per second
    private int customerRetrievalRate; // Tickets retrieved by customers per second

    // Default constructor
    public Configuration() {}

    // Parameterized constructor for setting all configuration values
    public Configuration(int totalTickets, int maxTicketCapacity, int noOfVendors, int ticketReleaseRate, int customerRetrievalRate) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.noOfVendors = noOfVendors;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // Getters and setters for accessing and updating fields
    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; }

    public int getNoOfVendors() { return noOfVendors; }
    public void setNoOfVendors(int noOfVendors) { this.noOfVendors = noOfVendors; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }

    // Formats the configuration details for display or logs
    @Override
    public String toString() {
        return "\nConfiguration Settings: \n" +
                "_______________________\n" +
                "Total Tickets to Issue: " + totalTickets + "\n" +
                "Maximum Ticket Capacity: " + maxTicketCapacity + "\n" +
                "No Of Vendors: " + noOfVendors + "\n" +
                "Ticket Release Rate: " + ticketReleaseRate + " tickets/second\n" +
                "Customer Retrieval Rate: " + customerRetrievalRate + " tickets/second\n";
    }
}
