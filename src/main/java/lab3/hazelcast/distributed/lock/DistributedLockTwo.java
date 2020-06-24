package lab3.hazelcast.distributed.lock;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;

import java.util.concurrent.TimeUnit;


public class DistributedLockTwo {

    public static void main(String[] args) throws InterruptedException {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        FencedLock lock = hz.getCPSubsystem().getLock("testLock");

        System.out.println("Getting lock");
        if ( lock.tryLock ( 10, TimeUnit.SECONDS ) ) {
            try {
                System.out.println("Got lock");
                Thread.sleep(1000);
            } finally {
                lock.unlock();
                System.out.println("Unlocked");
            }
        } else {
            System.out.println("Could not get lock");
        }
    }


}
