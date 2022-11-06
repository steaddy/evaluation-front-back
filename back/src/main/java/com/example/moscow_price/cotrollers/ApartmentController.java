package com.example.moscow_price.cotrollers;

import com.example.moscow_price.DTO.ApartmentDTO;
import com.example.moscow_price.DTO.AvgPriceAndSimilarApartments;
import com.example.moscow_price.Entity.ApartmentEntity;
import com.example.moscow_price.servis.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @PostMapping("/apartment")
    public AvgPriceAndSimilarApartments referenceApartment(@RequestBody ApartmentDTO apartmentDTO){

        AvgPriceAndSimilarApartments apartmentEntities = apartmentService.calculatePrice(apartmentDTO);

        return apartmentEntities;
    }

    @GetMapping("/apartmentsData")
    public List<ApartmentEntity> getDataFromDB(){
        return apartmentService.getApartmentsFromDB();
    }
}
