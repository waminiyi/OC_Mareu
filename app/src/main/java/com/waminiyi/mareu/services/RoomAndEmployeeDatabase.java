package com.waminiyi.mareu.services;

import com.waminiyi.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomAndEmployeeDatabase {
    private static RoomAndEmployeeDatabase INSTANCE;
    private List<String> mMeetingRoomsList;
    private List<String> mEmployeesMailList;
    private int mRandomColor;

    public RoomAndEmployeeDatabase() {
    }

    public static RoomAndEmployeeDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (RoomAndEmployeeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RoomAndEmployeeDatabase();
                }
            }
        }
        return INSTANCE;
    }


    public List<String> getRoomList() {
        mMeetingRoomsList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            mMeetingRoomsList.add("Salle " + i);
        }

        return mMeetingRoomsList;
    }

    public List<String> getEmployeesMailList() {
        List<String> employeesList = Arrays.asList("abdallah", "abdel", "abdelah", "abdelaziz", "abdelhalim", "abderrahim", "abdoullah", "abel", "achille", "adam", "adeline", "adolf", "adolphe", "adrian", "adriana", "adrianus", "adrien",
                "adrienne", "adélaïde", "agathe", "aglaé", "agnès", "ahmad", "ahmed", "aimery", "aimé", "aimée", "alain", "alan", "alban", "albert", "albertine", "alberto", "albin", "albine",
                "albino", "albéric", "aldo", "alec", "alessandra", "alessandro", "alex", "alexander", "alexandra", "alexandre", "alexandrine", "alexia", "alexis", "alfred", "ali", "alice",
                "alicia", "alida", "aline", "alison", "alistair", "allan-david", "allan", "allen", "alphonse", "amanda", "amandine", "amar", "amaury", "ambroise", "amel", "amina", "amélie",
                "ana-Maria", "anna-Maria", "ana", "anna", "anaïs", "andrea", "andreas", "andrew", "andré-clément", "andré-Jean", "andré-Louis", "andré-Luc", "andré-Marie", "andré-Michel",
                "andré", "andrée", "ange", "angel", "angelina", "angelo", "angie", "angèle", "angélique", "anissa", "anita", "ann-charlotte", "anne-charlotte", "ann", "anna-Lisa", "anna-Maria",
                "anna", "annabel", "annabelle", "anne-catherine", "anne-charlotte", "anne-claire", "anne-clotilde", "anne-cécile", "anne-France", "anne-Françoise", "anne-Gaëlle",
                "anne-Joséphine", "anne-Laure", "anne-Lise", "anne-Lyse", "anne-Marie", "anne-Sophie", "anne", "annette", "annick", "annie-claude", "annie", "anny", "anouchka", "anouk", "anthony"
                , "antoine", "antoinette", "antoni", "antonio", "antony", "ariane", "ariel", "arielle", "aristide", "arié", "arlette", "armand", "armando", "armel", "armelle", "armin", "arnaud",
                "arnauld", "arnault", "arnold", "arthur", "arturo", "astrid", "aude", "audebert", "audrey", "augusta", "auguste", "augustin", "augusto", "aurore", "aurélia", "auréliane", "aurélie",
                "aurélien", "axel", "axelle", "aymar", "aymeric", "aïcha", "bahia", "baptist", "baptiste", "baptiste", "barbara", "bart", "barthélémy", "basile", "bastien", "baudoin", "benaïcha",
                "benjamin", "benjamine", "benoît-david", "benoît-Paul", "benoît", "bernadette", "bernard-eric", "bernard", "bernhard", "bertrand", "betty", "bianca", "bill", "björn", "blaise",
                "blanche", "blandine", "boris", "brahim", "brian-John", "brian", "brice", "brigitte", "bruce", "bruno", "béa", "béatrice", "béatrice", "bénédicte", "bérangère", "bérengère",
                "cameron", "camille-Pierre", "camille", "candice", "capucine", "carine", "carl", "carla", "carlo", "carlos", "carmen", "carmine", "caro", "caroline", "carol", "carole", "caroline",
                "carolyn", "catherine", "cathie", "cathy", "cendrine", "cerise", "cesare", "chantal", "charle", "charles", "charles-andré", "charles-emile", "charles-eric", "charles-Henri",
                "charles-Henry", "charles-Louis", "charles-Marie", "charles", "charlie", "charline", "charlotte", "charly", "charlène", "chiara", "chloé", "christel", "christelle", "christian",
                "christiane", "christina", "christine", "christo", "christophe", "christoph", "christophe", "christopher", "christèle", "chrystel", "cindy", "claire", "clara", "clarisse",
                "claude-Henri", "claude", "claudette", "claudia", "claudie", "claudine", "claudio", "claudius", "clotilde", "clémence", "clément", "colette", "colin", "concepcion", "constance",
                "constant", "constantin", "coralie", "corine", "corinne", "cynthia", "cyprien", "cyril", "cyrille", "cécil", "cécile", "cécilia", "cédric", "célia", "céline", "césar", "daisy",
                "dalida", "damien", "dan", "daniel-Henri", "daniel", "daniela", "danielle", "danièle", "danny", "dany", "daphné", "dario", "darlène", "darren", "david", "deborah", "delphine",
                "denis", "denise", "dennis", "denys", "denyse", "desmond", "diana", "diane-Karine", "diane", "didier", "diego", "dieter", "dietrich", "dieudonné", "dimitri", "diogo", "djamel",
                "djamila", "dolorès", "domingos", "dominique", "dominoco", "donald", "dorothy", "dorothée", "douglas", "dounia", "eddie", "eddy", "edgar", "edgard", "edmond", "edmonde", "edoardo",
                "edouard", "eduardo", "edward", "edwige", "edwin", "elena", "eliane", "elisabeth", "elizabeth", "eloïse", "elsa", "elvire", "eléonore", "emannuel", "emmanuel", "emannuele",
                "emmanuelle", "emannuelle", "emmanuelle", "emanuel", "emmanuel", "emanuelle", "emmanuelle", "emily", "emma", "emmanuel", "emmanuelle", "enrico", "enzo", "eric", "erick", "erik",
                "erika", "ernest", "ernst", "erwan", "erwann", "erwin", "estelle", "esther", "etiennette", "eudes", "eugenio", "eugène", "eugénie", "evangéline");

        mEmployeesMailList = new ArrayList<>();
        for (int j = 1; j <= 381; j++) {
            String mail = employeesList.get(j) + "@lamzone.com";
            mEmployeesMailList.add(mail);
        }

        return mEmployeesMailList;
    }

    public int getRandomImage(String room) {

        int[] img = {R.color.red1, R.color.violet, R.color.green, R.color.green2,
                R.color.yellow1, R.color.yellowgreen, R.color.blue, R.color.pink,
                R.color.violet2, R.color.orange};


        if (room.equals("Salle 1")) {
            mRandomColor = img[0];
        } else if (room.equals("Salle 2")) {
            mRandomColor = img[1];
        } else if (room.equals("Salle 3")) {
            mRandomColor = img[2];
        } else if (room.equals("Salle 4")) {
            mRandomColor = img[3];
        } else if (room.equals("Salle 5")) {
            mRandomColor = img[4];
        } else if (room.equals("Salle 6")) {
            mRandomColor = img[5];
        } else if (room.equals("Salle 7")) {
            mRandomColor = img[6];
        } else if (room.equals("Salle 8")) {
            mRandomColor = img[7];
        } else if (room.equals("Salle 9")) {
            mRandomColor = img[8];
        } else if (room.equals("Salle 10")) {
            mRandomColor = img[9];
        } else {
            mRandomColor = img[0];
        }
        return mRandomColor;
}
}
