package lab3.hazelcast.queue;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.HazelcastInstance;

public class Producer {
    public static void main(String[] args) throws Exception {
        produce();
    }

    public static void produce() throws Exception {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        IQueue<Integer> queue = hz.getQueue("boundedQueue");
        for ( int k = 1; k < 100; k++ ) {
            queue.put( k );
            System.out.println( "Producing: " + k );
            Thread.sleep(500);
        }
        queue.put( -1 );
        System.out.println( "Producer Finished!" );
    }
}
