package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

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
    // This test has just been rewritten!!!!
    void testProcessCreationForm_ValidPath() throws Exception {
        // Given
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        // The ID is sent as Param, but will not be taken in account.
        // Also see the comment in line77
        multiValueMap.add("id","4");
        multiValueMap.add("firstName","Owner");
        multiValueMap.add("lastName","Mock");
        multiValueMap.add("address","Rue des manguiers");
        multiValueMap.add("city","Douala");
        multiValueMap.add("telephone","1234567");

        mockMvc.perform(post("/owners/new")
                    .params(multiValueMap))
                .andExpect(status().is3xxRedirection())
                // Doesn't exists in this case
//                .andExpect(model().attributeHasNoErrors("firstName,lastName,address,city,telephone"))
                // we can not submit an id in the form the way the Controller is implemented!!!!
                .andExpect(view().name("redirect:/owners/null"))
                .andDo(print());
    }

    @Test
    // the new Test
    void testProcessCreationForm_InvalidPath() throws Exception {
        mockMvc.perform(post("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("owner","lastName","NotEmpty"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM))
                .andDo(print());
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