public class Player {
    private String name;
    public boolean whiteSide;
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    Player(boolean whiteSide){
        this.whiteSide = whiteSide;
    }
    public boolean isWhiteSide(){
        return this.whiteSide;
    }
}
