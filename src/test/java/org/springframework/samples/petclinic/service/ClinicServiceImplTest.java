package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class ClinicServiceImplTest {
	@Mock
	private PetRepository petRepository;

	@Mock
	private VetRepository vetRepository;

	@Mock
	private OwnerRepository ownerRepository;

	@Mock
	private VisitRepository visitRepository;

	@InjectMocks
	private ClinicServiceImpl clinicService;

	private List<PetType> expectedPetTypes = Lists.newArrayList();

	@Before
	public void setUp() {
		PetType dog = new PetType();
		dog.setName("Fido");
		dog.setId(1);
		expectedPetTypes.add(dog);
	}

	@Test
	public void findPetTypes_returns_the_pets() {
		given(petRepository.findPetTypes()).willReturn(expectedPetTypes);
		Collection<PetType> actualPetTypes = clinicService.findPetTypes();
		then(petRepository).should().findPetTypes();
		assertThat(actualPetTypes).isEqualTo(expectedPetTypes);
	}
}
