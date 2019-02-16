package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Service;

/**
 * Created by jt on 2019-02-16.
 */
@Service
public class HearingInterprter {

    private final WordProducer wordProducer;

    public HearingInterprter(WordProducer wordProducer) {
        this.wordProducer = wordProducer;
    }

    public String whatIheard(){
        String word = wordProducer.getWord();

        System.out.println(word);

        return word;
    }
}
