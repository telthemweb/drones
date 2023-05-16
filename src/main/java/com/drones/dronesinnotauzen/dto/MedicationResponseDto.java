package com.drones.dronesinnotauzen.dto;

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
public class MedicationResponseDto {
    private String code;
    private String name;
    private Double weight;
    private String image;
}
