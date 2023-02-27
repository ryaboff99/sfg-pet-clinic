package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
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

import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
@DisplayName("Owner Controller Test")
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    Set<Owner> ownerSet;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ownerSet = Set.of(Owner.builder().id(1L).build(), Owner.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

//    @Test
//    @DisplayName("Controller list Owners")
//    void controllerListOwners() throws Exception {
//        when(ownerService.findAll()).thenReturn(ownerSet);
//
//        mockMvc.perform(MockMvcRequestBuilder s.get("/owners"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("owners/index"))
//                .andExpect(model().attribute("owners", hasSize(2)));
//    }

//    @Test
//    @DisplayName("Controller list Owners by index")
//    void controllerListOwnersByIndex() throws Exception {
//        when(ownerService.findAll()).thenReturn(ownerSet);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/owners/index"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("owners/index"))
//                .andExpect(model().attribute("owners", hasSize(2)));
//    }

//    @Test
//    @DisplayName("Controller list Owners by index.html")
//    void listOwnersByIndexHtml() throws Exception {
//        when(ownerService.findAll()).thenReturn(ownerSet);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/owners/index.html"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("owners/index"))
//                .andExpect(model().attribute("owners", hasSize(2)));
//    }

    @Test
    @DisplayName("Controller find Owners")
    void controllerFindOwners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFromReturnMany()  throws  Exception {
        when(ownerService.findAllByLastNameLike(anyString())).
                thenReturn(Arrays.asList(Owner.builder().id(1L).build(),
                Owner.builder().id(2L).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).
                thenReturn(Arrays.asList(Owner.builder().id(1L).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
    }

    @Test
    void displayOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }
}