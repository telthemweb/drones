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
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePayloadDto {
	private int code;
    private String message;
    private Object payload;
}
