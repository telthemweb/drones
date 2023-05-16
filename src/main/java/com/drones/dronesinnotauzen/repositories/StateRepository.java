package com.drones.dronesinnotauzen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drones.dronesinnotauzen.models.State;
/*
|--------------------------------------------------------------------------
|          WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/
public interface StateRepository extends JpaRepository<State, Long> {
	State getByName(String name);
}
