package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import simulation.FireSimulation;
import simulation.GameOfLifeSimulation;
import simulation.PredatorPreySimulation;
import simulation.SegregationSimulation;
import simulation.Simulation;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Handles parsing XML files by returning the root elements.
 *
 */
public class XMLParser {
    private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();    
    
    public Simulation createSimulation(Element rootElement) {
        String simulationType = getSimulationType(rootElement);
        Simulation simulation;
        if (simulationType.equals("Fire")) {
            simulation = new FireSimulation(generateSimulationConfig(rootElement));  
        }
        else if (simulationType.equals("GameOfLife")) {
            simulation = new GameOfLifeSimulation(generateSimulationConfig(rootElement));
        }
        else if (simulationType.equals("PredatorPrey")) {
            simulation = new PredatorPreySimulation(generateSimulationConfig(rootElement));
        }
        else {
            simulation = new SegregationSimulation(generateSimulationConfig(rootElement));
        }
        return simulation;
    }
    
    public void printMap (Map<String, String> input) {
        for (Map.Entry<String, String> cell : input.entrySet()) {
            System.out.println(cell.getKey() + " " + cell.getValue());
        }
    }
    
    public Map<String, Map<String, String>> generateSimulationConfig(Element rootElement) {
        Map<String, Map<String, String>> SimulationConfig = new HashMap<String, Map<String, String>>();
        SimulationConfig.put("Cells", generateInitCellsConfig(rootElement));
        SimulationConfig.put("SimulationDetails", generateSimulationDetailsConfig(rootElement));
        SimulationConfig.put("GridConfig", generateCellGridConfig(rootElement));
        return SimulationConfig;
    }
    
    //TODO-refactor either into Reflection or to make more concise or change to be uniform like make cell xml tags comply
    public Map<String, String> generateInitCellsConfig(Element rootElement) {
        Map<String, String> initCells = new HashMap<String, String>();
        NodeList cells = rootElement.getElementsByTagName("cell");
        for (int i = 0; i < cells.getLength(); i++) {
            String[] cellData = cells.item(i).getFirstChild().getNodeValue().split("_");
            initCells.put(cellData[1] + "_" + cellData[2], cellData[0]);
        }
        printMap(initCells);
        return initCells;
    }
    
    public Map<String, String> generateSimulationDetailsConfig(Element rootElement) {
        Map<String, String> SimulationDetailsConfig = new HashMap<String, String>();
        NodeList cells = rootElement.getElementsByTagName("SimulationDetails").item(0).getChildNodes();
        for (int i = 0; i < cells.getLength(); i++) {
            if(cells.item(i).getFirstChild() != null) {
                String val = cells.item(i).getFirstChild().getNodeValue();
                String tag = cells.item(i).getNodeName(); //.getFirstChild()
                SimulationDetailsConfig.put(tag, val);
            }
        }
        printMap(SimulationDetailsConfig);
        return SimulationDetailsConfig;
    }
    
    public Map<String, String> generateCellGridConfig(Element rootElement) {
        Map<String, String> cellGridConfig = new HashMap<String, String>();
        NodeList cells = rootElement.getElementsByTagName("GridConfig").item(0).getChildNodes();
        for (int i = 0; i < cells.getLength(); i++) {
            if(cells.item(i).getFirstChild() != null) {
                String val = cells.item(i).getFirstChild().getNodeValue();
                String tag = cells.item(i).getNodeName();
                cellGridConfig.put(tag, val);
            }
        }
        printMap(cellGridConfig);
        return cellGridConfig;
    }
    
    public String getSimulationType(Element rootElement) {
        return rootElement.getElementsByTagName("SimulationType").item(0).getAttributes().getNamedItem("Simulation").getNodeValue();
    }
    
    public NodeList getTagValues(String tagName, Element rootElement) {
        return rootElement.getElementsByTagName(tagName);
    }
    
    public String getTagValue(String tagName, Element rootElement) {
        return rootElement.getAttribute(tagName);
        //return rootElement.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }
    
    public Double getDoubleValue(String tagName, Element rootElement) {
        return Double.parseDouble(getTagValue(tagName, rootElement));
    }
    
    public int getIntValue(String tagName, Element rootElement) {
        return Integer.parseInt(getTagValue(tagName, rootElement));
    }
    
    /**
     * Gets the root element in an XML file.
     *
     * @param xmlFilename the location of the xmlFile
     * @return the root element in the xmlFile
     */
    public Element getRootElement (String xmlFilename) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFilename);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLParserException(e);
        }
    }

    // Helper method to do the boilerplate code needed to make a documentBuilder.
    private static DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLParserException(e);
        }
    }
}
