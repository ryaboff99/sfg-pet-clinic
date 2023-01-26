package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
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
@DisplayName("Pet Spring Data JPA Service Test")
class PetSDJpaServiceTest {

    final Long PET_ID = 1L;
    final Pet PET = Pet.builder().id(PET_ID).build();
    final Set<Pet> PET_SET = Set.of(PET);

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetSDJpaService petSDJpaService;

    @Test
    @DisplayName("Service find all Pets")
    void serviceFindAllPets() {
        when(petRepository.findAll()).thenReturn(PET_SET);

        Set<Pet> petSetToTest = petSDJpaService.findAll();

        assertNotNull(petSetToTest);
        assertEquals(1, petSetToTest.size());

        verify(petRepository).findAll();
    }

    @Test
    @DisplayName("Service find Pet by Id")
    void serviceFindPetById() {
        when(petRepository.findById(PET_ID)).thenReturn(Optional.of(PET));

        Pet petToTest = petSDJpaService.findById(PET_ID);

        assertNotNull(petToTest);
        assertEquals(PET_ID, petToTest.getId());

        verify(petRepository).findById(PET_ID);
    }

    @Test
    @DisplayName("Service do not find Pet by Id")
    void serviceDoNotFindPetById() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        Pet petToTest = petSDJpaService.findById(PET_ID);

        assertNull(petToTest);

        verify(petRepository).findById(PET_ID);
    }

    @Test
    @DisplayName("Service save Pet")
    void serviceSavePet() {
        Long id = 2L;
        Pet savedPet = Pet.builder().id(id).build();

        when(petRepository.save(savedPet)).thenReturn(savedPet);

        Pet petToTest = petSDJpaService.save(savedPet);

        assertEquals(id, petToTest.getId());

        verify(petRepository).save(savedPet);
    }

    @Test
    @DisplayName("Service delete Pet")
    void serviceDeletePet() {
        petSDJpaService.delete(PET);

        verify(petRepository).delete(PET);
    }

    @Test
    @DisplayName("Service delete Pet by Id")
    void serviceDeletePetById() {
        petSDJpaService.deleteById(PET_ID);

        verify(petRepository).deleteById(PET_ID);
    }
}