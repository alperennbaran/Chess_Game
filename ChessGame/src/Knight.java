import javax.swing.*;

public class Knight extends Piece {
    private String type = "K";
    private final String path;
    public Knight(boolean white) {
        super(white);
        if (isWhite()){
            path="src/images/wn.png";
        }else path="src/images/bn.png";
    }
    @Override
    public String getType(){
        return this.type;
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(path);
    }
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {

        if (destination.getPiece() == null) {
            if ((Math.abs(destination.getX() - start.getX()) == 1 && Math.abs(destination.getY() - start.getY()) == 2)
                    || (Math.abs(destination.getX() - start.getX()) == 2 && Math.abs(destination.getY() - start.getY()) == 1)) {
                return true;  // L şeklindeki hareketi kontrol eder
            }
        }

        // Hedef hücrede aynı renkte başka bir taş varsa
        else if (destination.getPiece() != null )
            if (start.getPiece().isWhite() == destination.getPiece().isWhite())
                return false;
            else if (start.getPiece().isWhite() != destination.getPiece().isWhite())
                if ((Math.abs(destination.getX() - start.getX()) == 1 && Math.abs(destination.getY() - start.getY()) == 2)
                        || (Math.abs(destination.getX() - start.getX()) == 2 && Math.abs(destination.getY() - start.getY()) == 1))
                    return true;

        return false;
    }
}