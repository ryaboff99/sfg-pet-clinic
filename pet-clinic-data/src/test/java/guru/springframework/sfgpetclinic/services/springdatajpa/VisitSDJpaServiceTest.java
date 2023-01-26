package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Visit Spring Data JPA Service Test")
class VisitSDJpaServiceTest {

    final Long VISIT_ID = 1L;
    final Visit VISIT = Visit.builder().id(VISIT_ID).build();
    final Set<Visit> VISIT_SET = Set.of(VISIT);

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    @Test
    @DisplayName("Service find all Visits")
    void serviceFindAllVisits() {
        when(visitRepository.findAll()).thenReturn(VISIT_SET);

        Set<Visit> visitSetToTest = visitSDJpaService.findAll();

        assertNotNull(visitSetToTest);
        assertEquals(1, visitSetToTest.size());

        verify(visitRepository).findAll();
    }

    @Test
    @DisplayName("Service find Visit by Id")
    void serviceFindVisitById() {
        when(visitRepository.findById(VISIT_ID)).thenReturn(Optional.of(VISIT));

        Visit visitToTest = visitSDJpaService.findById(VISIT_ID);

        assertNotNull(visitToTest);
        assertEquals(VISIT_ID, visitToTest.getId());

        verify(visitRepository).findById(VISIT_ID);
    }

    @Test
    @DisplayName("Service do not find Visit by Id")
    void serviceDoNotFindVisitById() {
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());

        Visit visitToTest = visitSDJpaService.findById(VISIT_ID);

        assertNull(visitToTest);

        verify(visitRepository).findById(VISIT_ID);
    }

    @Test
    @DisplayName("Service save Visit")
    void serviceSaveVisit() {
        Long id = 2L;
        Visit savedVisit = Visit.builder().id(id).build();

        when(visitRepository.save(savedVisit)).thenReturn(savedVisit);

        Visit visitToTest = visitSDJpaService.save(savedVisit);

        assertEquals(id, visitToTest.getId());

        verify(visitRepository).save(savedVisit);
    }

    @Test
    @DisplayName("Service delete Visit")
    void serviceDeleteVisit() {
        visitSDJpaService.delete(VISIT);

        verify(visitRepository).delete(VISIT);
    }

    @Test
    @DisplayName("Service delete Visit by Id")
    void serviceDeleteVisitById() {
        visitSDJpaService.deleteById(VISIT_ID);

        verify(visitRepository).deleteById(VISIT_ID);
    }
}