package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeMapServiceTest {

    @Test
    void testPetType() {
        PetType petType1 = new PetType();
        petType1.setName("petType1");
        PetType petType2 = new PetType();
        petType2.setName("petType2");
        PetType petType3 = new PetType();
        petType3.setName("petType3");
        PetType petType4 = new PetType();
        petType4.setName("petType4");

        PetTypeMapService petTypeMapService1 = new PetTypeMapService();
        petTypeMapService1.save(petType1);
        petTypeMapService1.save(petType2);


        PetTypeMapService petTypeMapService2 = new PetTypeMapService();
        petTypeMapService2.save(petType3);
        petTypeMapService2.save(petType4);

        System.out.println("111");
        petTypeMapService1.findAll().stream().forEach(System.out::println);
        System.out.println("222");
        petTypeMapService2.findAll().stream().forEach(System.out::println);
    }
}