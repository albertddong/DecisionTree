package sol;

import java.util.List;
import java.util.LinkedList;
import src.ITreeNode;
import src.Row;

/**
 * A class representing an inner node in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class AttributeNode /* implements ITreeNode */ {
    // TODO: add more fields as needed
    private String defaultValue;
    private String attribute;
    private List<ValueEdge> outgoingEdges; // Remember to make an addEdge method!

    public AttributeNode(String defaultValue, String attribute) {
        this.defaultValue = defaultValue;
        this.attribute = attribute;
        this.outgoingEdges = new LinkedList<ValueEdge>();

    }


    // TODO: implement the ITreeNode interface
}