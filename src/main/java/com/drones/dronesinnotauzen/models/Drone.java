package com.drones.dronesinnotauzen.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
/*
|--------------------------------------------------------------------------
|          WRITTEN BY INNOCENT TAUZENI innocent.tauzeni@gmail.com
|--------------------------------------------------------------------------
|
|
*/
@Getter
@Setter
@Entity
@Table(name = "drones")
public class Drone {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "serial_number")
    private String serialNumber;

    @OneToOne(fetch = FetchType.EAGER)
    private Model model;

    @Column(name = "weight_limit")
    private int weightLimit;

    @Column(name = "battery_capacity")
    private Double batteryCapacity;
  @OneToOne(fetch = FetchType.EAGER)
  private State state;

  @OneToMany(mappedBy = "drone", fetch = FetchType.LAZY)
  private Set<Medication> medication;
}
