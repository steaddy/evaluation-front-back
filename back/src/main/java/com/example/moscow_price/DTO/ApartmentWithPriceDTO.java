package com.example.moscow_price.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentWithPriceDTO {
    private String location;
    private String numberOfRooms;
    private String segment;
    private Integer numberOfFloors;
    private String wallMaterial;
    private Integer locationFloor;
    private Double apartmentArea;
    private Double kitchenArea;
    private String balcony;
    private Integer metroDistance;
    private String condition;
    private Integer price;
}
