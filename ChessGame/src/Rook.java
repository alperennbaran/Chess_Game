import javax.swing.*;

public class Rook extends Piece {
    private String type = "R";
    private final String path;
    private boolean castlingDone = false;   //Rok yapip yapmama durununu kontrol etmek icin degisken.
    public Rook(boolean white) {
        super(white);
        if (isWhite()){
            path="src/images/wr.png";
        }else path="src/images/br.png";
    }
    @Override
    public Icon getIcon() {
        return new ImageIcon(path);
    }

    @Override
    public String getPath() {
        return path;
    }

    public String getType(){
        return this.type;
    }
    public void setCastlingDone(boolean castlingDone){
        this.castlingDone = castlingDone;
    }
    public boolean hasCastlingDone(){
        return this.castlingDone;
    }
    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {

        // Hedef hücrede aynı renkte başka bir taş varsa
        if (start.getPiece().isWhite() && destination.getPiece() != null && destination.getPiece().isWhite()) {
            return false;
        }

        int diffX = Math.abs(destination.getX() - start.getX());
        int diffY = Math.abs(destination.getY() - start.getY());

        // sadece x ve ya sadece y koordinatı değişeceği için biri sabit
        if ((diffX == 0 && diffY > 0) || (diffX > 0 && diffY == 0)) {
            //dikey hareketinde
            if (diffX == 0) {
                int yDir = (destination.getY() - start.getY()) > 0 ? 1 : -1;
                int y = start.getY() + yDir;

                // yol boyunca taş var mı
                while (y != destination.getY()) {
                    if (board.getCell(start.getX(), y).getPiece() != null) {
                        return false; // yolda taş varsa gidemez
                    }
                    y += yDir;
                }
                return true;
            }
            // yatay hareket
            if (diffY == 0){
                int xDir = (destination.getX() - start.getX()) > 0 ? 1 : -1;
                int x = start.getX() + xDir;

                // yol boyunca taş var mı
                while (x != destination.getX()) {
                    if (board.getCell(x, start.getY()).getPiece() != null) {
                        return false; // yolda taş varsa gidemez
                    }
                    x += xDir;
                }
                return true;
            }
        }
        return false;
    }
}