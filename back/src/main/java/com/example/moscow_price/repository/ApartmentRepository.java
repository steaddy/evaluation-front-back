package com.example.moscow_price.repository;

import com.example.moscow_price.Entity.ApartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {

    List<ApartmentEntity> findApartmentEntitiesByNumberOfRoomsAndSegmentAndNumberOfFloorsAndWallMaterial(String NumberOfRooms,
                                                                                                         String Segment,
                                                                                                         Integer NumberOfFloors,
                                                                                                         String WallMaterial);

}
