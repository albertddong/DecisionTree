package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * A class containing the tests for methods in the TreeGenerator and Dataset classes
 */
public class DecisionTreeTest {
    //TODO: Write more unit and system tests! Some basic guidelines that we will be looking for:
    // 1. Small unit tests on the Dataset class testing the IDataset methods
    // 2. Small unit tests on the TreeGenerator class that test the ITreeGenerator methods
    // 3. Tests on your own small dataset (expect 70% accuracy on testing data, 95% on training data)
    // 4. Test on the villains dataset (expect 70% accuracy on testing data, 95% on training data)
    // 5. Tests on the mushrooms dataset (expect 70% accuracy on testing data, 95% on training data)
    // Feel free to write more unit tests for your own helper methods -- more details can be found in the handout!
    public String trainingPath = "data/give-a-loan.csv";
    public String targetAttribute = "Give a loan?";

    public List<Row> dataObjects;

    public List<String> attributeList;

    public Dataset training;



    /**
     * Creating a small Dataset to test basic Dataset methods
     */
    @Before
    public void buildTreeForTest() {
        this.dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
        this.attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.training = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
    }

    /**
     * Testing the getAttributeList method in the Dataset class
     */
    @Test
    public void testGetAttributeList() {
        assertEquals(this.attributeList, this.training.getAttributeList());
    }

    /**
     * Testing the getDataObjects method in the Dataset class
     */
    @Test
    public void testGetDataObjects() {
        assertEquals(this.dataObjects, this.training.getDataObjects());
    }

    /**
     * Testing the getSelectionType method in the Dataset class
     */
    @Test
    public void testGetSelectionType() {
        assertEquals(AttributeSelection.ASCENDING_ALPHABETICAL, training.getSelectionType());
    }

    /**
     * Testing the size method in the Dataset class
     */
    @Test
    public void testSize() {
        assertEquals(10, this.training.size());
    }

    /**
     * Testing the defaultOutcome method, which computes the default outcome of a Dataset
     */
    @Test
    public void testDefaultOutcome() {
        // Because these tests pass, we know that our allAttributeValues and uniqueOutcomesList methods are working
        assertEquals("High", this.training.defaultOutcome("Credit score"));
        assertEquals("Single", this.training.defaultOutcome("Marital status"));
    }

    /**
     * Testing the shouldCreateLeaf method, which determines if a leaf should be created on a given Dataset object
     */
    @Test
    public void testShouldCreateLeaf() {
        String createLeafTrainingPath = "data/create-leaf-test.csv";
        List<Row> createLeafDataObjects = DecisionTreeCSVParser.parse(createLeafTrainingPath);
        List<String> createLeafAttributeList = new ArrayList<>(createLeafDataObjects.get(0).getAttributes());
        Dataset training2 = new Dataset(createLeafAttributeList, createLeafDataObjects, AttributeSelection.DESCENDING_ALPHABETICAL);


        assertFalse(training.shouldCreateLeaf("Give a loan?"));
        assertTrue(training2.shouldCreateLeaf("Weather"));
        assertTrue(training2.shouldCreateLeaf("Worn yesterday?"));
        assertFalse(training2.shouldCreateLeaf("Plans"));
        assertFalse(training2.shouldCreateLeaf("Location"));
        assertTrue(training2.shouldCreateLeaf("Wear bucket hat?"));
    }

    /**
     * Testing the removeAttribute method which returns a copy of the attribtueList without the attribute to remove
     */
    @Test
    public void testRemoveAttribute() {
        List<String> removedList = this.training.removeAttribute("Age");
        assertEquals(4, removedList.size());
        assertEquals(5, training.getAttributeList().size());
        assertEquals("Credit score", removedList.get(2));
        assertEquals("Give a loan?", removedList.get(3));
    }

    /**
     * Testing the splitDataset method, which will split a Dataset object into a list of Datset object subsets
     */
    @Test
    public void testSplitDataset() {
        List<Dataset> split = training.splitDataset("Credit score");
        assertEquals(3, split.size());
        assertEquals(4, split.get(0).getDataObjects().size());
        assertEquals(5, split.get(1).getDataObjects().size());
        assertEquals(1, split.get(2).getDataObjects().size());
        assertEquals(4, split.get(0).getAttributeList().size());
        assertEquals(4, split.get(1).getAttributeList().size());
        assertEquals(4, split.get(2).getAttributeList().size());
        assertEquals(5, training.getAttributeList().size());
    }
}

