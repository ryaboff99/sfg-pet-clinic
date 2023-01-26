package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Vet Map Service Test")
class VetMapServiceTest {

    VetMapService vetMapService;
    final Long VET_ID = 1L;

    @BeforeEach
    void setUp() {
        vetMapService = new VetMapService(new SpecialityMapService());
        vetMapService.save(Vet.builder().id(VET_ID).specialities(Set.of(new Speciality())).build());
    }

    @Test
    @DisplayName("Service find all Vets")
    void serviceFindAllVets() {
        Set<Vet> vetSetToTest = vetMapService.findAll();

        assertEquals(1, vetSetToTest.size());
    }

    @Test
    @DisplayName("Service find Vet by Id")
    void serviceFindVetById() {
        Vet vetToTest = vetMapService.findById(VET_ID);

        assertEquals(VET_ID, vetToTest.getId());
    }

    @Test
    @DisplayName("Service save Vet with existing Id")
    void serviceSaveVetWithExistingId() {
        Long id = 2L;
        Set<Speciality> specialitySet = Set.of(new Speciality());
        Vet vetToTest = vetMapService.save(Vet.builder().specialities(specialitySet).id(id).build());

        assertEquals(id, vetToTest.getId());
    }

    @Test
    @DisplayName("Service save Vet without Id")
    void serviceSaveVetWithoutId() {
        Set<Speciality> specialitySet = Set.of(new Speciality());
        Vet vetToTest = vetMapService.save(Vet.builder().specialities(specialitySet).build());

        assertNotNull(vetToTest);
        assertNotNull(vetToTest.getId());
    }

    @Test
    @DisplayName("Service delete Vet")
    void serviceDeleteVet() {
        vetMapService.delete(vetMapService.findById(VET_ID));

        assertEquals(0, vetMapService.findAll().size());
    }

    @Test
    @DisplayName("Service delete Vet by Id")
    void serviceDeleteVetById() {
        vetMapService.deleteById(VET_ID);

        assertEquals(0, vetMapService.findAll().size());
    }
}