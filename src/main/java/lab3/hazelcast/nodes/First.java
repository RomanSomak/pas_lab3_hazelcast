package lab3.hazelcast.nodes;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.HashMap;
import java.util.Map;

public class First {
    public static void main(String[] args) {
        final Config config = new Config();
        MapConfig mapConfig = new MapConfig("default").setBackupCount(1);
        config.addMapConfig(mapConfig);

        Map<String, QueueConfig> queueConfigs = new HashMap<>();
        queueConfigs.put("boundedQueue", new QueueConfig().setMaxSize(10));
        config.setQueueConfigs(queueConfigs);


        config.setClusterName("dev");
        config.setInstanceName("First");
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
    }


}
