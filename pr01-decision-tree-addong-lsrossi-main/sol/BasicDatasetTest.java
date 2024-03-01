package sol;

import org.junit.Assert;
import org.junit.Test;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

/**
 * A class to test basic decision tree functionality on a basic training dataset
 */
public class BasicDatasetTest {
    // IMPORTANT: for this filepath to work, make sure the project is open as the top-level directory in IntelliJ
    // (See the first yellow information box in the handout testing section for details)
    String trainingPath = "data/give-a-loan.csv";
    String targetAttribute = "Give a loan?";
    TreeGenerator testGenerator;

    /**
     * Constructs the decision tree for testing based on the input file and the target attribute.
     */
    @Before
    public void buildTreeForTest() {
        List<Row> dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        Dataset training = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        // builds a TreeGenerator object and generates a tree for "foodType"
        this.testGenerator = new TreeGenerator();
//        TODO: Uncomment this once you've implemented generateTree
        this.testGenerator.generateTree(training, this.targetAttribute);
    }

    /**
     * Tests the expected classification of the "tangerine" row is a fruit
     */
    @Test
    public void testClassification() {
        // makes a new (partial) Row representing the tangerine from the example
        // TODO: make your own rows based on your dataset
        Row dan = new Row("test row (dan)");
        dan.setAttributeValue("Age", "0-18");
        dan.setAttributeValue("Credit score", "High");
        dan.setAttributeValue("Member of this bank", "Yes");
        dan.setAttributeValue("Marital status", "Single");
        // TODO: make your own assertions based on the expected classifications
        // TODO: Uncomment this once you've implemented getDecision
        Assert.assertEquals("No", this.testGenerator.getDecision(dan));

        Row jan = new Row("test row (jan)");
        jan.setAttributeValue("Age", "50+");
        jan.setAttributeValue("Credit score", "High");
        jan.setAttributeValue("Member of this bank", "No");
        jan.setAttributeValue("Marital status", "Married");
        Assert.assertEquals("Yes", this.testGenerator.getDecision(jan));

        Row ann = new Row("test row (ann)");
        ann.setAttributeValue("Age", "36-50");
        ann.setAttributeValue("Credit score", "Medium");
        ann.setAttributeValue("Member of this bank", "Yes");
        ann.setAttributeValue("Marital status", "Divorced");
        Assert.assertEquals("No", this.testGenerator.getDecision(ann));

        Row stan = new Row("test row (stan)");
        stan.setAttributeValue("Age", "18-35");
        stan.setAttributeValue("Credit score", "High");
        stan.setAttributeValue("Member of this bank", "No");
        stan.setAttributeValue("Marital status", "Divorced");
        Assert.assertEquals("Yes", this.testGenerator.getDecision(stan));
    }
}
