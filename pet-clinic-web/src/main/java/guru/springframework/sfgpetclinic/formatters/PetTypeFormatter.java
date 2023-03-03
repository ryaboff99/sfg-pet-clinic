package guru.springframework.sfgpetclinic.formatters;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Collection<PetType> findPetTypes = petTypeService.findAll();    // takes all PetTypes from db

        for (PetType type : findPetTypes) { // iterate over all petTypes
            if (type.getName().equals(text)) {  // and pick up petType that passed to the method
                return type;
            }
        }

        throw new ParseException("type not found: " + text, 0);

//        return petTypeService.findAll().stream()
//                .filter(petType -> petType.getName().equals(text)).findAny()
//                .orElseThrow(() -> new ParseException("type not found: " + text, 0));
    }
}