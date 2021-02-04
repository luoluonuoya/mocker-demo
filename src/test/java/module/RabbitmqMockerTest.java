package module;

import configuration.MockRabbitConfig;
import demo.Bootstrap;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Bootstrap.class, MockRabbitConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RabbitmqMockerTest {

    @Autowired
    RabbitTemplate template;

    @Test
    public void test() {
        template.convertAndSend("exchange", "routingKey", "sendMessage");
        Message msg = template.receive("queue");
        String msgBody = new String(msg.getBody());
        System.out.println("-------------------------------" + msgBody);
        assertEquals(msgBody, "sendMessage");
    }

}