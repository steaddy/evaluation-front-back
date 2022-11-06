package com.example.moscow_price.DTO;

import com.example.moscow_price.Entity.ApartmentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgPriceAndSimilarApartments {
    private Integer avgPrice;
    private List<ApartmentEntity> apartmentEntities;
}
