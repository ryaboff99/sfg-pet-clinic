package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("PetType Map Service Test")
class PetTypeMapServiceTest {

    PetTypeMapService petTypeMapService;
    final Long PET_TYPE_ID = 1L;

    @BeforeEach
    void setUp() {
        petTypeMapService = new PetTypeMapService();
        petTypeMapService.save(PetType.builder().id(PET_TYPE_ID).build());
    }

    @Test
    @DisplayName("Service find all PetTypes")
    void serviceFindAllPetTypes() {
        Set<PetType> petTypeSetToTest = petTypeMapService.findAll();

        assertEquals(1, petTypeSetToTest.size());
    }

    @Test
    @DisplayName("Service find PetType by Id")
    void serviceFindPetTypeById() {
        PetType petTypeToTest = petTypeMapService.findById(PET_TYPE_ID);

        assertEquals(PET_TYPE_ID, petTypeToTest.getId());
    }

    @Test
    @DisplayName("Service save PetType with existing Id")
    void serviceSavePetTypeWithExistingId() {
        Long id = 2L;
        PetType petTypeToTest = petTypeMapService.save(PetType.builder().id(id).build());

        assertEquals(id, petTypeToTest.getId());
    }

    @Test
    @DisplayName("Service save PetType without Id")
    void serviceSavePetTypeWithoutId() {
        PetType petTypeToTest = petTypeMapService.save(PetType.builder().build());

        assertNotNull(petTypeToTest);
        assertNotNull(petTypeToTest.getId());
    }

    @Test
    @DisplayName("Service delete PetType")
    void serviceDeletePetType() {
        petTypeMapService.delete(petTypeMapService.findById(PET_TYPE_ID));

        assertEquals(0, petTypeMapService.findAll().size());
    }

    @Test
    @DisplayName("Service delete PetType by Id")
    void serviceDeletePetTypeById() {
        petTypeMapService.deleteById(PET_TYPE_ID);

        assertEquals(0, petTypeMapService.findAll().size());
    }
}