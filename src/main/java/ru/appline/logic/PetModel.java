package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PetModel implements Serializable {

    private static final PetModel instance = new PetModel();

    private final Map<Integer, Pet> model;

    private PetModel() {
        model = new HashMap<Integer, Pet>();
    }

    public static PetModel getInstance() {
        return instance;
    }

    public void add(int id, Pet pet) {
        model.put(id, pet);
    }

    public Pet getFromList(int id) {
        return model.get(id);
    }

    public Map<Integer,Pet> getAllFromList() {
        return model;
    }
}
