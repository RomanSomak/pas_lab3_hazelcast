package lab3.hazelcast.topic;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;


public class TopicSubscriberOne {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        ITopic<Integer> topic = hz.getTopic("testTopic");
        topic.addMessageListener(new MessageListenerImpl());
        System.out.println("Subscribed");
    }

    private static class MessageListenerImpl implements MessageListener<Integer> {
        public void onMessage(Message<Integer> m) {
            System.out.println("Received: " + m.getMessageObject());
        }
    }
}
