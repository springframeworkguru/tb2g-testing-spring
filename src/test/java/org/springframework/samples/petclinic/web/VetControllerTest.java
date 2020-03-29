package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class VetControllerTest {

	@Mock
	private ClinicService clinicService;

	@InjectMocks
	private VetController vetController;

	private Map<String, Object> model = new HashMap<>();

	private Vet jimVet = new Vet();

	private Vet maryVet = new Vet();

	private Collection<Vet> expectedVets = Lists.newArrayList();

	@Before
	public void setUp() {
		jimVet.setFirstName("Jim");
		jimVet.setLastName("Bob");
		maryVet.setFirstName("Mary");
		maryVet.setLastName("Edwards");
		expectedVets.add(jimVet);
		expectedVets.add(maryVet);

		given(clinicService.findVets()).willReturn(expectedVets);
	}

	@Test
	public void given_a_list_of_vets_when_showVetList_return_vets_to_model() {
		String response = vetController.showVetList(model);
		assertThat(response).isEqualTo("vets/vetList");
		assertThat(((Vets) model.get("vets")).getVetList()).isEqualTo(expectedVets);
	}

	@Test
	public void given_a_list_of_vets_when_showResourcesVetList_return_vets_to_model() {
		Vets actualVets = vetController.showResourcesVetList();
		assertThat((actualVets).getVetList()).isEqualTo(expectedVets);
	}
}
