import java.util.Scanner;

public class Move {
    private Cell start;
    private Cell destination;
    private Piece movingPiece;
    private Player player;
    public Move(Cell start, Cell destination, Player player){
        this.start = start;
        this.destination = destination;
        this.player = player;
        this.movingPiece = start.getPiece();
    }
    public Cell getStart(){
        return this.start;
    }
    public Cell getDestination(){
        return this.destination;
    }

    //  Yapilan hamle sonucu Sah'in tehdit altinda kalip kalmayacagi durumu kontrol eder.
    //  Tehdit altinda kaliyorsa true dondurur.
    public boolean resultInThreat(Cell start, Cell destination, Board board, Player player){

        Cell copyOfStart = new Cell(start.getX(), start.getY(), start.getPiece());
        Cell copyOfDestination = new Cell(destination.getX(), destination.getY(), destination.getPiece());


        if (start.getPiece() != null && start.getPiece().canMove(start, destination, board)){

            destination.setPiece(start.getPiece());
            start.setPiece(null);
        }
        Cell whiteKingsPosition = whiteKingsPosition(board);
        Cell blackKingPosition = blackKingsPosition(board);
        if (player.isWhiteSide() && whiteKingsPosition.getPiece() instanceof King &&
                ((King) whiteKingsPosition.getPiece()).isWhiteUnderThreat(board)){

            start.setPiece(copyOfStart.getPiece());
            destination.setPiece(copyOfDestination.getPiece());
            return true;
        } else if (!player.isWhiteSide() && blackKingPosition.getPiece() instanceof King &&
                (((King) blackKingPosition.getPiece()).isBlackUnderThreat(board))){

            start.setPiece(copyOfStart.getPiece());
            destination.setPiece(copyOfDestination.getPiece());
            return true;
        }

        start.setPiece(copyOfStart.getPiece());
        destination.setPiece(copyOfDestination.getPiece());
        return false;

    }

    Scanner scanner = new Scanner(System.in);
    public void promote(Cell destination){
        if (movingPiece instanceof Pawn && (movingPiece.isWhite() && destination.getY() == 0) || (!movingPiece.isWhite() && destination.getY() == 7)){
            boolean Loop = true;
            while (Loop){
                System.out.print("\nPiyonu terfi ettirmek istediginiz tas turunu seciniz: ");
                String choice = scanner.next();
                switch (choice){
                    case "K":
                    case "k":
                        destination.setPiece(new Knight(movingPiece.isWhite()));
                        Loop = false;
                        break;

                    case "Q":
                    case "q":
                        destination.setPiece(new Queen(movingPiece.isWhite()));
                        Loop = false;
                        break;

                    case "B":
                    case "b":
                        destination.setPiece(new Bishop(movingPiece.isWhite()));
                        Loop = false;
                        break;

                    case "R":
                    case "r":
                        destination.setPiece(new Rook(movingPiece.isWhite()));
                        Loop = false;
                        break;

                    default:
                        System.out.println("Lutfen gecerli tas giriniz.");
                }
            }
        }
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
                    x = 7;
                    y = 7;
                }
            }
        }
        return blackKingsPosition;
    }
}
