package io.github.rmmc.rmmctourism.model;

import java.util.HashMap;
import java.util.Map;

public class CItyBarangayData {

    public static String[] cityList = {"Banga", "Lake Sebu", "Norala", "Polomolok", "Santo Niño", "Surallah", "T'Boli", "Tampakan", "Tantangan", "Tupi", "Koronadal"};

    public static Map<String, String[]> brgyList() {
        Map<String, String[]> brgy = new HashMap<>();

        // Barangays for Banga
        brgy.put("Koronadal", new String[]{"Assumption (Bulol)", "Avanceña (Barrio 3)", "Cacub", "Caloocan", "Carpenter Hill", "Concepcion (Barrio 6)", "Esperanza", "Mabini", "Magsaysay", "Mambucal", "Morales", "Namnama", "Paraiso", "Rotonda", "San Isidro", "San Jose (Barrio 5)", "New Pangasinan (Barrio 4)", "San Roque", "Santa Cruz", "Santo Niño (Barrio 2)", "Saravia (Barrio 8)", "Topland (Barrio 7)", "Zone 1 (Poblacion)", "Zone 2 (Poblacion)", "Zone 3 (Poblacion)", "Zone 4 (Poblacion)", "General Paulino Santos (Barrio 1)"});

        brgy.put("Banga", new String[]{"Assumption (Bulol)", "Avanceña (Barrio 3)", "Benitez (Poblacion)", "Cabudian", "Cabuling", "Cinco (Barrio 5)", "Derilon", "El Nonok", "Improgo Village (Poblacion)", "Kusan (Barrio 8)", "Lam-Apos", "Lamba", "Lambingi", "Lampari", "Liwanay (Barrio 1)", "Malaya (Barrio 9)", "Punong Grande (Barrio 2)", "Rang-ay (Barrio 4)", "Reyes (Poblacion)", "Rizal (Barrio 3)", "Rizal Poblacion", "San Jose (Barrio 7)", "San Vicente (Barrio 6)", "Yangco Poblacion"});

        brgy.put("Lake Sebu", new String[]{"Bacdulong", "Denlag", "Halilan", "Hanoon", "Klubi", "Lake Lahit", "Lamcade", "Lamdalag", "Lamfugon", "Lamlahak", "Lower Maculan", "Luhib", "Ned", "Poblacion", "Siloton", "Lake Seloton", "Talisay", "Takunel", "Upper Maculan", "Tasiman"});

        brgy.put("Norala", new String[]{"Dumaguil", "Esperanza", "Kibid", "Lapuz", "Liberty", "Lopez Jaena", "Matapol", "Poblacion", "Puti", "San Jose", "San Miguel", "Simsiman", "Tinago", "Benigno Aquino, Jr."});

        brgy.put("Polomolok", new String[]{"Bentung", "Crossing Palkan", "Bentung", "Crossing Palkan", "Glamang", "Kinilis", "Klinan", "Koronadal Proper", "Lam-Caliaf", "Landan", "Lumakil", "Maligo", "Palkan", "Poblacion", "Polo", "Magsaysay", "Rubber", "Silway 7", "Silway 8", "Sulit", "Sumbakil", "Upper Klinan", "Lapu", "Cannery Site", "Pagalungan"});

        brgy.put("Santo Niño", new String[]{"Ambalgan", "Guinsang-an (Bo.4)", "Katipunan (Bo.11)", "Manuel Roxas (Bo.10)", "New Panay (Bo.9)", "Poblacion (Bo. 13)", "San Isidro (Bo. 12)", "San Vicente (Bo. 5)", "Teresita", "Sajaneba"});

        brgy.put("Surallah", new String[]{"Buenavista", "Centrala", "Colongulo", "Dajay", "Duengas", "Canahay (Godwino)", "Lambontong", "Lamian", "Lamsugod", "Libertad (Poblacion)", "Little Baguio", "Moloy", "Naci (Doce)", "Talahik", "Tubiala", "Upper Sepaka", "Veterans"});

        brgy.put("T'Boli", new String[]{"Aflek", "Afus", "Basag", "Datal Bob", "Desawo", "Datal Dlanag", "Edwards (Poblacion)", "Kematu", "Laconon", "Lambangan", "Lambuling", "Lamhako", "Lamsalome", "Lemsnolon", "Maan", "Malugong", "Mongocayo", "New Dumangas", "Poblacion", "Salacafe", "Sinolon", "Talcon", "Talufo", "T'bolok", "Tudok"});

        brgy.put("Tampakan", new String[]{"Albagan", "Buto", "Danlag", "Kipalbig", "Lambayong", "Liberty", "Lampitak", "Maltana", "Poblacion", "Palo", "Pula Bato", "San Isidro", "Santa Cruz", "Tablu"});

        brgy.put("Tangtangan", new String[]{"Bukay Pait", "Cabuling", "Dumadalig", "Libas", "Magon", "Maibo", "Mangilala", "New Iloilo", "New Lambunao", "Poblacion", "San Felipe", "New Cuyapo", "Tinongcop"});

        brgy.put("Tupi", new String[]{"Acmonan", "Bololmala", "Bunao", "Cebuano", "Crossing Rubber", "Kablon", "Kalkam", "Linan", "Lunen", "Miasong", "Palian", "Poblacion", "Polonuling", "Simbo", "Tubeng"});

        return brgy;
    }
}
