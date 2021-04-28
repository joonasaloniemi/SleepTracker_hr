/*
Project work
SleepTracker
Joona Saloniemi
*/


package com.example.harkka_ver_1;

public class CoordinatesOfCities {

    // This method gets city name and return coordinates as a one string.
    public String coordinates(String city) {

        String latitude;
        String longitude;

        // City locations for JSON api.
        switch (city) {
            case "Helsinki":
                latitude = "60.167";
                longitude = "24.945";
                break;
            case "Turku":
                latitude = "60.452";
                longitude = "22.267";
                break;
            case "Tampere":
                latitude = "61.497";
                longitude = "23.761";
                break;
            case "Jyväskylä":
                latitude = "62.242";
                longitude = "25.749";
                break;
            case "Vaasa":
                latitude = "63.093";
                longitude = "21.616";
                break;
            case "Kuopio":
                latitude = "62.893";
                longitude = "27.689";
                break;
            case "Oulu":
                latitude = "65.014";
                longitude = "25.472";
                break;
            case "Rovaniemi":
                latitude = "66.503";
                longitude = "25.728";
                break;
            default:
                latitude = "61.059";
                longitude = "28.188";
                break;
        }

        // Return readable value for JSON URL.
        String cityCoordinates = ("lat=" + latitude + "&lng=" + longitude);

        return (cityCoordinates);
    }

}
