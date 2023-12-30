/*
 * Oyun tahtasinda 64 Cell bulunacak ve bu 64 Cell X ve Y ekseninde Cell[][] dizisinide saklanacak.
 * */
public class Board {

    //  Hücrelerin tutulacagi hucreler dizisinin baslatilmasi:
    private Cell[][] cells = new Cell[8][8];

    public Board(){
        this.resetBoard();
    }
    public Cell getCell(int x, int y){
        return this.cells[y][x];
    }

    public Cell getCell(int id) {
        int row = id / 8;
        int col = id % 8;

        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            return this.cells[row][col];
        } else {
            throw new IllegalArgumentException("hatalı id " + id);
        }
    }


    public void resetBoard(){

        //  Siyah taslarin baslatilmasi
        //    y  x
        cells[0][0] = new Cell(0, 0, new Rook(false));
        cells[0][1] = new Cell(1, 0, new Knight(false));
        cells[0][2] = new Cell(2, 0, new Bishop(false));
        cells[0][3] = new Cell(3, 0, new Queen(false));
        cells[0][4] = new Cell(4, 0, new King(false));
        cells[0][5] = new Cell(5, 0, new Bishop(false));
        cells[0][6] = new Cell(6, 0, new Knight(false));
        cells[0][7] = new Cell(7, 0, new Rook(false));

        cells[1][0] = new Cell(0, 1, new Pawn(false));
        cells[1][1] = new Cell(1, 1, new Pawn(false));
        cells[1][2] = new Cell(2, 1, new Pawn(false));
        cells[1][3] = new Cell(3, 1, new Pawn(false));
        cells[1][4] = new Cell(4 ,1, new Pawn(false));
        cells[1][5] = new Cell(5, 1, new Pawn(false));
        cells[1][6] = new Cell(6, 1, new Pawn(false));
        cells[1][7] = new Cell(7, 1, new Pawn(false));


        //  Beyaz taslarin baslatilmasi.
        cells[6][0] = new Cell(0, 6, new Pawn(true));
        cells[6][1] = new Cell(1, 6, new Pawn(true));
        cells[6][2] = new Cell(2, 6, new Pawn(true));
        cells[6][3] = new Cell(3, 6, new Pawn(true));
        cells[6][4] = new Cell(4, 6, new Pawn(true));
        cells[6][5] = new Cell(5, 6, new Pawn(true));
        cells[6][6] = new Cell(6, 6, new Pawn(true));
        cells[6][7] = new Cell(7, 6, new Pawn(true));

        cells[7][0] = new Cell(0, 7, new Rook(true));
        cells[7][1] = new Cell(1, 7, new Knight(true));
        cells[7][2] = new Cell(2, 7, new Bishop(true));
        cells[7][3] = new Cell(3, 7, new Queen(true));
        cells[7][4] = new Cell(4, 7, new King(true));
        cells[7][5] = new Cell(5, 7, new Bishop(true));
        cells[7][6] = new Cell(6, 7, new Knight(true));
        cells[7][7] = new Cell(7, 7, new Rook(true));

        //  Kalan hucrelerin tas olmadan baslatilmasi
        for (int y = 2; y < 6; y++){
            for (int x = 0; x < 8; x++){
                cells[y][x] = new Cell(x, y, null);
            }
        }
    }
}