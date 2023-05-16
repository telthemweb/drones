package com.drones.dronesinnotauzen.repositories;

import com.drones.dronesinnotauzen.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

/*
|--------------------------------------------------------------------------
|          WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/

public interface ModelRepository extends JpaRepository<Model, String> {
	Model getByName(String name);
}
