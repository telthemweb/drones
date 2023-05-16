package com.drones.dronesinnotauzen.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
@Table(name = "medications")
public class Medication {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name="drones_serial", nullable=true)
    private Drone drone;
}