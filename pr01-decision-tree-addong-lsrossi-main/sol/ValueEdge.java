package sol;

import src.ITreeNode;

/**
 * A class that represents the edge of an attribute node in the decision tree
 */
public class ValueEdge {
    // TODO: add more fields if needed
    private ITreeNode child;
    private String value;

    /**
     * Constructor for ValueEdge
     * @param child - the child node that will extend from this edge
     * @param value - the value of the attribute that this edge is connected to
     */
    public ValueEdge(ITreeNode child, String value) {
        this.child = child;
        this.value = value;
    }

}
