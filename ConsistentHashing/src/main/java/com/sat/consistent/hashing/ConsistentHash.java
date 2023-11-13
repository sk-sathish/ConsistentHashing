package com.sat.consistent.hashing;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.TreeMap;

//Using SHA 1 For Hashing and get having huge hash space of 2^128,
// as restricting the hash space to smaller number (eg. 1024) leads to collisions
//as the hash space is huge, we are only storing the node hash(s) rather than the entire hashspace
public class ConsistentHash {
    TreeMap<String, String> nodeMapping;
    int nodeCount;
    ConsistentHash(){
        nodeMapping = new TreeMap<String, String>();
        nodeCount = 0;
    }
    public void addNode(String nodeId) throws Exception {
        String hash = getHash(nodeId);
        if(nodeMapping.containsKey(hash)){
            throw new Exception("Node id already exists");
        }
        nodeCount++;
        nodeMapping.put(hash, nodeId);
        System.out.println("Node id: " + nodeId+", Hash: "+hash);
    }
    public void removeNode(String nodeId) throws Exception {
        String hash = getHash(nodeId);
        if(nodeMapping.containsKey(hash)){
            throw new Exception("Node id already exists");
        }
        nodeCount--;
        nodeMapping.remove(hash);
        System.out.println("Node id: " + nodeId+", Hash: "+hash);
    }
    private String getHash(String key){
        String hash = DigestUtils.sha1Hex(key);
        return hash;
    }
    public String getNodeFromKey(String key) throws Exception {
        String hash = getHash(key);
        if(nodeCount == 0){
            throw new Exception("No Node Exists");
        }
        String nodeHash = nodeMapping.higherKey(hash);
        if(nodeHash == null){
            nodeHash = nodeMapping.firstKey();
        }
        return nodeMapping.get(nodeHash);
    }
}
