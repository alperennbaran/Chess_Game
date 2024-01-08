import javax.swing.*;
import java.nio.file.Paths;

public class King extends Piece{
    private String type = "S";
    private final String path;
    private boolean alive = true;
    private boolean castlingDone = false;   //  Eger sah hareket etmis ise oyuncu rok yapamamali.
    public King(boolean white){
        super(white);
        if (isWhite()){
            path = Paths.get("src", "images", "wk.png").toString();
        }else path = Paths.get("src", "images", "bk.png").toString();
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
    public boolean hasCastlingDone(){
        return this.castlingDone;
    }
    public void setCastlingDone(boolean castlingDone){
        this.castlingDone = castlingDone;
    }
    @Override
    public boolean canMove(Cell start, Cell destination, Board board){
        if ( destination.getPiece() != null ){
            if (start.getPiece().isWhite()==destination.getPiece().isWhite()) {
                return false;
            }
        }
        //  Sah X ve Y ekseninde en fazla 1er kare ilerleyebilmeli.
        if (Math.abs(start.getX() - destination.getX()) <= 1 && Math.abs(start.getY() - destination.getY()) <= 1){
            //  Hareket edecek tasin gidecegi konumda ayni renk tas var ise o konuma gidememeli.
            if (start.getPiece().isWhite() && destination.getPiece() != null && destination.getPiece().isWhite()){
                return false;
            }
            else{
                return true;
            }
            //  Sah beyaz ise rok yapma durumu.
        }else if(start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 6 && destination.getY() == 7) {

            return isCastlingMove(start, destination, board);
        }else if(start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 2 && destination.getY() == 7){

            return isCastlingMove(start, destination, board);

            //  Sah siyah ise rok yapma durumu.
        }else if (!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 6 && destination.getY() == 0){

            return isCastlingMove(start, destination, board);
        } else if (!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 2 && destination.getY() == 0) {

            return isCastlingMove(start, destination, board);
        }else
            return false;
    }


    //  Girilen parametrelere gore Sah'in yaptigi hareketin rok olup olmadigini kontrol eden fonksiyon.
    public boolean isCastlingMove(Cell start, Cell destination, Board board){
        if (start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 6 && destination.getY() == 7){

            return board.getCell(5, 7).getPiece() == null && board.getCell(7, 7).getPiece() instanceof Rook &&
                    !((Rook) board.getCell(7, 7).getPiece()).hasCastlingDone();


        } else if (start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 2 && destination.getY() == 7) {

            return board.getCell(3, 7).getPiece() == null && board.getCell(2, 7).getPiece() == null &&
                    board.getCell(1, 7).getPiece() == null && board.getCell(0, 7).getPiece() instanceof Rook &&
                    !((Rook) board.getCell(0, 7).getPiece()).hasCastlingDone();

        } else if (!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 6 && destination.getY() == 0) {

            return board.getCell(5, 0).getPiece() == null && board.getCell(7, 0).getPiece() instanceof Rook
                    && !((Rook) board.getCell(7, 0).getPiece()).hasCastlingDone();

        }else if(!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 2 && destination.getY() == 0){

            return board.getCell(3, 0).getPiece() == null && board.getCell(2, 0).getPiece() == null &&
                    board.getCell(1, 0).getPiece() == null && board.getCell(0, 0).getPiece() instanceof Rook &&
                    !((Rook) board.getCell(0, 0).getPiece()).hasCastlingDone();

        }else
            return false;
    }


    //  Beyaz Sah'in yerini bulan fonksiyon:
    public Cell whiteKingsPosition(Board board){
        Cell whiteKingsPosition = new Cell(0, 0, null);
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <=7; x++){
                if (board.getCell(x, y).getPiece() instanceof King && board.getCell(x, y).getPiece().isWhite()) {
                    whiteKingsPosition = board.getCell(x, y);
                    break;
                }
            }
        }
        return whiteKingsPosition;
    }


    //  Siyah Sah'in yerini bulan fonksiyon:
    public Cell blackKingsPosition(Board board){
        Cell blackKingsPosition = new Cell(0, 0, null);
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <=7; x++){
                if (board.getCell(x, y).getPiece() instanceof King && !board.getCell(x, y).getPiece().isWhite()) {
                    blackKingsPosition = board.getCell(x, y);
                    break;
                }
            }
        }
        return blackKingsPosition;
    }


    //  Beyaz Sah'in tehdit altinda olup olmadigini kontrol eden fonksiyon:
    public boolean isWhiteUnderThreat(Board board){

        //  Beyaz Sah'in yerinin bulunmasi:
        Cell whiteKingsPosition = whiteKingsPosition(board);


        //  Sol üstten baslayarak tum hucreleri kontrol eder ve kontrol ettigi hucredeki tas siyahsa
        //  Sah'in hucresine gidip gidemeyecegini kontrol eder.Gidebiliyorsa true dondurur.
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <= 7; x++){
                if (board.getCell(x, y).getPiece() != null &&
                        board.getCell(x, y).getPiece().canMove(board.getCell(x, y), whiteKingsPosition, board)){
                    return true;
                }
            }
        }
        return false;
    }

    //  Siyah Sah'in tehdit altinda olup olmadigini kontrol eden fonksiyon:
    public boolean isBlackUnderThreat(Board board){

        //  Sah'in yerinin bulunmasi:
        Cell blackKingsPosition = blackKingsPosition(board);
        Cell controlCell;
        //  Sol üstten baslayarak tum hucreleri kontrol eder ve kontrol ettigi hucredeki tas beyazsa
        //  Sah'in hucresine gidip gidemeyecegini kontrol eder.Gidebiliyorsa true dondurur.
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <= 7; x++){
                controlCell = board.getCell(x, y);
                if (controlCell.getPiece() != null && controlCell.getPiece().isWhite() && controlCell.getPiece().canMove(controlCell, blackKingsPosition, board)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isCheckmate(Board board){

        if (isWhiteUnderThreat(board)){    //Beyaz şah tehdit altında ise

            //önce tehdit eden taşı alabiliyor muyuz bakılır
            //alınamıyorsa önüne bir taş koyabilir miyiz buna bakılır
            //koyulamıyorsa şahın gidecek yeri kaldı mı diye bakılır

            Cell kingCell = whiteKingsPosition(board);

            if (canTakeMinator(board,kingCell,true)){
                return false;
            } else if (canDefendThreat(board,kingCell,true)) {
                return false;
            } else if (canEscapeCheck(board,kingCell,true)) {
                return false;
            }else return true;

        }else if (isBlackUnderThreat(board)){   //siyah şah tehdit altında ise

            Cell kingCell = blackKingsPosition(board);

            if (canTakeMinator(board,kingCell,false)){
                return false;
            } else if (canDefendThreat(board,kingCell,false)) {
                return false;
            } else if (canEscapeCheck(board,kingCell,false)) {
                return false;
            }else return true;
        }
        //iki renk de tehdit altında değilse
        return false;
    }

    private Cell findMinator(Board board, Cell kingCell, boolean isWhite){
        for (int y =0 ; y<8 ; y++){
            for (int x = 0; x<8 ; x++){

                //bütün celllere bak eğer taş varsa ve bizden değilse
                if ( board.getCell(x,y).getPiece()!=null && board.getCell(x,y).getPiece().isWhite() != isWhite ){
                    Piece minator = board.getCell(x,y).getPiece();
                    Cell minatorCell = board.getCell(x,y);
                    // ve şahı tehdit ediyorsa
                    if (minator.canMove(minatorCell,kingCell,board)){
                        return minatorCell;
                    }
                }
            }
        }return null;
    }
    private boolean canTakeMinator(Board board, Cell kingCell, boolean isWhite){
        //herhangi bir taşımız tehdit eden taşı alabiliyor mu

        Cell minatorCell = findMinator(board,kingCell,isWhite);

        if (minatorCell!=null){
            for (int i =0 ; i<8 ; i++){
                for (int j=0; j<8; j++){

                    if (!(board.getCell(i,j).getPiece()==null)  &&  board.getCell(i,j).getPiece().isWhite() == isWhite){

                        Piece defender = board.getCell(i,j).getPiece();
                        Cell defenderCell = board.getCell(i,j);

                        if (defender.canMove(defenderCell,minatorCell,board)){   //alabiliyorsa
                            return true;
                        }
                    }
                }
            } return false;
        }
        return false;
    }

    private boolean canDefendThreat(Board board, Cell kingCell, boolean isWhite){

        // önüne bir taş koyarak engelleyebilir miyiz?

        Cell minatorCell = findMinator(board,kingCell,isWhite);

        if (minatorCell!=null){

            int diffX = Math.abs(minatorCell.getX() - kingCell.getX());
            int diffY = Math.abs(minatorCell.getY() - kingCell.getY());

            if ( diffX == 0 ){
                // minator dikeyde

                // aradaki cell sayısı kadar dönecek döngü
                for ( int y = Math.abs(kingCell.getY()-minatorCell.getY())-1 ; y>0 ; y--){
                    for (int i =0 ; i<8 ; i++){
                        for (int j=0; j<8; j++){

                            if ( board.getCell(i,j).getPiece()!=null  &&  board.getCell(i,j).getPiece().isWhite()==isWhite){

                                Piece defender = board.getCell(i,j).getPiece();
                                Cell defenderCell = board.getCell(i,j);
                                Cell searchCell = null;

                                if (!(defender instanceof King)){
                                    if (kingCell.getY()-minatorCell.getY()<0){
                                        //şah üstte
                                        searchCell = board.getCell(kingCell.getX(),kingCell.getY()+y);
                                    }else if (kingCell.getY()-minatorCell.getY()>0){
                                        //şah altta
                                        searchCell = board.getCell(kingCell.getX(),kingCell.getY()-y);
                                    }

                                    if (defender.canMove(defenderCell,searchCell,board)){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (diffY == 0) {
                // minator yatayda

                // aradaki cell sayısı kadar dönecek döngü
                for ( int x = Math.abs(kingCell.getX()-minatorCell.getX())-1 ; x>0 ; x--){

                    for (int i =0 ; i<8 ; i++){
                        for (int j=0; j<8; j++){
                            if (!(board.getCell(i,j).getPiece()==null)  &&  board.getCell(i,j).getPiece().isWhite()==isWhite){

                                Piece defender = board.getCell(i,j).getPiece();
                                Cell defenderCell = board.getCell(i,j);
                                Cell searchCell = null;


                                if (!(defender instanceof King)){
                                    if (kingCell.getX()-minatorCell.getX()>0){
                                        //şah sağda
                                        searchCell = board.getCell(kingCell.getX()-x,kingCell.getY());

                                    } else if (kingCell.getX()-minatorCell.getX()<0) {
                                        //şah solda
                                        searchCell = board.getCell(kingCell.getX()+x,kingCell.getY());
                                    }

                                    if (defender.canMove(defenderCell,searchCell,board)){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }

            } else if (diffX == diffY) {
                // minator çaprazda

                // aradaki cell sayısı kadar dönecek döngü
                for (int c = Math.abs(diffX) - 1; c > 0; c--) {

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {

                            if ( board.getCell(i, j).getPiece() != null && board.getCell(i, j).getPiece().isWhite()==isWhite) {

                                Piece defender = board.getCell(i, j).getPiece();
                                Cell defenderCell = board.getCell(i, j);
                                Cell searchcell = null;

                                if (!(defender instanceof King)){
                                    if (kingCell.getX() > minatorCell.getX() && kingCell.getY() < minatorCell.getY()) {
                                        // şah sağ üst
                                        searchcell = board.getCell(kingCell.getX() - c, kingCell.getY() + c);
                                    } else if (kingCell.getX() > minatorCell.getX() && kingCell.getY() > minatorCell.getY()) {
                                        // şah sağ alt
                                        searchcell = board.getCell(kingCell.getX() - c, kingCell.getY() - c);
                                    } else if (kingCell.getX() < minatorCell.getX() && kingCell.getY() < minatorCell.getY()) {
                                        // şah sol üst
                                        searchcell = board.getCell(kingCell.getX() + c, kingCell.getY() + c);
                                    } else if (kingCell.getX() < minatorCell.getX() && kingCell.getY() > minatorCell.getY()){
                                        // şah sol alt
                                        searchcell = board.getCell(kingCell.getX() + c, kingCell.getY() - c);
                                    }

                                    if (defender.canMove(defenderCell, searchcell, board)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }return false;
    }
    private Cell createCell(int x,int y,Board board){
        if (x > 0 && x <= 8 && y > 0 && y <= 8){
            return new Cell(x,y,board.getCell(x,y).getPiece());
        }else return new Cell(0,0,new King(isWhite()));
    }

    private boolean canEscapeCheck(Board board, Cell kingCell, boolean isWhite){

        //şahın etrafındaki 8 kare için kontrol

        int x = kingCell.getX();
        int y = kingCell.getY();

        Cell cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8;

        cell1 = createCell(x-1,y-1,board);
        cell2 = createCell(x,y-1,board);
        cell3 = createCell(x+1,y-1,board);
        cell4 = createCell(x-1,y,board);
        cell5 = createCell(x+1,y,board);
        cell6 = createCell(x-1,y+1,board);
        cell7 = createCell(x,y+1,board);
        cell8 = createCell(x+1,y+1,board);

        Cell[] cells = {cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8};

        boolean[] booleans = new boolean[]{true,true,true,true,true,true,true,true};

        for (int i=0;i<8;i++){
            //şah o kareye gidebiliyor mu
            if (kingCell.getPiece().canMove(kingCell,cells[i],board)){
                //gidebiliyor ama orası başka bir taş tarafından tehdit ediliyor mu?
                for (int yy=0; yy<8; yy++){
                    for (int xx=0; xx<8; xx++){
                        //eğer taş varsa ve bizden değilse

                        if ( !(board.getCell(xx,yy).getPiece()==null)  &&  board.getCell(xx,yy).getPiece().isWhite()!=isWhite){
                            Piece minator2 = board.getCell(xx,yy).getPiece();
                            Cell minator2Cell = board.getCell(xx,yy);
                            //ve şahın gidebileceği yeri tehdit ediyorsa
                            if (minator2.canMove(minator2Cell,cells[i],board)){
                                booleans[i]=false; // şah o kareye gidemez
                            }
                        }
                    }
                }
            }else booleans[i]=false; //gidemiyor
        }

        for (int i=0;i<8;i++){
            if (booleans[i]){  //8 kareden herhangi birine gidebiliyor
                return true;
            }
        }return false;   //hiçbirine gidemiyor
    }
}