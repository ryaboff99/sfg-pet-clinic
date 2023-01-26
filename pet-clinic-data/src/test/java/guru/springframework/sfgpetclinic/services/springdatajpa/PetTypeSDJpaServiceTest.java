package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
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
@DisplayName("PetType Spring Data JPA Service Test")
class PetTypeSDJpaServiceTest {

    final Long PET_TYPE_ID = 1L;
    final PetType PET_TYPE = PetType.builder().id(PET_TYPE_ID).build();
    final Set<PetType> PET_TYPE_SET = Set.of(PET_TYPE);

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    PetTypeSDJpaService petTypeSDJpaService;

    @Test
    @DisplayName("Service find all PetTypes")
    void serviceFindAllPetTypes() {
        when(petTypeRepository.findAll()).thenReturn(PET_TYPE_SET);

        Set<PetType> petTypeSetToTest = petTypeSDJpaService.findAll();

        assertNotNull(petTypeSetToTest);
        assertEquals(1, petTypeSetToTest.size());

        verify(petTypeRepository).findAll();
    }

    @Test
    @DisplayName("Service find PetType by Id")
    void serviceFindPetTypeById() {
        when(petTypeRepository.findById(PET_TYPE_ID)).thenReturn(Optional.of(PET_TYPE));

        PetType petTypeToTest = petTypeSDJpaService.findById(PET_TYPE_ID);

        assertNotNull(petTypeToTest);
        assertEquals(PET_TYPE_ID, petTypeToTest.getId());

        verify(petTypeRepository).findById(PET_TYPE_ID);
    }

    @Test
    @DisplayName("Service do not find PetType by Id")
    void serviceDoNotFindPetTypeById() {
        when(petTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        PetType petTypeToTest = petTypeSDJpaService.findById(PET_TYPE_ID);

        assertNull(petTypeToTest);

        verify(petTypeRepository).findById(PET_TYPE_ID);
    }

    @Test
    @DisplayName("Service save PetType")
    void serviceSavePetType() {
        Long id = 2L;
        PetType savedPetType = PetType.builder().id(id).build();

        when(petTypeRepository.save(savedPetType)).thenReturn(savedPetType);

        PetType petTypeToTest = petTypeSDJpaService.save(savedPetType);

        assertEquals(id, petTypeToTest.getId());

        verify(petTypeRepository).save(savedPetType);
    }

    @Test
    @DisplayName("Service delete PetType")
    void serviceDeletePetType() {
        petTypeSDJpaService.delete(PET_TYPE);

        verify(petTypeRepository).delete(PET_TYPE);
    }

    @Test
    @DisplayName("Service delete PetType by Id")
    void serviceDeletePetTypeById() {
        petTypeSDJpaService.deleteById(PET_TYPE_ID);

        verify(petTypeRepository).deleteById(PET_TYPE_ID);
    }
}