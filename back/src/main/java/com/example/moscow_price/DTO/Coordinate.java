package com.example.moscow_price.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Coordinate {
    private Double coordinateLat;
    private Double coordinateLng;

    public Coordinate(Double coordinateLat, Double coordinateLng) {
        this.coordinateLat = coordinateLat;
        this.coordinateLng = coordinateLng;
    }
}
