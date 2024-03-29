package com.robotmq.client.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robotmq.client.common.setup.RobotMQSetUp;
import com.robotmq.client.glob.CommonVars;
import com.robotmq.client.handler.RobotMQHandler;
import com.robotmq.client.handler.RobotMQProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class RobotMQStarter {

    private RobotMQSetUp robotMQSetUp = RobotMQSetUp.getINSTANCE();
    private RobotMQHandler handler = RobotMQHandler.getINSTANCE();

    private Person p = new Person(1,"Yigit");
    private List<Person> x = Arrays.asList(p);
    public void build(String basePackage) throws InterruptedException {
            if (!StringUtils.hasText(basePackage)){
                throw new InterruptedException();
            }
            CommonVars.PACKAGE_NAME= basePackage;
            robotMQSetUp.setUp();
            handler.handler();
    }


    @Scheduled(fixedRate = 1000)
    private void timingProduce() throws InterruptedException, JsonProcessingException {
        RobotMQProducer producer = RobotMQProducer.getINSTANCE();
        producer.produce("topic1", UUID.randomUUID().toString());
        producer.produce("topic2", UUID.randomUUID().toString());
        producer.produce("topic3", p);
        producer.produce("topic4",x);
    }
}

class Person{

    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}