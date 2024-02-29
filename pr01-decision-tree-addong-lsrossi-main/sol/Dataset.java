package sol;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.AttributeSelection;
import src.IDataset;
import src.Row;

/**
 * A class representing a training dataset for the decision tree
 */
// TODO: Uncomment this once you've implemented the methods in the IDataset interface!
public class Dataset /* implements IDataset */ {

    private List<String> attributeList;
    private List<Row> dataObjects;
    private AttributeSelection selectionType;



    /**
     * Constructor for a Dataset object
     * @param attributeList - a list of attributes
     * @param dataObjects -  a list of rows
     * @param attributeSelection - an enum for which way to select attributes
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects, AttributeSelection attributeSelection) {
        // TODO: implement the constructor! (Hint: take a look at `getAttributeToSplitOn`)
        this.attributeList = attributeList;
        this.dataObjects = dataObjects;
        this.selectionType = attributeSelection;
    }

    /**
     * Returns the new attribute/node that should be split on next depending on the selection type
     * @return - the attribute to split on
     */
    public String getAttributeToSplitOn() {
        switch (this.selectionType) {
            case ASCENDING_ALPHABETICAL -> { //We deleted AttributeSelection. before ASCENDING_ALPHABETICAL - is that okay?
                return this.attributeList.stream().sorted().toList().get(0);
            }
            case DESCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);
            }
            case RANDOM -> {
                Random random = new Random();
                int upperBound = this.attributeList.size();
                int randomNum = random.nextInt(upperBound);
                return this.attributeList.get(randomNum);
            }
        }
        throw new RuntimeException("Non-Exhaustive Switch Case");
    }

    /**
     * Gets list of attributes in the dataset
     *
     * @return a list of strings
     */
    public List<String> getAttributeList() {
        return this.attributeList;
    }

    /**
     * Gets list of data objects (row) in the dataset
     *
     * @return a list of Rows
     */
    public List<Row> getDataObjects() {
        return this.dataObjects;
    }

    /**
     * Returns the attribute selection type (alphabetical, reverse alphabetical, random) for this Dataset
     *
     * @return the attribute selection type
     */
    public AttributeSelection getSelectionType() {
        return this.selectionType;
    }

    /**
     * Finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    public int size() {
        return this.dataObjects.size();
    }


    /**
     * Creating a list of all the attribute value outcomes of the target attribute for a dataset
     * @param attribute - the String representing the attribute of a tree
     * @return a list of all the corresponding attribute values
     */
    public List<String> allAttributeValues(String attribute) {
        List<String> allOutcomes = new ArrayList<>();
        for(Row r : this.dataObjects) {
            allOutcomes.add(r.getAttributeValue(attribute));
        }
        return allOutcomes;
    }

    /**
     * Creating a list of the unique values of the target attribute of a tree
     *
     * @param attribute - the String representing the attribute being predicted by the tree
     * @return a list of the unique outcomes of a Dataset
     */
    public List<String> uniqueAttributeValues(String attribute) {
        List<String> allOutcomes = this.allAttributeValues(attribute);
        List<String> uniqueOutcomes = new ArrayList<>();
        for(String s : allOutcomes) {
            if(Collections.frequency(uniqueOutcomes, s) == 0) {
                uniqueOutcomes.add(s);
            }
        }
        return uniqueOutcomes;
    }

    /**
     * Returns the most common value among the values of the target attribute
     *
     * @param targetAttribute - the String representing the attribute being predicted by the tree
     * @return the default outcome
     */
    public String defaultOutcome(String targetAttribute) {
        List<String> allOutcomes = this.allAttributeValues(targetAttribute);
        List<String> uniqueOutcomes = this.uniqueAttributeValues(targetAttribute);

        int maxFrequency = 0;
        List<String> maxOutcomes = new ArrayList<>();
        for(String s : uniqueOutcomes) {
            if (Collections.frequency(allOutcomes, s) > maxFrequency) {
                maxFrequency = Collections.frequency(allOutcomes, s);
                maxOutcomes.clear();
                maxOutcomes.add(s);
            }
            else if (Collections.frequency(allOutcomes, s) == maxFrequency) {
                maxOutcomes.add(s);
            }
        }

        if (!maxOutcomes.isEmpty()) {
            return maxOutcomes.get(0); // Returning the first elt of a list of multiple equal outcomes is "random"
            // because it is arbitrary which row is higher in the Dataset (the Dataset rows have not been sorted)
        } else {
            throw new RuntimeException("No Outcomes found.");// Throw error saying no outcomes found
        }
    }

    /**
     * Determining if a leaf should be created based on if the values in the Dataset are all the same for the given
     * target attribute
     *
     * @param targetAttribute - the String of the target attribute
     * @return a boolean representing if a leaf should be created
     */
    public boolean shouldCreateLeaf(String targetAttribute) {
        return (this.attributeList.isEmpty() || this.uniqueAttributeValues(targetAttribute).size() == 1);
    }

    /**
     * Creates a copy of the attributeList with the "toRemove" attribute not included within the list
     * @param toRemove - the String of the attribute to remove
     * @return a new list with the String of the attribute removed
     */
    public List<String> removeAttribute(String toRemove) {
        List<String> filteredList = this.attributeList.stream().filter(a -> !a.equals(toRemove)).toList();
        return filteredList;
    }

    /**
     * Splitting a Dataset into a list of Datasets, sorted into the various attribute values of the attribute to
     * split on
     *
     * @param attribute - the attribute to split on
     * @return a list of the sorted datasets
     */
    public List<Dataset> splitDataset(String attribute) {
        List<Dataset> newDatasets = new ArrayList<>();
        boolean added;
        for (Row row : this.dataObjects) {
            added = false;
            for (Dataset dataset : newDatasets) {
                if (row.getAttributeValue(attribute).equals(dataset.dataObjects.get(0).getAttributeValue(attribute))) {
                    dataset.dataObjects.add(row); // add to the dataset where we found a match
                    added = true;
                }
            }

            if(!added) {
                List<Row> newDataObjectList = new ArrayList<>();
                newDataObjectList.add(row);
                List<String> newAttrLst = this.removeAttribute(attribute);
                Dataset d = new Dataset(newAttrLst, newDataObjectList, this.selectionType);
                newDatasets.add(d);
            }

        }

        return newDatasets;
    }
}
