package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    Map<String, Object> model;
    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController controller;

    @BeforeEach
    void setUp(){
        Vet vet = new Vet();
        List<Vet> vetsList = new ArrayList<>();
        vetsList.add(vet);
        given(clinicService.findVets()).willReturn(vetsList);
    }

    @Test
    void showVetList() {

        //when
        String res = controller.showVetList(model);

        //then
        then(model).should(times(1)).put(anyString(), any());
        then(clinicService).should(times(1)).findVets();
        assertEquals("vets/vetList", res);
    }

    @Test
    void showResourcesVetList() {

        //when
        Vets res = controller.showResourcesVetList();

        //then
        then(clinicService).should(times(1)).findVets();
        assertEquals(res.getVetList().size(), 1);
    }
}