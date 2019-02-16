package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {BaseConfig.class, LaurelConfig.class})
class HearingInterpreterLaurelTest {

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIheard() {
        String word = hearingInterpreter.whatIheard();

        assertEquals("Laurel", word);
    }
}