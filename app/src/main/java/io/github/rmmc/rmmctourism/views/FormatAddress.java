package io.github.rmmc.rmmctourism.views;

public class FormatAddress {

    public static String setAddress(String city, String barangay, String address){
        return "Brgy " + barangay + ", " + city + ", " + address;
    }
}
