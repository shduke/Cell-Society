package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
                      ORTHOGONAL("MIDLEFT", "TOPMID", "BOTTOMMID", "MIDRIGHT"),
                      DIAGONALS("TOPLEFT", "BOTTOMLEFT", "TOPRIGHT", "BOTTOMRIGHT");

    private List<Coordinate> neighbors;

    Neighbor (String ... args) {
        ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();
        for (String arg : args) {
            neighbors.add(Positions.valueOf(arg).getCoordinate());
        }
        this.neighbors = Collections.unmodifiableList(neighbors);
    }

    public List<Coordinate> getNeighbors () {
        return neighbors;
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
        Coordinate coordinate;

        Positions (int x, int y) {
            this.coordinate = new Coordinate(x, y);
        }

        private Coordinate getCoordinate () {
            return coordinate;
        }
    }
}
