package xyz.theasylum.showmeme.post.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import xyz.theasylum.showmeme.post.PostApplication;
import xyz.theasylum.showmeme.post.kafka.domain.KafkaPost;

@Service
public class KafkaPostTopic {
    private static final String TOPIC_NAME="posts";

    @Value("${application.id}")
    String id;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendPostMessage(KafkaPost postMessage){
        Message<KafkaPost> message = MessageBuilder
                .withPayload(postMessage)
                .setHeader("producer", id)
                .setHeader(KafkaHeaders.TOPIC,TOPIC_NAME)
                .build();
        kafkaTemplate.send(message);

    }
}
