package com.example.moscow_price.servis;

import com.example.moscow_price.DTO.ApartmentDTO;
import com.example.moscow_price.DTO.ApartmentWithPriceDTO;
import com.example.moscow_price.DTO.AvgPriceAndSimilarApartments;
import com.example.moscow_price.DTO.Coordinate;
import com.example.moscow_price.Entity.ApartmentEntity;
import com.example.moscow_price.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class ApartmentService {

    @Autowired
    private CoordinateService coordinateService;

    @Autowired
    private ApartmentRepository apartmentRepository;

    public List<ApartmentEntity> storeToDB(List<ApartmentWithPriceDTO> apartmentDTOList){
        List<ApartmentEntity> apartmentEntities = new ArrayList<>();

        for (ApartmentWithPriceDTO apartmentDTO: apartmentDTOList){
            Coordinate coordinate = coordinateService.getCoordinateByAddress(apartmentDTO.getLocation());
            ApartmentEntity apartmentEntity = apartmentDtoToEntity(apartmentDTO, coordinate);

//            apartmentRepository.save(apartmentEntity);

            apartmentEntities.add(apartmentEntity);
        }

        apartmentRepository.saveAll(apartmentEntities);
        return apartmentEntities;
    }

    public List<ApartmentEntity> getApartmentsFromDB(){
        Iterable<ApartmentEntity> all = apartmentRepository.findAll();
        ArrayList<ApartmentEntity> apartmentEntities = new ArrayList<>((Collection) all);

        return apartmentEntities;
    }

    public AvgPriceAndSimilarApartments calculatePrice(ApartmentDTO apartmentDTO){
        Coordinate coordinateByAddress = coordinateService.getCoordinateByAddress(apartmentDTO.getLocation());

        List<ApartmentEntity> similarApartments = apartmentRepository.findApartmentEntitiesByNumberOfRoomsAndSegmentAndNumberOfFloorsAndWallMaterial(apartmentDTO.getNumberOfRooms(),
                apartmentDTO.getSegment(), apartmentDTO.getNumberOfFloors(), apartmentDTO.getWallMaterial());

        HashMap<ApartmentEntity, Double> apartmentEntityToDistance = new HashMap<>();
        for (ApartmentEntity apartmentEntity: similarApartments){
            Coordinate similarApartmentCoordinate = new Coordinate(apartmentEntity.getCoordinateLat(),
                    apartmentEntity.getCoordinateLng());
            Double distance = coordinateService.getDistance(coordinateByAddress, similarApartmentCoordinate);
            System.out.println(distance);
            apartmentEntityToDistance.put(apartmentEntity, distance);
        }

        int suitableApartmentsNumber = 0;
        double searchRadius = 1000.0;
        ArrayList<ApartmentEntity> suitableApartments = new ArrayList<>();
        while (searchRadius < 5000.0 && suitableApartments.size() < 3){
            suitableApartments.clear();
            for (Map.Entry<ApartmentEntity, Double> apartmentEntityDoubleEntry: apartmentEntityToDistance.entrySet()){
                if (apartmentEntityDoubleEntry.getValue() <= searchRadius){
                    suitableApartments.add(apartmentEntityDoubleEntry.getKey());
                }
            }
//            apartmentEntityToDistance.entrySet().stream()
//                    .filter(apartmentEntityDoubleEntry -> apartmentEntityDoubleEntry.getValue() <= searchRadius)

            searchRadius += 500.0;
        }

        System.out.println("similarApartments " + similarApartments);
        System.out.println("suitableApartments " + suitableApartments);

//        if (suitableApartments.size() < 3){
//            throw new RuntimeException("Not enough data for calculate");
//        }

        double asDouble = suitableApartments.stream()
                .flatMapToInt(apartmentEntity -> IntStream.of(apartmentEntity.getPrice()))
                .average().getAsDouble();
        int avgPrice = (int) asDouble;

        AvgPriceAndSimilarApartments avgPriceAndSimilarApartments =
                new AvgPriceAndSimilarApartments(avgPrice, suitableApartments);

        return avgPriceAndSimilarApartments;
    }

    private ApartmentEntity apartmentDtoToEntity(ApartmentWithPriceDTO apartmentDTO, Coordinate coordinate){
        ApartmentEntity apartmentEntity = new ApartmentEntity();
        apartmentEntity.setLocation(apartmentDTO.getLocation());
        apartmentEntity.setNumberOfRooms(apartmentDTO.getNumberOfRooms());
        apartmentEntity.setSegment(apartmentDTO.getSegment());
        apartmentEntity.setNumberOfFloors(apartmentDTO.getNumberOfFloors());
        apartmentEntity.setWallMaterial(apartmentDTO.getWallMaterial());
        apartmentEntity.setLocationFloor(apartmentDTO.getLocationFloor());
        apartmentEntity.setApartmentArea(apartmentDTO.getApartmentArea());
        apartmentEntity.setKitchenArea(apartmentDTO.getKitchenArea());
        apartmentEntity.setBalcony(apartmentDTO.getBalcony());
        apartmentEntity.setMetroDistance(apartmentDTO.getMetroDistance());
        apartmentEntity.setCondition(apartmentDTO.getCondition());
        apartmentEntity.setCoordinateLat(coordinate.getCoordinateLat());
        apartmentEntity.setCoordinateLng(coordinate.getCoordinateLng());
        apartmentEntity.setPrice(apartmentDTO.getPrice());

        return apartmentEntity;
    }
}
