package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Sean Hudson
 *
 */
public enum Neighbor {
                      SQUARE(
                             "TOPLEFT",
                             "MIDLEFT",
                             "BOTTOMLEFT",
                             "TOPMID",
                             "BOTTOMMID",
                             "TOPRIGHT",
                             "MIDRIGHT",
                             "BOTTOMRIGHT"),
                      DIAGONAL("TOPLEFT", "TOPRIGHT", "BOTTOMRIGHT", "BOTTOMLEFT"),
                      CARDINAL("MIDLEFT", "TOPMID", "BOTTOMMID", "MIDRIGHT"),
                      SQUAREORTHOGONAL("MIDLEFT", "TOPMID", "BOTTOMMID", "MIDRIGHT"),
                      HEXAGON(
                              "MIDLEFT",
                              "TOPMID",
                              "BOTTOMMID",
                              "MIDRIGHT",
                              "BOTTOMLEFT",
                              "BOTTOMRIGHT"),
                      HEXAGONEVEN(
                                  "MIDLEFT",
                                  "MIDRIGHT",
                                  "TOPMID",
                                  "BOTTOMLEFT",
                                  "BOTTOMRIGHT",
                                  "BOTTOMMID"),
                      HEXAGONORTHOGONAL(
                                        "MIDLEFT",
                                        "TOPMID",
                                        "BOTTOMMID",
                                        "MIDRIGHT",
                                        "TOPLEFT",
                                        "TOPRIGHT");

    private List<Coordinate> myNeighbors;

    Neighbor (String ... args) {
        ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();
        for (String arg : args) {
            neighbors.add(Positions.valueOf(arg).getCoordinate());
        }
        this.myNeighbors = Collections.unmodifiableList(neighbors);
    }

    public List<Coordinate> getNeighbors () {
        return myNeighbors;
    }

    private enum Positions {
                            TOPLEFT(-1, -1),
                            MIDLEFT(-1, 0),
                            BOTTOMLEFT(-1, 1),
                            TOPMID(0, -1),
                            BOTTOMMID(0, 1),
                            TOPRIGHT(1, -1),
                            MIDRIGHT(1, 0),
                            BOTTOMRIGHT(1, 1);
        private Coordinate myCoordinate;

        Positions (int x, int y) {
            this.myCoordinate = new Coordinate(x, y);
        }

        private Coordinate getCoordinate () {
            return myCoordinate;
        }
    }
}
