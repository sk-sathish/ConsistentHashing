package com.sat.consistent.hashing;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Consistent Hashing Testing");
        int nodeCount = 50;
        int keyCount = 100000;
        HashMap<String, Integer> nodeVsKeyCount = new HashMap<String,Integer>();
        ConsistentHash consistentHash = new ConsistentHash();
        for(int i=0;i<nodeCount;i++){
            consistentHash.addNode("Node" + i);
        }
        for(int i=0;i<keyCount;i++){
            String key = "key" + i;
            String nodeId = consistentHash.getNodeFromKey(key);
            nodeVsKeyCount.merge(nodeId, 1, Integer::sum);
            System.out.println(key+" -> "+nodeId);
        }
        double idealKeyDistribution = (1d/nodeCount)*100;
        for(int i=0;i<nodeCount;i++){
            double count = nodeVsKeyCount.getOrDefault("Node"+i, 0);
            double actualKeyDistribution = 100*(count/keyCount);
            System.out.println(count);
            System.out.println("Node id : "+i+", Ideal key Distribution : "+idealKeyDistribution+"% ,actual key Distribution : "+actualKeyDistribution+"%");
        }
    }
}