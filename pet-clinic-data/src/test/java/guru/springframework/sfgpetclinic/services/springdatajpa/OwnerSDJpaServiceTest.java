package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
    void findAll() {
        when(ownerRepository.findAll()).thenReturn(OWNER_SET);

        Set<Owner> ownerSet = ownerSDJpaService.findAll();

        assertNotNull(ownerSet);
        assertEquals(1, ownerSet.size());

        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(OWNER_ID)).thenReturn(Optional.of(OWNER));

        Owner owner = ownerSDJpaService.findById(OWNER_ID);

        assertNotNull(owner);
        assertEquals(OWNER_LAST_NAME, owner.getLastName());

        verify(ownerRepository, times(1)).findById(OWNER_ID);
    }

    @Test
    void FindByIdNotFound() {
        Long id = 2L;
        when(ownerRepository.findById(id)).thenReturn(Optional.empty());

        Owner owner = ownerSDJpaService.findById(id);

        assertNull(owner);

        verify(ownerRepository, times(1)).findById(id);
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner savedOwner = Owner.builder().id(id).build();

        when(ownerRepository.save(savedOwner)).thenReturn(savedOwner);

        Owner ownerToCheck = ownerSDJpaService.save(savedOwner);

        assertEquals(id, ownerToCheck.getId());

        verify(ownerRepository, times(1)).save(savedOwner);
    }

    @Test
    void saveNoId() {

        Owner savedOwner = Owner.builder().build();

        when(ownerRepository.save(savedOwner)).thenReturn(savedOwner);

        Owner ownerToCheck = ownerSDJpaService.save(savedOwner);

        assertNotNull(ownerToCheck);

        verify(ownerRepository, times(1)).save(savedOwner);
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(OWNER);

        verify(ownerRepository, times(1)).delete(OWNER);
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(OWNER_ID);

        verify(ownerRepository, times(1)).deleteById(OWNER_ID);
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(OWNER_LAST_NAME)).thenReturn(OWNER);

        Owner ownerToCheck = ownerSDJpaService.findByLastName(OWNER_LAST_NAME);

        assertEquals(OWNER_LAST_NAME, ownerToCheck.getLastName());

        verify(ownerRepository, times(1)).findByLastName(OWNER_LAST_NAME);
    }
}