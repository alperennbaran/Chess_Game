import javax.swing.*;

public class Queen extends Piece {
    private String type = "Q";
    private final String path;
    @Override
    public Icon getIcon() {
        return new ImageIcon(path);
    }
    @Override
    public String getPath() {
        return path;
    }

    public Queen(boolean white) {
        super(white);
        if (isWhite()){
            path="src/images/wq.png";
        }else path="src/images/bq.png";
    }

    public String getType(){
        return this.type;
    }
    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {

        // Hedef hücrede aynı renkte başka bir taş varsa
        if ( destination.getPiece() != null ){
            if (start.getPiece().isWhite()==destination.getPiece().isWhite()) {
                return false;
            }
        }

        int diffX = Math.abs(destination.getX() - start.getX());
        int diffY = Math.abs(destination.getY() - start.getY());

        // yatay, dikey veya çapraz hareket edebilir
        if ((diffX == 0 && diffY > 0) || (diffX > 0 && diffY == 0) || (diffX == diffY)) {
            // dikey hareket
            if (diffX == 0) {
                int yDir = (destination.getY() - start.getY()) > 0 ? 1 : -1;
                int y = start.getY() + yDir;

                // yolda taş var mı
                while (y != destination.getY()) {
                    if (board.getCell(start.getX(), y).getPiece() != null) {
                        return false;
                    }
                    y += yDir;
                }
                return true;
            }
            // yatay hareket
            else if (diffY == 0) {
                int xDir = (destination.getX() - start.getX()) > 0 ? 1 : -1;
                int x = start.getX() + xDir;

                // yolda taş var mı
                while (x != destination.getX()) {
                    if (board.getCell(x, start.getY()).getPiece() != null) {
                        return false;
                    }
                    x += xDir;
                }
                return true;
            }
            // çapraz hareket
            else {
                int xDir = (destination.getX() - start.getX()) > 0 ? 1 : -1;
                int yDir = (destination.getY() - start.getY()) > 0 ? 1 : -1;
                int x = start.getX() + xDir;
                int y = start.getY() + yDir;

                //yolda taş var mı
                while (x != destination.getX() && y != destination.getY()) {
                    if (board.getCell(x, y).getPiece() != null) {
                        return false;
                    }
                    x += xDir;
                    y += yDir;
                }
                return true;
            }
        }
        return false;
    }
}