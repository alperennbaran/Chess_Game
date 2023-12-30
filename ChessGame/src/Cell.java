/*
 * Her tasin nerede oldugu bilgisini tutmak yerine her Cell'deki tas bilgisi tutulacak.
 * Uzerinde tas varsa Piece attribute'una eklenecek.
 * Cell'de tas yoksa Piece attribute'u null olacak.
 */
public class Cell {
    private int x;
    private  int y;
    private Piece piece;

    public Cell(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Piece getPiece(){
        return this.piece;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }
}