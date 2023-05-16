package com.drones.dronesinnotauzen.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.drones.dronesinnotauzen.models.Drone;
import com.drones.dronesinnotauzen.models.Medication;

/*
|--------------------------------------------------------------------------
|          WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/

public interface  MedicationRepository extends JpaRepository<Medication, String> {
	 Medication getByCode(String medicationcode);
	 List<Medication> getAllByDrone(Drone drone);
}
