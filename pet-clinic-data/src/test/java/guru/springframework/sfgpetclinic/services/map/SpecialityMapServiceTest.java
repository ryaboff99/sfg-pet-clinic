package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Speciality Map Service Test")
class SpecialityMapServiceTest {

    SpecialityMapService specialityMapService;
    final Long SPECIALITY_ID = 1L;

    @BeforeEach
    void setUp() {
        specialityMapService = new SpecialityMapService();
        specialityMapService.save(Speciality.builder().id(SPECIALITY_ID).build());
    }

    @Test
    @DisplayName("Service find all Specialities")
    void serviceFindAllSpecialities() {
        Set<Speciality> specialitySetToTest = specialityMapService.findAll();

        assertEquals(1, specialitySetToTest.size());
    }

    @Test
    @DisplayName("Service find Speciality by Id")
    void serviceFindSpecialityById() {
        Speciality specialityToTest = specialityMapService.findById(SPECIALITY_ID);

        assertEquals(SPECIALITY_ID, specialityToTest.getId());
    }

    @Test
    @DisplayName("Service save Speciality with existing Id")
    void serviceSaveSpecialityWithExistingId() {
        Long id = 2L;
        Speciality specialityToTest = specialityMapService.save(Speciality.builder().id(id).build());

        assertEquals(id, specialityToTest.getId());
    }

    @Test
    @DisplayName("Service save Speciality without Id")
    void serviceSaveSpecialityWithoutId() {
        Speciality specialityToTest = specialityMapService.save(Speciality.builder().build());

        assertNotNull(specialityToTest);
        assertNotNull(specialityToTest.getId());
    }

    @Test
    @DisplayName("Service delete Speciality")
    void serviceDeleteSpeciality() {
        specialityMapService.delete(specialityMapService.findById(SPECIALITY_ID));

        assertEquals(0, specialityMapService.findAll().size());
    }

    @Test
    @DisplayName("Service delete Speciality by Id")
    void serviceDeleteSpecialityById() {
        specialityMapService.deleteById(SPECIALITY_ID);

        assertEquals(0, specialityMapService.findAll().size());
    }
}