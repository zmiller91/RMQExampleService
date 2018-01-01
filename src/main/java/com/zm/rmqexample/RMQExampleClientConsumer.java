package com.zm.rmqexample;

import com.zm.rmqexample.configuration.RMQExampleConfiguration.*;
import com.zm.rmqexample.exception.PersonNotFoundException;
import com.zm.rmqexample.model.Person;

public class RMQExampleClientConsumer {

    public static void main(String[] args) throws Exception {
        RMQExampleClient client = new RMQExampleClient(Host.TEST, Channel.TEST);

        try {

            // Put a person
            Person person = new Person();
            person.setFirstName("Zachary");
            person.setLastName("Miller");

            person = client.putPerson(person);
            System.out.println(
                    person.getFirstName() + " " + person.getLastName() + " (" + person.getId() + ")"
            );

            // Get a person
            int id = person.getId();
            person = client.getPerson(id);
            System.out.println(
                    person.getFirstName() + " " + person.getLastName() + " (" + person.getId() + ")"
            );

            // Update a person
            person = client.getPerson(id);
            person.setFirstName("Zack");
            System.out.println(
                    person.getFirstName() + " " + person.getLastName() + " (" + person.getId() + ")"
            );

            // Update nonexistent person
            person.setId(12345);
            client.updatePerson(person);
        }

        catch(PersonNotFoundException e) {
            System.out.println("The person " + e.getId() + " does not exist.");
        }

        finally {
            client.close();
        }
    }
}
