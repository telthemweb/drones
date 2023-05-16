package com.drones.dronesinnotauzen.jobs;
import com.drones.dronesinnotauzen.models.Drone;
import com.drones.dronesinnotauzen.repositories.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
/*
   |--------------------------------------------------------------------------
   |                        WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
   |--------------------------------------------------------------------------
   |
   |
   */
@Service
public class DroneBatteryScheduler {
    private static final Logger logger = LoggerFactory.getLogger(DroneBatteryScheduler.class);
    @Autowired
    private DroneRepository droneRepository;


    @Scheduled(fixedRate = 300000)
    @Async
    public void getDroneBattery(){

    //list all drones
        List<Drone> drones = droneRepository.findAll();
        //looping through the list of drones
        for (Drone drone : drones) {
            logger.info("Drone: " + drone.getSerialNumber() + " - Battery: " + drone.getBatteryCapacity());
        }
        //log message to log===
        logger.info("DroneBatteryScheduler done");
    }
}
