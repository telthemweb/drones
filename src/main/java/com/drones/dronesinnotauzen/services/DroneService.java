package com.drones.dronesinnotauzen.services;


import java.util.List;

import com.drones.dronesinnotauzen.dto.*;
import com.drones.dronesinnotauzen.models.Model;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.drones.dronesinnotauzen.models.Drone;
import com.drones.dronesinnotauzen.models.State;
import com.drones.dronesinnotauzen.models.Medication;
import com.drones.dronesinnotauzen.repositories.DroneRepository;
import com.drones.dronesinnotauzen.repositories.MedicationRepository;
import com.drones.dronesinnotauzen.repositories.ModelRepository;
import com.drones.dronesinnotauzen.repositories.StateRepository;
import com.drones.dronesinnotauzen.exception.CustomException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

/*
|--------------------------------------------------------------------------
|                        WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/

@Service
public class DroneService {
	
	
	public Logger logger = LoggerFactory.getLogger(DroneService.class);

	@Autowired
    private DroneRepository  droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    /*
    |--------------------------------------------------------------------------
    |                        WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
    |--------------------------------------------------------------------------
    |           This function will register drone
    |
    */
    public MessageResponseDto RegisterDrone(HttpServletRequest request, DroneRequestDto droneRequestDto) {
    	
    	//check if drone is already added in database
        Drone found = droneRepository.findBySerialNumber(droneRequestDto.getSerialNumber());
        if (found != null) {
            throw new CustomException("Drone with serial number " + droneRequestDto.getSerialNumber() + " already exists", HttpStatus.BAD_REQUEST);
        }
      //check drone serial number lenght
        Drone drone = new Drone();
        if (droneRequestDto.getSerialNumber().length() > 100) {
            throw new CustomException("Serial number cannot be more than 100 characters long", HttpStatus.BAD_REQUEST);
        }
      //check for model name is available
        Model model = modelRepository.getByName(droneRequestDto.getModel().substring(0, 1).toUpperCase() + droneRequestDto.getModel().substring(1).toLowerCase());
        if (model == null) {
            throw new CustomException("Model not found", HttpStatus.BAD_REQUEST);
        }

      //check weight 
        if (droneRequestDto.getWeightLimit() > 500) {
            logger.info("Weight limit cannot be more than 500 grams " + HttpStatus.BAD_REQUEST.value());
            throw new CustomException("Weight limit cannot be more than 500 grams", HttpStatus.BAD_REQUEST);
        }

        State state = stateRepository.getByName(droneRequestDto.getState().toUpperCase());
        if (state == null) {
            throw new CustomException("State not found", HttpStatus.BAD_REQUEST);
        }

        drone.setSerialNumber(droneRequestDto.getSerialNumber());
        drone.setModel(model);
        drone.setBatteryCapacity(droneRequestDto.getBatteryCapacity());
        drone.setWeightLimit(droneRequestDto.getWeightLimit());
        drone.setState(state);

        Drone savedDrone = droneRepository.save(drone);

        logger.info("Drone registration request completed");

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setMessage("Drone with serial number " + savedDrone.getSerialNumber() + " successfully registered");
        messageResponseDto.setCode(HttpStatus.OK.value());

        return modelMapper.map(messageResponseDto, MessageResponseDto.class);
    }
    
    /*
    |--------------------------------------------------------------------------
    |                        WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
    |--------------------------------------------------------------------------
    |           This function will register Medication
    |
    */
    
    public MedicationResponseDto RegisterMedication(HttpServletRequest request, MedicationRequestDto medicationRequestDto) throws Exception {
        try {
        	Medication medication = new Medication();

            if (!medicationRequestDto.getCode().matches("^[A-Z0-9_]*$")) {
                throw new Exception("Medication code can only contain upper case letters, numbers and ‘_’");
            }

            if (!medicationRequestDto.getName().matches("^[a-zA-Z0-9_-]*$")) {
                throw new Exception("Medication code can only contain letters, numbers, ‘-‘, ‘_’");
            }

            medication.setCode(medicationRequestDto.getCode());
            medication.setName(medicationRequestDto.getName());
            medication.setWeight(medicationRequestDto.getWeight());
            medication.setImage(medicationRequestDto.getImage());

            Medication savedMedication = medicationRepository.save(medication);

            return modelMapper.map(savedMedication, MedicationResponseDto.class);
        } catch (Exception e) {
            throw new Exception(e.getLocalizedMessage());
        }
    }
    
    
    public MessageResponseDto load(HttpServletRequest request, LoadDroneRequestDto loadDroneRequestDto) {

        Drone drone = droneRepository.findBySerialNumber(loadDroneRequestDto.getSerialNumber());
        logger.info("Drone found: " + drone);
        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        Medication medication = medicationRepository.getByCode(loadDroneRequestDto.getMedicationCode());
        logger.info("Medication found: " + medication);
        if (medication == null) {
            throw new CustomException("Medication not found", HttpStatus.BAD_REQUEST);
        }

        if (medication.getDrone() != null) {
            throw new CustomException("Medication already loaded", HttpStatus.BAD_REQUEST);
        }

        if (drone.getState().getName().equals("LOADED")) {
            throw new CustomException("Drone is loaded", HttpStatus.BAD_REQUEST);
        }

        if (drone.getBatteryCapacity() >= 25) {
            drone.setState(stateRepository.getByName("LOADING"));
            droneRepository.save(drone);
        }

        if (!drone.getState().getName().equals("LOADING")) {
            throw new CustomException("Drone is not in loading state", HttpStatus.BAD_REQUEST);
        }

        List<Medication> medications = medicationRepository.getAllByDrone(droneRepository.findBySerialNumber(loadDroneRequestDto.getSerialNumber()));
        logger.info("Medications found: " + medications.size());

        double totalWeight = medications.stream().mapToDouble(Medication::getWeight).sum();
        logger.info("Total weight: " + totalWeight);

        if (totalWeight + medication.getWeight() > drone.getWeightLimit()) {
            throw new CustomException("Drone cannot load medication as it exceeds its carrying capacity", HttpStatus.BAD_REQUEST);
        }

        medication.setDrone(drone);
        medicationRepository.save(medication);

        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Medication successfully loaded");
        responseDto.setCode(HttpStatus.OK.value());

        return modelMapper.map(responseDto, MessageResponseDto.class);
    }
    public MessageResponsePayloadDto DronebatteryLevel(HttpServletRequest request, String serial) {

        Drone drone = droneRepository.findBySerialNumber(serial);

        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Battery level for drone with serial number " + serial);
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(drone.getBatteryCapacity());

        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);
    }

    public MessageResponsePayloadDto GetavailableDrones(HttpServletRequest request) {

        State state = stateRepository.getByName("IDLE");
        List<Drone> drone = droneRepository.findAllByState(state);
        if (drone.size() == 0){
            throw new CustomException("No drones available", HttpStatus.BAD_REQUEST);
        }

        List<DroneResponseDto> listOfDrones = drone
                .stream()
                .filter(drone1 -> drone1.getBatteryCapacity() >= 25)
                .map(drone1 -> modelMapper.map(drone1, DroneResponseDto.class)).toList();

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Success");
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(listOfDrones);
        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);

    }

    public MessageResponsePayloadDto checkLoadedDronewithMedication(HttpServletRequest request, String serial) {

        Drone drone = droneRepository.findBySerialNumber(serial);

        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        List<Medication> medications = medicationRepository.getAllByDrone(drone);
        List<MedicationResponseDto> listOfMedications = medications
                .stream()
                .map(medication -> modelMapper.map(medication, MedicationResponseDto.class)).toList();

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Success");
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(listOfMedications);

        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);

    }
    
    
    
    
}
