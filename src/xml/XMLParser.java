package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simulation.FireSimulation;
import simulation.ForagingAntsSimulation;
import simulation.GameOfLifeSimulation;
import simulation.PredatorPreySimulation;
import simulation.SegregationSimulation;
import simulation.Simulation;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Handles parsing XML files by returning the root elements.
 *
 */
public class XMLParser {
    private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();

    public Simulation createSimulation (Element rootElement) {
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
        else if(simulationType.equals("Segregation")){
            simulation = new SegregationSimulation(generateSimulationConfig(rootElement));
        }
        else{
            simulation = new ForagingAntsSimulation(generateSimulationConfig(rootElement));
        }
        return simulation;
    }

    public void printMap (Map<String, String> input) {
        for (Map.Entry<String, String> cell : input.entrySet()) {
            System.out.println(cell.getKey() + " " + cell.getValue());
        }
    }

    private Map<String, Map<String, String>> generateSimulationConfig (Element rootElement) {
        Map<String, Map<String, String>> simulationConfig =
                new HashMap<String, Map<String, String>>();
        NodeList sections = getChildNodesOfTag("Simulation", rootElement);
        for (int i = 0; i < sections.getLength(); i++) {
            if (sections.item(i).getFirstChild() != null) {
                simulationConfig
                        .put(sections.item(i).getNodeName(),
                             generateSectionConfig(rootElement, sections.item(i).getNodeName()));
            }
        }
        return simulationConfig;

    }

    private Map<String, String> generateSectionConfig (Element rootElement, String section) {
        Map<String, String> sectionMap = new HashMap<String, String>();
        NodeList items = getChildNodesOfTag(section, rootElement);
        for (int i = 0; i < items.getLength(); i++) {
            if (items.item(i).getFirstChild() != null) {
                String keyTag = items.item(i).getNodeName();
                String valSimData = items.item(i).getFirstChild().getNodeValue();
                sectionMap.put(keyTag, valSimData);
            }
        }
        // printMap(initCells);
        return sectionMap;
    }

    private String getSimulationType (Element rootElement) {
        return rootElement.getElementsByTagName("Simulation").item(0).getAttributes()
                .getNamedItem("SimulationType").getNodeValue();
    }

    public NodeList getChildNodesOfTag (String tagName, Element rootElement) {
        return rootElement.getElementsByTagName(tagName).item(0).getChildNodes();
    }
    
    /*
     * public String getTagValue (String tagName, Element rootElement) {
     * return rootElement.getAttribute(tagName);
     * // return rootElement.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
     * }
     * 
     * public Double getDoubleValue (String tagName, Element rootElement) {
     * return Double.parseDouble(getTagValue(tagName, rootElement));
     * }
     * 
     * public int getIntValue (String tagName, Element rootElement) {
     * return Integer.parseInt(getTagValue(tagName, rootElement));
     * }
     */

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
