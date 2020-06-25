package lab3.hazelcast.maps;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.map.IMap;

import java.io.Serializable;

public class OptimisticLock {
    public static void main(String[] args) throws InterruptedException {
        distributedMapWithOptimisticLock();
    }

    private static void distributedMapWithOptimisticLock() throws InterruptedException {
        ClientConfig config = new ClientConfig();
        config.setClusterName("dev");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        IMap<String, Value> map = hz.getMap( "OptimisticLock" );
        String key = "9";
        map.putIfAbsent( key, new Value() );
        System.out.println( "Starting" );

        //+

        for ( int k = 0; k < 1000; k++ ) {
            if ( k % 10 == 0 ) System.out.println( "At: " + k );
            for (; ; ) {
                Value oldValue = map.get( key );
                Value newValue = new Value( oldValue );
                Thread.sleep( 10 );
                newValue.amount++;
                FencedLock lock = hz.getCPSubsystem().getLock(key);
                lock.lock();
                if (oldValue.equals(map.get(key))) {
                    map.put(key, newValue);
                    lock.unlock();
                    break;
                } else {
                    lock.unlock();
                }
            }
        }

        /*for ( int k = 0; k < 1000; k++ ) {
            if ( k % 10 == 0 ) System.out.println( "At: " + k );
            for (; ; ) {
                Value oldValue = map.get( key );
                Value newValue = new Value( oldValue );
                Thread.sleep( 10 );
                newValue.amount++;
                map.lock( key );
                if (oldValue.equals(map.get(key))) {
                    map.put(key, newValue);
                    map.unlock(key);
                    break;
                } else {
                    map.unlock(key);
                }
            }
        }*/

        /*for ( int k = 0; k < 1000; k++ ) {
            if ( k % 10 == 0 ) System.out.println( "At: " + k );
            for (; ; ) {
                Value oldValue = map.get( key );
                Value newValue = new Value( oldValue );
                Thread.sleep( 10 );
                newValue.amount++;
                if ( map.replace( key, oldValue, newValue ) )
                    break;
            }
        }*/

        System.out.println( "Finished! Result = " + map.get( key ).amount );

    }

    static class Value implements Serializable {
        public int amount;

        public Value() {
        }

        public Value( Value that ) {
            this.amount = that.amount;
        }

        public boolean equals( Object o ) {
            if ( o == this ) return true;
            if ( !( o instanceof Value ) ) return false;
            Value that = ( Value ) o;
            return that.amount == this.amount;
        }
    }
}
