import javax.swing.*;

public class Bishop extends Piece {
    private final String type = "B";
    private final String path;
    public Bishop(boolean white) {
        super(white);
        if (isWhite()){
            path="src/images/wb.png";
        }else path="src/images/bb.png";
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

        // Hedef hücrede aynı renkte başka bir taş varsa
        if ( destination.getPiece() != null ){
            if (start.getPiece().isWhite()==destination.getPiece().isWhite()) {
                return false;
            }
        }

        //Fil X'de ne kadar yol alıyorsa Y'de de o kadar yol alır onun için değişken
        int diffX = Math.abs(destination.getX() - start.getX());
        int diffY = Math.abs(destination.getY() - start.getY());

        // Filin çapraz hareketlerinin kontrolü
        if (diffX == diffY) {

            //hangi çapraza doğru gitmek istediğine göre x y ye eklenecek sayıyı belirlemek için
            int xDir = (destination.getX() - start.getX()) > 0 ? 1 : -1;
            int yDir = (destination.getY() - start.getY()) > 0 ? 1 : -1;
            int x = start.getX() + xDir;
            int y = start.getY() + yDir;

            // Yol boyunca herhangi bir taş var mı kontrol
            while (x != destination.getX() && y != destination.getY()) {
                if (board.getCell(x, y).getPiece() != null) {
                    System.out.println("yolda taş var");
                    return false; // Yolda bir taş varsa gidemez
                }
                x += xDir;
                y += yDir;
            }

            return true;
        }
        return false; // gitmek istediği konum çaprazında değilse gidemez
    }
}