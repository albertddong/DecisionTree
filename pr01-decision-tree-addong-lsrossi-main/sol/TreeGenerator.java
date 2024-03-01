package sol;

import org.w3c.dom.Attr;
import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {
    private ITreeNode root;

    /**
     * Creating a decision tree based on a dataset and a target attribute, calling on a recursive helper
     * @param trainingData - the dataset used to create the tree
     * @param targetAttribute - the attribute for the tree to predict on
     */
    public void generateTree(Dataset trainingData, String targetAttribute) {
        if(trainingData.getDataObjects().isEmpty()) {
            throw new RuntimeException("Dataset is empty!");
        }

        if(!trainingData.getAttributeList().contains(targetAttribute)) {
            throw new RuntimeException("Target attribute not found in dataset");
        }

        List<String> removeTarget = trainingData.removeAttribute(targetAttribute);
        Dataset withoutTarget =
                new Dataset(removeTarget, trainingData.getDataObjects(), trainingData.getSelectionType());
        this.root = this.generateLeafOrNode(withoutTarget, targetAttribute);
    }

    /**
     * Recursively creating ITreeNodes (AttributeNodes or DecisionLeafs) that will make up the generated tree
     *
     * @param trainingData - the Dataset used to create the ITreeNode
     * @param targetAttribute - the attribute being predicted on
     * @return an AttributeNode or DecisionLeaf
     */
    public ITreeNode generateLeafOrNode(Dataset trainingData, String targetAttribute) {
        if(trainingData.shouldCreateLeaf(targetAttribute)) {
            String outcome = trainingData.getDataObjects().get(0).getAttributeValue(targetAttribute);
            return new DecisionLeaf(outcome);
        }
        // Select an attribute to split on
        // Get unique attribute values for that attribute to split on
        // Call split dataset in a for loop for each ValueEdge
            // Add to the list of ValueEdge
            // Recursive call
        String defVal = trainingData.defaultOutcome(targetAttribute);
        String attribute = trainingData.getAttributeToSplitOn();
        List<ValueEdge> valueEdges = new ArrayList<>();
        AttributeNode newNode = new AttributeNode(defVal, attribute, valueEdges);
        List<Dataset> subsets = trainingData.splitDataset(attribute);
        for(Dataset d : subsets) {
            String edge = d.uniqueAttributeValues(attribute).get(0);
            ValueEdge newEdge = new ValueEdge(this.generateLeafOrNode(d, targetAttribute), edge);
            valueEdges.add(newEdge);
        }
        // Return new node, which now has the list of ValueEdges
        return newNode;
    }

    /**
     * Getting the decision that the tree would produce for a given testing Row
     *
     * @param datum - the row to get the decision for
     * @return a string representing the outcome for that row
     */
    public String getDecision(Row datum) {
        return this.root.getDecision(datum);
    }
}
