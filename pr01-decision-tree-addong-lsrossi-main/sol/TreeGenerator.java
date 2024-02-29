package sol;

import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import java.util.List;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeGenerator interface!
public class TreeGenerator /* implements ITreeGenerator<Dataset> */ {
    // TODO: document this field
    private ITreeNode root;
    
    // TODO: Implement methods declared in ITreeGenerator interface!

    /**
     * Creating a decision tree based on a dataset and a target attribute, calling on a recursive helper
     *
     * @param trainingData - the dataset used to create the tree
     * @param targetAttribute - the attribute for the tree to predict on
     */
    public void generateTree(Dataset trainingData, String targetAttribute) {
        // Changed <D trainingData> to <Dataset trainingData>. I think that's allowed

        // Remove the targetAttribute from the Dataset's attributeList - will require making a new ArrayList and setting
        // the Dataset's attributeList equal to this new list
        // EdStem talks about a .filter method that will be useful for this. We may want to implement .filter in our
        // splitDataset method
        // Construct the root based on the selectionType of the Dataset

        // List<Integer> origList = <some elided list...>;
        // List<Integer> filteredList = origList.stream().filter(<some predicate>).toList();

        List<String> removeTarget = trainingData.removeAttribute(targetAttribute);
        String def = trainingData.defaultOutcome(targetAttribute);
    }

    /**
     * Recursively creating ITreeNodes (AttributeNodes or DecisionLeafs) that will make up the generated tree
     *
     * @param trainingData - the Dataset used to create the ITreeNode
     * @param targetAttribute - the attribute being predicted on
     * @return an AttributeNode or DecisionLeaf
     */
    public ITreeNode generateLeafOrNode(Dataset trainingData, String targetAttribute) {
        String splitAttribute = trainingData.getAttributeToSplitOn(); //includes the outcome attribute (as of now) -
        // may not pass in the same trainingData as we pass into the generateTree method
        if(trainingData.shouldCreateLeaf(targetAttribute)) {
            String outcome = trainingData.getDataObjects().get(0).getAttributeValue(targetAttribute);
            DecisionLeaf outcomeLeaf = new DecisionLeaf(outcome);
            return outcomeLeaf;
        }
        else
    }
}
