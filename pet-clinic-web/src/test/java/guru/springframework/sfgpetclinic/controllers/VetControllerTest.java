package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
@DisplayName("Vet Controller Test")
class VetControllerTest {

    @Mock
    VetService vetService;

    @InjectMocks
    VetController vetController;

    Set<Vet> vetSet;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        vetSet = Set.of(Vet.builder().id(1L).build(), Vet.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    @DisplayName("Controller list Vets")
    void controllerListVets() throws Exception {
        when(vetService.findAll()).thenReturn(vetSet);

        mockMvc.perform(MockMvcRequestBuilders.get("/vets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("vets/index"))
                .andExpect(model().attribute("vets", hasSize(2)));
    }

    @Test
    @DisplayName("Controller list Vets by vets.html")
    void controllerListVetsByHtml() throws Exception {
        when(vetService.findAll()).thenReturn(vetSet);

        mockMvc.perform(MockMvcRequestBuilders.get("/vets.html"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("vets/index"))
                .andExpect(model().attribute("vets", hasSize(2)));
    }

    @Test
    @DisplayName("Controller list Vets by vets/index")
    void controllerListVetsByIndex() throws Exception {
        when(vetService.findAll()).thenReturn(vetSet);

        mockMvc.perform(MockMvcRequestBuilders.get("/vets/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("vets/index"))
                .andExpect(model().attribute("vets", hasSize(2)));
    }

    @Test
    @DisplayName("Controller list Vets by vets/index.html")
    void controllerListVetsByIndexHtml() throws Exception {
        when(vetService.findAll()).thenReturn(vetSet);

        mockMvc.perform(MockMvcRequestBuilders.get("/vets/index.html"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("vets/index"))
                .andExpect(model().attribute("vets", hasSize(2)));
    }
}