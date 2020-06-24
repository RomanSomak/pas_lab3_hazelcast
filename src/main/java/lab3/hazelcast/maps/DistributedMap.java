package lab3.hazelcast.maps;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class DistributedMap {
    public static void main(String[] args) {
        distributedMap();
    }

    private static void distributedMap() {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        IMap<Integer, String> map = hz.getMap("distributedMap");
        for (int i = 0; i < 1000; i++) {
            map.putIfAbsent(i, "value: " + i);
        }

        for (int i = 0; i < 1000; i++) {
            System.out.println(map.get(i));
        }

    }
}
