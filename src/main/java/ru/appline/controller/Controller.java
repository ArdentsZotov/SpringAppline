package ru.appline.controller;

import javafx.beans.binding.IntegerBinding;
import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final PetModel model = PetModel.getInstance();

    private static final AtomicInteger counter = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json")
    public String creatPet(@RequestBody Pet pet) {
        model.add(counter.getAndIncrement(), pet);
        if (model.getAllFromList().size() == 1) return "You created first home pet!";
        else return "Home pet created";
    }

    @GetMapping(value = "/getAllPets", produces = "application/json")
    public Map<Integer, Pet> getAllPets() {
        return model.getAllFromList();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return model.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public String deletePet(@RequestBody Map<String, Integer> id) {
        int realId = id.get("id");
        if (model.getAllFromList().containsKey(realId)) {
            model.getAllFromList().remove(realId);
            return "Живность удалена из списка";
        } else return "Живности c ID=" + realId + " нет в списке";
    }

    @PutMapping(value = "/putPet", consumes = "application/json")
    public String putPet(@RequestBody Map<String, String> map) {
        int realId = Integer.parseInt(map.get("id"));

        if (model.getAllFromList().containsKey(realId)) {
            String newName = map.get("name");
            String newType = map.get("type");
            int newAge = Integer.parseInt(map.get("age"));

            Pet pet = model.getFromList(realId);

            pet.setName(newName);
            pet.setType(newType);
            pet.setAge(newAge);

            model.add(realId, pet);
            return "Успешно изменено";
        } else return "Изменения не были приняты";
    }
}
