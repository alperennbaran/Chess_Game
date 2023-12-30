public class DbPlayer {
    private int id;
    private Object player1;
    private Object player2;
    private Object winner;
    private int moveNumber;

    public DbPlayer(int id, Object player1, Object player2, Object winner, int moveNumber) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.moveNumber = moveNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getPlayer1() {
        return player1;
    }

    public Object getPlayer2() {
        return player2;
    }

    public Object getWinner() {
        return winner;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }
    
}
