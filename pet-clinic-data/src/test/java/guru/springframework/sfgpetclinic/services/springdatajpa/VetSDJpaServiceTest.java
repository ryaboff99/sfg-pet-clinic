package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
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
@DisplayName("Vet Spring Data JPA Service Test")
class VetSDJpaServiceTest {

    final Long VET_ID = 1L;
    final Vet VET = Vet.builder().id(VET_ID).build();
    final Set<Vet> VET_SET = Set.of(VET);

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetSDJpaService vetSDJpaService;

    @Test
    @DisplayName("Service find all Vets")
    void serviceFindAllVets() {
        when(vetRepository.findAll()).thenReturn(VET_SET);

        Set<Vet> vetSetToTest = vetSDJpaService.findAll();

        assertNotNull(vetSetToTest);
        assertEquals(1, vetSetToTest.size());

        verify(vetRepository).findAll();
    }

    @Test
    @DisplayName("Service find Vet by Id")
    void serviceFindVetById() {
        when(vetRepository.findById(VET_ID)).thenReturn(Optional.of(VET));

        Vet vetToTest = vetSDJpaService.findById(VET_ID);

        assertNotNull(vetToTest);
        assertEquals(VET_ID, vetToTest.getId());

        verify(vetRepository).findById(VET_ID);
    }

    @Test
    @DisplayName("Service do not find Vet by Id")
    void serviceDoNotFindVetById() {
        when(vetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Vet vetToTest = vetSDJpaService.findById(VET_ID);

        assertNull(vetToTest);

        verify(vetRepository).findById(VET_ID);
    }

    @Test
    @DisplayName("Service save Vet")
    void serviceSaveVet() {
        Long id = 2L;
        Vet savedVet = Vet.builder().id(id).build();

        when(vetRepository.save(savedVet)).thenReturn(savedVet);

        Vet vetToTest = vetSDJpaService.save(savedVet);

        assertEquals(id, vetToTest.getId());

        verify(vetRepository).save(savedVet);
    }

    @Test
    @DisplayName("Service delete Vet")
    void serviceDeleteVet() {
        vetSDJpaService.delete(VET);

        verify(vetRepository).delete(VET);
    }

    @Test
    @DisplayName("Service delete Vet by Id")
    void serviceDeleteVetById() {
        vetSDJpaService.deleteById(VET_ID);

        verify(vetRepository).deleteById(VET_ID);
    }
}