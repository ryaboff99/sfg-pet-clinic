package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Visit Map Service Test")
class VisitMapServiceTest {

    VisitMapService visitMapService;
    final Long VISIT_ID = 1L;

    @BeforeEach
    void setUp() {
        visitMapService = new VisitMapService();
        visitMapService.save(Visit.builder()
                .id(VISIT_ID)
                .pet(Pet.builder().id(1L).owner(Owner.builder().id(1L).build()).build())
                .build());
    }

    @Test
    @DisplayName("Service find all Visits")
    void serviceFindAllVisits() {
        Set<Visit> visitSetToTest = visitMapService.findAll();

        assertEquals(1, visitSetToTest.size());
    }

    @Test
    @DisplayName("Service find Visit by Id")
    void serviceFindVisitById() {
        Visit visitToTest = visitMapService.findById(VISIT_ID);

        assertEquals(VISIT_ID, visitToTest.getId());
    }

    @Test
    @DisplayName("Service save Visit with existing Id")
    void serviceSaveVisitWithExistingId() {
        Long id = 2L;
        Visit visitToTest = visitMapService.save(Visit.builder()
                .id(id)
                .pet(Pet.builder().id(id).owner(Owner.builder().id(id).build()).build())
                .build());

        assertEquals(id, visitToTest.getId());
    }

    @Test
    @DisplayName("Service save Visit without Id")
    void serviceSaveVisitWithoutId() {
        Long id = 2L;
        Visit visitToTest = visitMapService.save(Visit.builder()
                .pet(Pet.builder().id(id).owner(Owner.builder().id(2L).build()).build())
                .build());

        assertNotNull(visitToTest);
        assertNotNull(visitToTest.getId());
    }

    @Test
    @DisplayName("Service delete Visit")
    void serviceDeleteVisit() {
        visitMapService.delete(visitMapService.findById(VISIT_ID));

        assertEquals(0, visitMapService.findAll().size());
    }

    @Test
    @DisplayName("Service delete Visit by Id")
    void serviceDeleteVisitById() {
        visitMapService.deleteById(VISIT_ID);

        assertEquals(0, visitMapService.findAll().size());
    }
}