package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class representing a leaf in the decision tree.
 */
public class DecisionLeaf implements ITreeNode {
    private String outcomeValue;

    /**
     * Constructor for DecisionLeaf that assigns the outcome value
     * @param outcomeValue - the value of the outcome
     */
    public DecisionLeaf(String outcomeValue) {
        this.outcomeValue = outcomeValue;
    }

    /**
     * Getting the decision in a decision tree for this leaf
     * The output does not depend upon the input row
     *
     * @param forDatum the datum to look up a decision for
     * @return the outcomeValue string in this leaf
     */
    public String getDecision(Row forDatum) {
        return this.outcomeValue;
    }
}
