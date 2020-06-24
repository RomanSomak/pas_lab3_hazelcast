package lab3.hazelcast.topic;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;


public class TopicPublisher {

    public static void main(String[] args) throws InterruptedException {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        ITopic<Integer> topic = hz.getTopic("testTopic");

        System.out.println("Started publishing");

        for (int i = 0; i < 20; i++) {
            topic.publish(i);
            System.out.println("Published: " + i);
            Thread.sleep(1000);
        }

        System.out.println("Finished publishing");
    }
}
