package sol;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import src.ITreeNode;
import src.Row;

/**
 * A class representing an inner node in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class AttributeNode implements ITreeNode {
    // TODO: add more fields as needed
    private String defaultValue;
    private String attribute;
    private List<ValueEdge> outgoingEdges; // Remember to make an addEdge method!

    /**
     * Constructor for AttributeNode that assigns values for defaultValue, attrivbute, and edgeList.
     * @param defaultValue - the default outcome of a node
     * @param attribute - the attribute name associated with this node
     * @param edgeList - a list of valueEdge values associated with this particular node
     */
    public AttributeNode(String defaultValue, String attribute, List<ValueEdge> edgeList) {
        this.defaultValue = defaultValue;
        this.attribute = attribute;
        this.outgoingEdges = edgeList;

    }

    /**
     * Getting the decision for a given Row object once inside of this AttributeNode in a TreeGenerator
     *
     * @param forDatum - the datum to lookup a decision for
     * @return the outcome for that Row
     */
    public String getDecision(Row forDatum) {
        String attrVal = forDatum.getAttributeValue(this.attribute);
        for(ValueEdge edge : this.outgoingEdges) {
            if(edge.compareValueEdge(attrVal)) {
                return edge.getDecision(forDatum);
            }
        }
        return this.defaultValue;
    }
}
