package map;

public class Cell {

    /**
     * Type de la cellule
     */
    private CellType _type;

    public Cell(CellType type) {
        _type = type;
    }

    public CellType getType() {
        return _type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Cell cell = (Cell) o;
        if(!_type.equals(cell._type)){
            System.out.println(_type + ", " + cell._type);
            return false;
        }
        return true;
    }

}
