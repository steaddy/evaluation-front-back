package com.example.moscow_price.servis;

import com.example.moscow_price.DTO.Coordinate;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoordinateService {

    public Coordinate getCoordinateByAddress(String address){

        Coordinate coordinate = new Coordinate();

        //https://maps.googleapis.com/maps/api/geocode/json?address=г. Москва, ул. Ватутина, д. 11&key=AIzaSyAG32D1j8UTA2-NELbuaBEbdvvrJq-nHW4

        RestTemplate restTemplate = new RestTemplate();
        String api_key = "AIzaSyAG32D1j8UTA2-NELbuaBEbdvvrJq-nHW4";
        String fooResourceUrl
                = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + api_key;
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);

        String json=  restTemplate.getForObject(fooResourceUrl, String.class);

//        System.out.println("#############" + json);

        String jsonpathLat = "$['results'][0]['geometry']['location']['lat']";
        String jsonpathLng = "$['results'][0]['geometry']['location']['lng']";

//        System.out.println("?????????????" + response);

        DocumentContext jsonContext = JsonPath.parse(json);
        Double coordinateLat = jsonContext.read(jsonpathLat);
        Double coordinateLng = jsonContext.read(jsonpathLng);

        coordinate.setCoordinateLat(coordinateLat);
        coordinate.setCoordinateLng(coordinateLng);

        System.out.printf(coordinateLat.toString());
        System.out.printf(coordinateLng.toString());

        return coordinate;
    }

    public Double getDistance(Coordinate coordinate1, Coordinate coordinate2){

        return distance(coordinate1.getCoordinateLat(), coordinate2.getCoordinateLat(),
                coordinate1.getCoordinateLng(), coordinate2.getCoordinateLng(), 0, 0);
    }

    private static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
