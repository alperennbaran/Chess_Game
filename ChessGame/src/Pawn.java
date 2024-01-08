import javax.swing.*;
import java.nio.file.Paths;

public class Pawn extends Piece {
    private String type = "P";
    private final String path;
    private boolean moved = false;  //Eger hareket etmemis ise ilk hareketinde iki kare ilerleyebilme opsiyonu olmali
    private boolean recentlyMoved = false;


    @Override
    public Icon getIcon() {
        return new ImageIcon(path);
    }

    @Override
    public String getPath() {
        return path;
    }


    public Pawn(boolean white) {
        super(white);
        if (isWhite()) {
            path = Paths.get("src", "images", "wp.png").toString();
        }else path = Paths.get("src", "images", "bp.png").toString();
    }

    public boolean hasMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public String getType() {
        return this.type;
    }

    public boolean isRecentlyMoved(){
        return this.recentlyMoved;
    }

    public void setRecentlyMoved(boolean recentlyMoved) {
        this.recentlyMoved = recentlyMoved;
    }

    //  Piyonun secili kareye hareket edip edemeyecegini kontrol eden fonksiyon.
    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {


        //  Beyazsa bir kare ilerleme durumu:
        if (start.getPiece().isWhite() && (destination.getY() - start.getY() == -1) && destination.getX() == start.getX()){
            return destination.getPiece() == null;

            //  Siyahsa bir kare ilerleme durumu:
        } else if (!start.getPiece().isWhite() && (destination.getY() - start.getY() == 1) && start.getX() == destination.getX()) {
            return destination.getPiece() == null;

            //  Beyaz icin carprazindaki tasi yeme durumu:
        } else if (start.getPiece().isWhite() && Math.abs(destination.getX() - start.getX()) == 1 && destination.getY() - start.getY() == -1) {
            if (destination.getPiece() == null ){
                return false;
            }else {
                return !destination.getPiece().isWhite();
            }

            //  Siyah icin carprazindaki tasi yeme durumu:
        }else if (!start.getPiece().isWhite() && Math.abs(destination.getX() - start.getX()) == 1 && destination.getY() - start.getY() == 1){
            if (destination.getPiece() == null){
                return false;
            }else {
                return destination.getPiece().isWhite();
            }

            //  Beyaz icin iki kare ilerleme durumu:
        }else if (!hasMoved() && start.getPiece().isWhite() && destination.getY() - start.getY() == -2 && destination.getX() == start.getX()){
            return board.getCell(start.getX(), start.getY() - 1).getPiece() == null && board.getCell(start.getX(), start.getY() - 2).getPiece() == null;

            //  Siyah icin iki kare ilerleme durumu:
        }else if (!hasMoved() && !start.getPiece().isWhite() && destination.getY() - start.getY() == 2 && destination.getX() == start.getX()){
            return board.getCell(start.getX(), start.getY() + 1).getPiece() == null && board.getCell(start.getX(), start.getY() + 2).getPiece() == null;

        }
        return false;
    }
}