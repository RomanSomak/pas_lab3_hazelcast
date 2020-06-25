package lab3.hazelcast.maps;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.io.Serializable;

public class PessimisticLock {
    public static void main(String[] args) throws InterruptedException {
        distributedMapWithPessimisticLock();
    }

    private static void distributedMapWithPessimisticLock() throws InterruptedException {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        IMap<String, Value> map = hz.getMap( "PessimisticLock" );
        String key = "3";
        map.putIfAbsent( key, new Value() );
        System.out.println( "Starting" );
        for ( int k = 0; k < 1000; k++ ) {
            map.lock( key );
            try {
                Value value = map.get( key );
                Thread.sleep( 10 );
                value.amount++;
                map.put( key, value );
            } finally {
                map.unlock( key );
            }
        }
        System.out.println( "Finished! Result = " + map.get( key ).amount );

    }

    static class Value implements Serializable {
        public int amount;
    }
}
