package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Owner Spring Data JPA Service Test")
class OwnerSDJpaServiceTest {

    final Long OWNER_ID = 1L;
    final String OWNER_LAST_NAME = "Victoria";
    final Owner OWNER = Owner.builder().id(OWNER_ID).lastName(OWNER_LAST_NAME).build();
    final Set<Owner> OWNER_SET = Set.of(OWNER);

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    @Test
    @DisplayName("Service finds all Owners")
    void serviceFindsAllOwners() {
        when(ownerRepository.findAll()).thenReturn(OWNER_SET);

        Set<Owner> ownerSetToTest = ownerSDJpaService.findAll();

        assertNotNull(ownerSetToTest);
        assertEquals(1, ownerSetToTest.size());

        verify(ownerRepository).findAll();
    }

    @Test
    @DisplayName("Service find Owner by Id")
    void serviceFindOwnerById() {
        when(ownerRepository.findById(OWNER_ID)).thenReturn(Optional.of(OWNER));

        Owner ownerToTest = ownerSDJpaService.findById(OWNER_ID);

        assertNotNull(ownerToTest);
        assertEquals(OWNER_ID, ownerToTest.getId());

        verify(ownerRepository).findById(OWNER_ID);
    }

    @Test
    @DisplayName("Service do not find Owner by Id")
    void serviceDoNotFindOwnerById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner ownerToTest = ownerSDJpaService.findById(OWNER_ID);

        assertNull(ownerToTest);

        verify(ownerRepository).findById(OWNER_ID);
    }

    @Test
    @DisplayName("Service save Owner")
    void serviceSaveOwner() {
        Long id = 2L;
        Owner savedOwner = Owner.builder().id(id).build();

        when(ownerRepository.save(savedOwner)).thenReturn(savedOwner);

        Owner ownerToTest = ownerSDJpaService.save(savedOwner);

        assertEquals(id, ownerToTest.getId());

        verify(ownerRepository).save(savedOwner);
    }

    @Test
    @DisplayName("Service delete Owner")
    void serviceDeleteOwner() {
        ownerSDJpaService.delete(OWNER);

        verify(ownerRepository).delete(OWNER);
    }

    @Test
    @DisplayName("Service delete Owner by Id")
    void serviceDeleteOwnerById() {
        ownerSDJpaService.deleteById(OWNER_ID);

        verify(ownerRepository).deleteById(OWNER_ID);
    }

    @Test
    @DisplayName("Service find Owner by last name")
    void serviceFindOwnerByLastName() {
        when(ownerRepository.findByLastName(OWNER_LAST_NAME)).thenReturn(OWNER);

        Owner ownerToCheck = ownerSDJpaService.findByLastName(OWNER_LAST_NAME);

        assertEquals(OWNER_LAST_NAME, ownerToCheck.getLastName());

        verify(ownerRepository).findByLastName(OWNER_LAST_NAME);
    }

    @Test
    void findAllByLastNameLike() {
        List<Owner> ownerList = List.of(Owner.builder().id(1L).build(),
                Owner.builder().id(2L).build());

        when(ownerRepository.findAllByLastNameLike(OWNER_LAST_NAME)).thenReturn(ownerList);

        List<Owner> ownerListToCheck = ownerSDJpaService.findAllByLastNameLike(OWNER_LAST_NAME);

        assertEquals(2, ownerListToCheck.size());

        verify(ownerRepository, times(1)).findAllByLastNameLike(OWNER_LAST_NAME);
    }
}