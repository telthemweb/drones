package com.drones.dronesinnotauzen.dto;
import com.drones.dronesinnotauzen.models.Model;
import com.drones.dronesinnotauzen.models.State;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
|--------------------------------------------------------------------------
|          WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneRequestDto {
    private String serialNumber;
    private String model;
    private int weightLimit;
    private Double batteryCapacity;
    private String state;
}