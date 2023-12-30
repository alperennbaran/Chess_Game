import javax.swing.*;

 // Daha sonra olusturulacak 32 tas icin blueprint.

public abstract class Piece {
    private boolean white;
    private boolean alive = true;
    private String type;
    public abstract Icon getIcon();
    public abstract String getPath();
    public Piece(boolean white){
        this.white = white;
    }
    public boolean isWhite(){
        return this.white;
    }
    public boolean isAlive(){
        return this.alive;
    }
    public void setAlive(boolean alive){this.alive = alive;}
    public abstract String getType();
    public abstract boolean canMove(Cell start, Cell destination, Board board);
}