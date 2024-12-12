package com.example.Back_End.controllers;

import com.example.Back_End.config.Configuration;
import com.example.Back_End.services.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulation") // Base URL for simulation-related API endpoints
public class SimulationController {
    private final SimulationService simulationService;

    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService; // Injects the SimulationService
    }

    /**
     * Starts the simulation with the provided configuration.
     * @param config Configuration object containing simulation settings.
     * @return A confirmation message that the simulation has started.
     */
    @PostMapping("/start")
    public String startSimulation(@RequestBody Configuration config) {
        simulationService.loadConfiguration(config); // Load the configuration
        simulationService.startSimulation(); // Start the simulation
        return "Simulation started.";
    }

    /**
     * Stops the current simulation.
     * @return A confirmation message that the simulation has stopped.
     */
    @PostMapping("/stop")
    public String stopSimulation() {
        simulationService.stopSimulation(); // Stop the simulation
        return "Simulation stopped.";
    }

    /**
     * Restarts the simulation with the provided configuration.
     * @param config Configuration object containing simulation settings.
     * @return A confirmation message that the simulation has restarted.
     */
    @PostMapping("/restart")
    public String restartSimulation(@RequestBody Configuration config) {
        simulationService.loadConfiguration(config); // Reload the configuration
        simulationService.restartSimulation(); // Restart the simulation
        return "Simulation restarted.";
    }

    /**
     * Ends the simulation completely.
     * @return A confirmation message that the simulation has ended.
     */
    @PostMapping("/end")
    public String endSimulation() {
        simulationService.endSimulation(); // End the simulation
        return "Simulation ended.";
    }

    /**
     * Retrieves the simulation logs from the service.
     * @return A list of log messages.
     */
    @GetMapping("/logs")
    public List<String> getLogs() {
        return simulationService.getLogs(); // Get logs from the service
    }

    /**
     * Checks the current simulation status (running or stopped).
     * @return "Running" if the simulation is active, otherwise "Stopped".
     */
    @GetMapping("/status")
    public String getSimulationStatus() {
        return simulationService.isSimulationRunning() ? "Running" : "Stopped"; // Check simulation status
    }
}
