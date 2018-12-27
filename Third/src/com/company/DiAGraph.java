package com.company;

import java.util.*;

public class DiAGraph {
    private int numVertices,numEdges;
    private HashMap<Integer,Node> nodes;
    private LinkedList<LinkedList<Node>> dependencies;



    public DiAGraph(int numVertices) {
        if(numVertices<0) throw new IllegalArgumentException("No of vertices must be non-negative");
        this.numVertices = numVertices;
        this.numEdges = 0;
        nodes = new HashMap<>();
        dependencies = new LinkedList<>();
        //visited = new boolean[nodes.size()];
    }

    private boolean checkCyclic(int parentID,int childID){
        LinkedList<Node> ancestors = getAncestors(parentID);
        for(Node n:ancestors)
            if(n.getNode_id()==childID)
                return true;

        return false;
    }
    private boolean deleteCentral(int nodeID){

        if(nodes.containsKey(nodeID)) {
            for(Node n:dependencies.get(nodeID))
                deleteCentral(n.getNode_id());
            nodes.remove(nodeID);
            return true;
        }
        return false;
    }

    public boolean validateAddNode(int nodeID, String nodeName) throws NullPointerException{

        // adding a node in the central collection, containing all nodes in the graph
        Node newNode = new Node(nodeID,nodeName);
        if(!nodes.containsKey(newNode.getNode_id())) {
            nodes.put(newNode.getNode_id(), newNode);
        }
        else return false;

        // adding an empty linked list, indicating it can have multiple children node
        try{
            if(dependencies.get(newNode.getNode_id())==null){
                dependencies.add(newNode.getNode_id(),new LinkedList<>());
            }
            else return false;
        }
        catch (IndexOutOfBoundsException e){
            dependencies.add(newNode.getNode_id(),new LinkedList<>());
        }


        return true;
    }

    public boolean validateAddDependencyNode(int parentID, int childID, String nodeName){

        if(!checkCyclic(parentID,childID)){
            Node n = new Node(childID,nodeName);
            //adding the child node under an existing parent node
            if(nodes.containsKey(parentID)){
                dependencies.get(parentID).add(n);
                return true;
            }
        }
        return false;
    }

    public void printAll(){
        for(int i =0;i<nodes.size();i++){

            if(nodes.containsKey(i)){
                System.out.print(String.format("(%s,%d,%d)-->", nodes.get(i).getNode_name(),nodes.get(i).getNode_id(),i));
                LinkedList<Node> temp = dependencies.get(i);
                for(Node n:temp){
                    System.out.print(String.format("(%s,%d,%d),", n.getNode_name(),n.getNode_id(),temp.indexOf(n)));
                }
                System.out.println("\n");
            }
        }
    }

    public LinkedList<Node> getParents(int childNodeID){

        LinkedList<Node> parents = new LinkedList<>();
        //iterate over the dependency list and check nodeID
        for(int i =0;i<dependencies.size();i++){
            for(Node n:dependencies.get(i))
                if(n.getNode_id() == childNodeID)
                    // if node found, add node to parents list
                    parents.add(nodes.get(i));

        }
        return parents;

    }
    public LinkedList<Node> getChildren(int parentNodeID){
       if(nodes.containsKey(parentNodeID)){
           return dependencies.get(parentNodeID);
       }
       return null;
    }

    public LinkedList<Node> getAncestors(int childNodeID){
        LinkedList<Node> parents = getParents(childNodeID);
        LinkedList<Node> ancestors = getParents(childNodeID);
        for(Node n:parents){
            // recursively call get parents on all immediate parents of the node
            ancestors.addAll(getAncestors(n.getNode_id()));
        }
        return ancestors;
    }
    public LinkedList<Node> getDescendants(int parentNodeID){
        LinkedList<Node> children = getChildren(parentNodeID);
        LinkedList<Node> descendants = new LinkedList<>(children);
        if(children.size()!=0){
            for(Node n:children)
                if(dependencies.get(n.getNode_id()).size()!=0)
                    descendants.addAll(dependencies.get(n.getNode_id()));
        }
        return descendants;

    }
    public boolean deleteDependency(int parentID,int childID){
        int nodeIndex=-1;
        if(nodes.containsKey(parentID)) {
            for(int i=0;i<dependencies.get(parentID).size();i++)
                if(dependencies.get(parentID).get(i).getNode_id()==childID)
                {   nodeIndex=i;break;}
        }
        else return false;
        if(nodeIndex!=-1) {
            dependencies.get(parentID).remove(nodeIndex);
            return true;
        }
        else return false;
    }

    public boolean deleteNode(int nodeID){
        for(int i =0;i<dependencies.size();i++){

            LinkedList<Node> temp = dependencies.get(i);
            for(Node n:temp){
                if(n.getNode_id()==nodeID) {
                    dependencies.get(i).remove(n);
                    break;
                }

            }
        }
        if(deleteCentral(nodeID)){
            return true;
        }
        return false;
    }

}
