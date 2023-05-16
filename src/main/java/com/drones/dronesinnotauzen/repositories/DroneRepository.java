package com.drones.dronesinnotauzen.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drones.dronesinnotauzen.models.Drone;
import com.drones.dronesinnotauzen.models.State;
/*
|--------------------------------------------------------------------------
|          WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/
public interface DroneRepository extends JpaRepository<Drone, String> {
    Drone findBySerialNumber(String serialNumber);
    List<Drone> findAllByState(State state);
}
