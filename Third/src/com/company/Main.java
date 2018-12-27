package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        //building a temporary DAG
        DiAGraph familyTree = new DiAGraph(0);

        // to build DAG, first call validateAddNode() for all existing nodes in the DAG

        familyTree.validateAddNode(0,"A");
        familyTree.validateAddNode(1,"B");
        familyTree.validateAddNode(2,"C");
        familyTree.validateAddNode(3,"D");
        familyTree.validateAddNode(4,"E");
        familyTree.validateAddNode(5,"F");

        // to add edges/dependencies between nodes, call validateAddDependencyNode()
        //        return types for both the functions can be verified.
        familyTree.validateAddDependencyNode(0,1,"B");
        familyTree.validateAddDependencyNode(0,2,"C");
        familyTree.validateAddDependencyNode(0,3,"D");
        familyTree.validateAddDependencyNode(3,4,"E");
        familyTree.validateAddDependencyNode(3,5,"F");

        //to print the DAG
        familyTree.printAll();


//        familyTree.getParents(2);
//        familyTree.getChildren(0);
//        familyTree.getAncestors(4);


//        for(Node n:familyTree.getDescendants(0)){
//            //System.out.println(n.getNode_name());
//        }
//        System.out.println(familyTree.deleteNode(3));
//        familyTree.printAll();


    }
}
