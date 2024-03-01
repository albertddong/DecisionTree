package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class that represents the edge of an attribute node in the decision tree
 */
public class ValueEdge {
    private ITreeNode child;
    private String value;

    /**
     * Constructor for ValueEdge that assigns values for child and value
     * @param child - the child node that will extend from this edge
     * @param value - the value of the attribute that this edge is connected to
     */
    public ValueEdge(ITreeNode child, String value) {
        this.child = child;
        this.value = value;
    }

    /**
     * Comparing a ValueEdge's value field to an input String to compare
     * @param valueToCompare - the String to compare with the ValueEdge's value field
     * @return a boolean representing if the strings are the same
     */
    public boolean compareValueEdge(String valueToCompare) {
        return valueToCompare.equals(this.value);
    }

    /**
     * Calling getDecision on the ValueEdge class in order to access the child node and continue recursion
     * without a getter
     *
     * @param forDatum - the row to get the decision on
     * @return the outcome for that row
     */
    public String getDecision(Row forDatum){
        return this.child.getDecision(forDatum);
    }
}
