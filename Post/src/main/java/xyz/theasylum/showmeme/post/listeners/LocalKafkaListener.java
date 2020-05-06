package xyz.theasylum.showmeme.post.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class LocalKafkaListener {
    @KafkaListener(topics = "PostList")
    public void receive(@Payload String message,
                        @Headers MessageHeaders headers) {
//        LOG.info("received message='{}'", message);
//        headers.keySet().forEach(key -> LOG.info("{}: {}", key, headers.get(key)));
        System.out.println("Wow!");
    }
}
