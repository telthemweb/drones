package com.drones.dronesinnotauzen.controllers;
/*
|--------------------------------------------------------------------------
|          WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/

import com.drones.dronesinnotauzen.dto.DroneRequestDto;
import com.drones.dronesinnotauzen.dto.LoadDroneRequestDto;
import com.drones.dronesinnotauzen.dto.MessageResponseDto;
import com.drones.dronesinnotauzen.dto.MessageResponsePayloadDto;
import com.drones.dronesinnotauzen.services.DroneService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/drones")
public class DroneController {
    private static Logger logger = LoggerFactory.getLogger(DroneController.class);
    @Autowired
    private DroneService droneService;

    @PostMapping("/register")
    public MessageResponseDto registerDrone(HttpServletRequest request, @RequestBody DroneRequestDto droneRequestDto) {
        logger.info("Drone registration request received");
        return droneService.RegisterDrone(request, droneRequestDto);
    }

    //get all drones
    @PostMapping("/getalldrones")
    public MessageResponseDto loadDrone(HttpServletRequest request, @RequestBody LoadDroneRequestDto loadDroneRequestDto) {
        return droneService.load(request, loadDroneRequestDto);
    }

    //check drone load
    @GetMapping("/checkload")
    public MessageResponsePayloadDto checkLoadDrones(HttpServletRequest request, @RequestParam("serial") Optional<String> serial) {
        return droneService.checkLoadedDronewithMedication(request, serial.orElse(null));
    }

    //check available drone
    @GetMapping("/checkavailabledrone")
    public MessageResponsePayloadDto checkAvailableDrones(HttpServletRequest request) {
        return droneService.GetavailableDrones(request);
    }
    //check drone battery level
    @GetMapping("/dronebattery")
    public MessageResponsePayloadDto checkBatteryDrones(HttpServletRequest request, @RequestParam("serial") Optional<String> serial) {
        return droneService.DronebatteryLevel(request, serial.orElse(null));
    }

}
