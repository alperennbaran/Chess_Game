import javax.swing.*;

public class King extends Piece{
    private String type = "S";
    private final String path;

    private boolean alive = true;
    private boolean castlingDone = false;   //  Eger sah hareket etmis ise oyuncu rok yapamamali.
    public King(boolean white){
        super(white);
        if (isWhite()){
            path="src/images/wk.png";
        }else path="src/images/bk.png";
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

            return board.getCell(5, 7).getPiece() == null && board.getCell(7, 7).getPiece() instanceof Rook && !((Rook) board.getCell(7, 7).getPiece()).hasCastlingDone();


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
        //Beyaz şah tehdit altında ise
        if (isWhiteUnderThreat(board)){

            //önce tehdit eden taşı yiyebiliyor muyuz bakılır
            //yenilmiyorsa şahın gidecek yeri kaldı mı diye bakılır


            int wX = whiteKingsPosition(board).getX();
            int wY = whiteKingsPosition(board).getY();
            Piece wKing = whiteKingsPosition(board).getPiece();
            Cell king = new Cell(wX,wY,wKing);

            for (int iy=0; iy<8; iy++){
                for (int ix=0; ix<8; ix++){
                    //eğer taş varsa ve beyaz değilse
                    if ( !(board.getCell(ix,iy).getPiece()==null)  &&  !board.getCell(ix,iy).getPiece().isWhite()){
                        Piece minator = board.getCell(ix,iy).getPiece();
                        Cell minatorCell = board.getCell(ix,iy);
                        //ve şahı tehdit ediyorsa
                        if (minator.canMove(minatorCell,king,board)){

                            for (int i =0 ; i<8 ; i++){
                                for (int j=0; j<8; j++){
                                    //herhangi bir beyaz taş tehdit eden taşı yiyebiliyor mu
                                    if (!(board.getCell(i,j).getPiece()==null)  &&  board.getCell(i,j).getPiece().isWhite()){
                                        Piece white = board.getCell(i,j).getPiece();
                                        Cell whiteCell = board.getCell(i,j);
                                        if (white.canMove(whiteCell,minatorCell,board)){ //yiyebiliyorsa

                                            minatorCell.setPiece(white);
                                            // yedikten sonraki durum için isCheckmate tekrar kontrol
                                            boolean checkmate = isCheckmate(board);

                                            minatorCell.setPiece(minator);
                                            return checkmate;

                                        }
                                    }
                                }
                            }


                            //şahın etrafındaki 8 kare için kontrol
                            Cell[] cells={new Cell(wX-1,wY-1,null),
                                    new Cell(wX,wY-1,null),
                                    new Cell(wX+1,wY-1,null),
                                    new Cell(wX-1,wY,null),
                                    new Cell(wX+1,wY,null),
                                    new Cell(wX-1,wY+1,null),
                                    new Cell(wX,wY+1,null),
                                    new Cell(wX+1,wY+1,null)};

                            boolean[] booleans = new boolean[]{true,true,true,true,true,true,true,true};

                            for (int i=0;i<8;i++){
                                //şah o kareye gidebiliyor mu
                                if (wKing.canMove(king,cells[i],board)){
                                    //gidebiliyor ama orası başka bir taş tarafından tehdit ediliyor mu
                                    //her karedeki taşı al
                                    for (int yy=0; yy<8; yy++){
                                        for (int xx=0; xx<8; xx++){
                                            //eğer taş varsa ve beyaz değilse
                                            if ( !(board.getCell(xx,yy).getPiece()==null)  &&  !board.getCell(xx,yy).getPiece().isWhite()){
                                                Piece minator2 = board.getCell(xx,yy).getPiece();
                                                Cell minator2Cell = board.getCell(xx,yy);
                                                //ve şahın gidebileceği yeri tehdit ediyorsa
                                                if (minator2.canMove(minator2Cell,king,board)){
                                                    booleans[i]=false; // şah o kareye gidemez
                                                }
                                            }
                                        }
                                    }
                                }else booleans[i]=false; //gidemiyor
                            }

                            for (int i=0;i<8;i++){
                                if (booleans[i]){  //8 kareden herhangi birine gidebiliyorsa
                                    return false;  //mat yok
                                }
                            }return true;   //hiçbirine gidemiyorsa mat

                        }
                    }
                }
            }
        }else if (isBlackUnderThreat(board)){   //siyah şah tehdit altında ise


            //önce tehdit eden taşı yiyebiliyor muyuz bakılır
            //yenilmiyorsa şahın gidecek yeri kaldı mı diye bakılır


            int bX = blackKingsPosition(board).getX();
            int bY = blackKingsPosition(board).getY();
            Piece bKing = blackKingsPosition(board).getPiece();
            Cell king = new Cell(bX,bY,bKing);


            for (int iy=0; iy<8; iy++){
                for (int ix=0; ix<8; ix++){
                    //eğer taş varsa ve beyazsa
                    if ( !(board.getCell(ix,iy).getPiece()==null)  &&  board.getCell(ix,iy).getPiece().isWhite()){
                        Piece minator = board.getCell(ix,iy).getPiece();
                        Cell minatorCell = board.getCell(ix,iy);
                        //ve şahı tahdit ediyorsa
                        if (minator.canMove(minatorCell,king,board)){

                            for (int i =0 ; i<8 ; i++){
                                for (int j=0; j<8; j++){
                                    //herhangi bir siyah taş tehdit eden taşı yiyebiliyor mu
                                    if (!(board.getCell(i,j).getPiece()==null)  &&  !board.getCell(i,j).getPiece().isWhite()){
                                        Piece black = board.getCell(i,j).getPiece();
                                        Cell blackCell = board.getCell(i,j);
                                        if (black.canMove(blackCell,minatorCell,board)){

                                            minatorCell.setPiece(black);
                                            // yedikten sonraki durum için isCheckmate tekrar kontrol
                                            boolean checkmate = isCheckmate(board);

                                            minatorCell.setPiece(minator);
                                            return checkmate;
                                        }
                                    }
                                }
                            }

                            //yiyemiyorrsa şahın etrafındaki 8 kare için kontrol
                            Cell[] cells={new Cell(bX-1,bY-1,null),
                                    new Cell(bX,bY-1,null),
                                    new Cell(bX+1,bY-1,null),
                                    new Cell(bX-1,bY,null),
                                    new Cell(bX+1,bY,null),
                                    new Cell(bX-1,bY+1,null),
                                    new Cell(bX,bY+1,null),
                                    new Cell(bX+1,bY+1,null)};

                            boolean[] booleans = new boolean[]{true,true,true,true,true,true,true,true};

                            for (int i=0;i<8;i++){
                                //şah o kareye gidebiliyor mu
                                if (bKing.canMove(king,cells[i],board)){
                                    //her karedeki taşı al
                                    for (int yy=0; yy<8; yy++){
                                        for (int xx=0; xx<8; xx++){
                                            //eğer taş varsa ve beyazsa
                                            if (!(board.getCell(xx,yy).getPiece()==null) && board.getCell(xx,yy).getPiece().isWhite()){
                                                Piece minator2 = board.getCell(xx,yy).getPiece();
                                                Cell minator2Cell = board.getCell(xx,yy);
                                                //ve şahın gidebileceği yeri tehdit ediyorsa
                                                if (minator2.canMove(minator2Cell,king,board)){
                                                    booleans[i]=false; //siyah şah o kareye gidemez
                                                }
                                            }
                                        }
                                    }
                                }else booleans[i]=false; //gidemiyor
                            }

                            for (int i=0;i<8;i++){
                                if (booleans[i]){
                                    return false;
                                }
                            }return true;

                        }
                    }
                }
            }
        }
        //iki renk de tehdit altında değiğlse
        return false;
    }
}