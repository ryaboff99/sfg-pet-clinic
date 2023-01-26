package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Pet Map Service Test")
class PetMapServiceTest {

    PetMapService petMapService;
    final Long PET_ID = 1L;

    @BeforeEach
    void setUp() {
        petMapService = new PetMapService();
        petMapService.save(Pet.builder().id(PET_ID).build());
    }

    @Test
    @DisplayName("Service find all Pets")
    void serviceFindAllPets() {
        Set<Pet> petSetToTest = petMapService.findAll();

        assertEquals(1, petSetToTest.size());
    }

    @Test
    @DisplayName("Service find Pet by Id")
    void serviceFindPetById() {
        Pet petToTest = petMapService.findById(PET_ID);

        assertEquals(PET_ID, petToTest.getId());
    }

    @Test
    @DisplayName("Service save Pet with existing Id")
    void serviceSavePetWithExistingId() {
        Long id = 2L;
        Pet petToTest = petMapService.save(Pet.builder().id(id).build());

        assertEquals(id, petToTest.getId());
    }

    @Test
    @DisplayName("Service save Pet without Id")
    void serviceSavePetWithoutId() {
        Pet petToTest = petMapService.save(Pet.builder().build());

        assertNotNull(petToTest);
        assertNotNull(petToTest.getId());
    }

    @Test
    @DisplayName("Service delete Pet")
    void serviceDeletePet() {
        petMapService.delete(petMapService.findById(PET_ID));

        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    @DisplayName("Service delete Pet by Id")
    void serviceDeletePetById() {
        petMapService.deleteById(PET_ID);

        assertEquals(0, petMapService.findAll().size());
    }
}