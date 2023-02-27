package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Owner Map Service Test")
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long OWNER_ID = 1L;
    final String LAST_NAME = "Mike";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build());
    }

    @Test
    @DisplayName("Service find all Owners")
    void serviceFindAllOwners() {
        Set<Owner> ownerSetToTest = ownerMapService.findAll();

        assertEquals(1, ownerSetToTest.size());
    }

    @Test
    @DisplayName("Service find Owner by Id")
    void serviceFindOwnerById() {
        Owner ownerToTest = ownerMapService.findById(OWNER_ID);

        assertEquals(OWNER_ID, ownerToTest.getId());
    }

    @Test
    @DisplayName("Service save Owner with existing Id")
    void serviceSaveOwnerWithExistingId() {
        Long id = 2L;
        Owner ownerToTest = ownerMapService.save(Owner.builder().id(id).build());

        assertEquals(id, ownerToTest.getId());
    }

    @Test
    @DisplayName("Service save Owner without Id")
    void serviceSaveOwnerWithoutId() {
        Owner ownerToTest = ownerMapService.save(Owner.builder().build());

        assertNotNull(ownerToTest);
        assertNotNull(ownerToTest.getId());
    }

    @Test
    @DisplayName("Service delete Owner")
    void serviceDeleteOwner() {
        ownerMapService.delete(ownerMapService.findById(OWNER_ID));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    @DisplayName("Service delete Owner by Id")
    void serviceDeleteOwnerById() {
        ownerMapService.deleteById(OWNER_ID);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    @DisplayName("Service find Owner by last name")
    void serviceFindOwnerByLastName() {
        Owner ownerToTest = ownerMapService.findByLastName(LAST_NAME);

        assertNotNull(ownerToTest);
        assertEquals(OWNER_ID, ownerToTest.getId());
    }

    @Test
    @DisplayName("Service do not find Owner by last name")
    void serviceDoNotFindOwnerByLastName() {
        Owner ownerToTest = ownerMapService.findByLastName("foo");

        assertNull(ownerToTest);
    }

    @Test
    void serviceFindAllOwnersByLastNameLike() {
        ownerMapService.save(Owner.builder().id(2L).lastName("Ger").build());

        List<Owner> filteredList = ownerMapService.findAllByLastNameLike("Ger");

        assertEquals(1, filteredList.size());
    }
}