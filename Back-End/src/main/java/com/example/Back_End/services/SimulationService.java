package com.example.Back_End.services;

import com.example.Back_End.config.Configuration;
import com.example.Back_End.core.TicketPool;
import com.example.Back_End.logging.Logger;
import com.example.Back_End.models.Log;
import com.example.Back_End.repositories.LogRepository;
import com.example.Back_End.threads.Customer;
import com.example.Back_End.threads.Vendor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {

    private final LogRepository logRepository;
    private final Logger logger;
    private TicketPool ticketPool;
    private Configuration config;
    private final List<Thread> vendorThreads = new ArrayList<>();
    private Thread customerThread;
    private boolean isRunning = false;

    public SimulationService(LogRepository logRepository, Logger logger) {
        this.logRepository = logRepository;
        this.logger = logger;
    }

    public void loadConfiguration(Configuration config) {
        this.config = config;
        this.ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets(), logger);
        logger.log("Configuration loaded: " + config);
    }

    public void startSimulation() {
        if (isRunning) {
            logger.log("Simulation is already running.");
            return;
        }

        // Clear previous logs before starting a new simulation
        logger.logInitialize();

        isRunning = true;
        int totalTickets = config.getTotalTickets();
        int ticketsPerVendor = totalTickets / config.getNoOfVendors();
        int extraTickets = totalTickets % config.getNoOfVendors();

        for (int i = 1; i <= config.getNoOfVendors(); i++) {
            int vendorTicketQuota = ticketsPerVendor + (i <= extraTickets ? 1 : 0);
            Vendor vendor = new Vendor(ticketPool, i, config.getTicketReleaseRate(), vendorTicketQuota, logger);
            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate(), logger);
        customerThread = new Thread(customer, "Customer-Thread");
        customerThread.start();

        logger.log("Simulation started.");
    }

    public void stopSimulation() {
        if (!isRunning) {
            logger.log("Simulation is not running.");
            return;
        }

        vendorThreads.forEach(Thread::interrupt);
        if (customerThread != null) {
            customerThread.interrupt();
        }

        isRunning = false;
        logger.log("Simulation stopped by user.");
    }

    public void restartSimulation() {
        stopSimulation();
        logger.log("Restarting simulation...");
        startSimulation();
    }

    public void endSimulation() {
        stopSimulation();

        if (ticketPool != null) {
            ticketPool.markSimulationComplete();
        }

        vendorThreads.clear();
        customerThread = null;

        isRunning = false;
        logger.log("Simulation ended.");
    }

    public List<String> getLogs() {
//        logger.log("Fetching logs from MongoDB."); to check if its working
        return logRepository.findAll().stream()
                .map(Log::toString)
                .toList();
    }

    public boolean isSimulationRunning() {
        return isRunning;
    }
}
