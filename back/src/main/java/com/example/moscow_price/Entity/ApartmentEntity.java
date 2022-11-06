package com.example.moscow_price.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Apartment",  schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column
    private String location;

    @Column
    private Double coordinateLat;

    @Column
    private Double coordinateLng;

    @Column
    private String numberOfRooms;

    @Column
    private String segment;

    @Column
    private Integer numberOfFloors;

    @Column
    private String wallMaterial;

    @Column
    private Integer locationFloor;

    @Column(name = "apartment_area")
    private Double apartmentArea;

    @Column
    private Double kitchenArea;

    @Column
    private String balcony;

    @Column
    private Integer metroDistance;

    @Column
    private String condition;

    @Column
    private Integer price;
}
