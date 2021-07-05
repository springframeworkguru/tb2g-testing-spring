package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    Owner ownerMock, ownerMock1;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();

        // Given
        ownerMock = new Owner();
        ownerMock.setId(4);
        ownerMock.setFirstName("OwnerMock");
        ownerMock.setLastName("");

        // Given
        ownerMock1 = new Owner();
        ownerMock1.setId(5);
        ownerMock1.setFirstName("OwnerMock1");
        ownerMock1.setLastName("Mock1");

    }

    @Test
    void testFindByNameNotFound() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "Don't find ME!"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testFindByName_LastNameNull_OneOwner_Found() throws Exception {
        // IntelliJ warns the use of asList() with one element!!!!
        // List<Owner> ownersMock = new ArrayList<>(Arrays.asList(ownerMock));

        // Given
        List<Owner> ownersMock = Collections.singletonList(ownerMock);

        when(clinicService.findOwnerByLastName(ownerMock.getLastName())).thenReturn(ownersMock);

        // When, Then
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/"+ownerMock.getId()));
    }

    @Test
    void testFindByName_LastNameNull_MultOwners_Found() throws Exception {
        // Given
        List<Owner> ownersMock = new ArrayList<>(Arrays.asList(ownerMock,ownerMock1));

        when(clinicService.findOwnerByLastName(ownerMock.getLastName())).thenReturn(ownersMock);

        // When, Then
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"))
                .andExpect(view().name("owners/ownersList"));
    }

    @Test
    void initCreationFormTest() throws Exception {
        mockMvc.perform(get("/owners/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("owner"))
            .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }
}