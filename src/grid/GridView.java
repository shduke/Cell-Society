package grid;

import com.sun.javafx.css.Size;
import cell.Cell;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class GridView {
    private enum CellShape {
                            SQUARE("square") {
                                @Override
                                public void displayGrid (Dimension2D gridSize, Grid grid) {
                                    double cellWidth = gridSize.getWidth() / grid.getNumRows();
                                    double cellHeight = gridSize.getHeight() / grid.getNumRows();
                                    for (int r = 0; r < grid.getNumRows(); r++) {
                                        for (int c = 0; c < grid.getNumColumns(); c++) {
                                            Double xView = r * cellWidth;
                                            Double yView = c * cellHeight;
                                            grid.getCell(r, c).setMyNode(new Rectangle(cellWidth,
                                                                                       cellHeight));
                                            grid.getCell(r, c)
                                                    .setMyCoordinate(new Point2D(xView, yView));
                                        }
                                    }
                                }
                            };

        private String cellShape;

        CellShape (String cellShape) {
            cellShape = cellShape;
        }

        public String getCellShape () {
            return cellShape;
        }

        abstract public void displayGrid (Dimension2D gridSize, Grid grid);
    }

    CellShape cellShape;
    Dimension2D gridSize;

    public GridView (Dimension2D gridSize, String cellShapeType, Grid grid) {
        this.gridSize = gridSize;
        this.cellShape = CellShape.valueOf(cellShapeType.toUpperCase());
        cellShape.displayGrid(gridSize, grid);
    }

}
