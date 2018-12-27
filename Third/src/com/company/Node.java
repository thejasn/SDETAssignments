package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Node {
    private int node_id;
    private String node_name;
    private Map<String,String> additional_info;

    public Node(int node_id) {
        this.node_id = node_id;
    }

    public Node(int node_id, String node_name) {
        this.node_id = node_id;
        this.node_name = node_name;
    }

    public Map<String, String> getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(Map<String, String> additional_info) {
        this.additional_info = additional_info;
    }

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

}

