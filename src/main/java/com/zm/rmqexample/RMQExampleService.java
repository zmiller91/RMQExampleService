package com.zm.rmqexample;


import com.zm.rabbitmqservice.service.RMQApplication;
import com.zm.rmqexample.configuration.RMQExampleConfiguration;
import com.zm.rmqexample.exception.PersonNotFoundException;
import com.zm.rmqexample.model.Person;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RMQExampleService implements RMQExampleApi {

    private final Random random = new Random();
    private final ConcurrentHashMap<Integer, Person> people = new ConcurrentHashMap<>();

    public Person getPerson(int id) {
        return people.get(id);
    }

    public Person updatePerson(Person person) throws PersonNotFoundException {
        if(!people.containsKey(person.getId())) {
            PersonNotFoundException e = new PersonNotFoundException();
            e.setId(person.getId());
            throw e;
        }

        people.put(person.getId(), person);
        return people.get(person.getId());
    }

    public Person putPerson(Person person) {
        person.setId(random.nextInt());
        people.put(person.getId(), person);
        return person;
    }

    public static void main(String[] args) {
        String host = RMQExampleConfiguration.Host.TEST.getValue();
        String channel = RMQExampleConfiguration.Channel.TEST.getValue();
        RMQApplication.start(host, channel, new RMQExampleService(), RMQExampleApi.class);
    }
}