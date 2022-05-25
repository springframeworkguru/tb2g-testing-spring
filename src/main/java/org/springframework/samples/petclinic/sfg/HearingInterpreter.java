package org.springframework.samples.petclinic.sfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HearingInterpreter {
    private final WordProducer wordProducer;

    public HearingInterpreter(WordProducer wordProducer) {
        this.wordProducer = wordProducer;
    }

    public String whatIHeard(){
        String word = wordProducer.getWord();
        System.out.printf("word");
        return word;
    }
}
