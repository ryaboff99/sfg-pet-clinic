package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialityRepository;
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
@DisplayName("Speciality Spring Data JPA Service Test")
class SpecialitySDJpaServiceTest {

    final Long SPECIALITY_ID = 1L;
    final Speciality SPECIALITY = Speciality.builder().id(SPECIALITY_ID).build();
    final Set<Speciality> SPECIALITY_SET = Set.of(SPECIALITY);

    @Mock
    SpecialityRepository specialityRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    @DisplayName("Service find all Specialities")
    void serviceFindAllSpecialities() {
        when(specialityRepository.findAll()).thenReturn(SPECIALITY_SET);

        Set<Speciality> specialitySetToTest = specialitySDJpaService.findAll();

        assertNotNull(specialitySetToTest);
        assertEquals(1, specialitySetToTest.size());

        verify(specialityRepository).findAll();
    }

    @Test
    @DisplayName("Service find Speciality by Id")
    void serviceFindSpecialityById() {
        when(specialityRepository.findById(SPECIALITY_ID)).thenReturn(Optional.of(SPECIALITY));

        Speciality specialityToTest = specialitySDJpaService.findById(SPECIALITY_ID);

        assertNotNull(specialityToTest);
        assertEquals(SPECIALITY_ID, specialityToTest.getId());

        verify(specialityRepository).findById(SPECIALITY_ID);
    }

    @Test
    @DisplayName("Service do not find Speciality by Id")
    void serviceDoNotFindSpecialityById() {
        when(specialityRepository.findById(anyLong())).thenReturn(Optional.empty());

        Speciality specialityToTest = specialitySDJpaService.findById(SPECIALITY_ID);

        assertNull(specialityToTest);

        verify(specialityRepository).findById(SPECIALITY_ID);
    }

    @Test
    @DisplayName("Service save Speciality")
    void serviceSaveSpeciality() {
        Long id = 2L;
        Speciality savedSpeciality = Speciality.builder().id(id).build();

        when(specialityRepository.save(savedSpeciality)).thenReturn(savedSpeciality);

        Speciality specialityToTest = specialitySDJpaService.save(savedSpeciality);

        assertEquals(id, specialityToTest.getId());

        verify(specialityRepository).save(savedSpeciality);
    }

    @Test
    @DisplayName("Service delete Speciality")
    void serviceDeleteSpeciality() {
        specialitySDJpaService.delete(SPECIALITY);

        verify(specialityRepository).delete(SPECIALITY);
    }

    @Test
    @DisplayName("Service delete Speciality by Id")
    void serviceDeleteSpecialityById() {
        specialitySDJpaService.deleteById(SPECIALITY_ID);

        verify(specialityRepository).deleteById(SPECIALITY_ID);
    }
}