package zk;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher;

public class CreateSession {

    public static ZkClient connectZK(String message) {
        //zk集群的地址
        String ZKServers = "39.108.212.167:2181";

        ZkClient zkClient = new ZkClient(ZKServers,10000,10000,new SerializableSerializer());
        /**
         * zk连接状态的变更
         */
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleNewSession() throws Exception {
                System.out.println(message + " handleNewSession()");
            }

            @Override
            public void handleStateChanged(Watcher.Event.KeeperState stat) throws Exception {
                System.out.println(message + " handleStateChanged,stat:" + stat);
            }
        });
        System.out.println("conneted ok!");
        return zkClient;
    }

}
