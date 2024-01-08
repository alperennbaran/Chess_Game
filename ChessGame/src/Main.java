import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Game game = new Game();
    static Player player0 = new Player(true);
    static Player player1 = new Player(false);
    static DbHelper dbHelper = new DbHelper();

    public static void main(String[] args) {
        LogInScreen logInScreen = new LogInScreen();
        }
    public static void startGame() {
        game.initialize(player0, player1);

        while (game.getStatus() == GameStatus.ACTIVE) {

            for (int column = 0; column <= 8; column++) {
                System.out.println("+----+----+----+----+----+----+----+----+");

                if (column != 8) {
                    for (int row = 0; row < 8; row++) {
                        String color;
                        if (game.getBoard().getCell(row, column).getPiece() != null && game.getBoard().getCell(row, column).getPiece().isWhite()) {
                            color = "W";
                        } else {
                            color = "B";
                        }
                        if (game.getBoard().getCell(row, column).getPiece() != null) {
                            System.out.print("| " + color + game.getBoard().getCell(row, column).getPiece().getType() + " ");
                        } else {
                            System.out.print("|    ");
                        }
                        if (row == 7) {
                            System.out.print("|");
                        }
                    }
                }
                System.out.println();
            }

            System.out.println();
            if (game.getCurrentTurn().isWhiteSide()) {
                System.out.println("Sira beyazda");
            } else {
                System.out.println("Sira siyahta");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("\nHamle yapacaginiz tasin x kordinatini giriniz: ");
            int startX = scanner.nextInt() - 1;
            System.out.print("\nHamle yapacaginiz tasin y kordinatini giriniz: ");
            int startY = scanner.nextInt() - 1;

            System.out.print("\nTasin gidecegi yerin x kordinatini giriniz: ");
            int destinationX = scanner.nextInt() - 1;
            System.out.print("\nTasin gidecegi yerin y kordinatini giriniz: ");
            int destinationY = scanner.nextInt() - 1;

            Move move = new Move(game.getBoard().getCell(startX, startY), game.getBoard().getCell(destinationX, destinationY), player0);
            game.makeMove(move, game.getCurrentTurn());
        }
        try {
            DbCommands.insertData(player0.getName(), player1.getName(), game.getWinner().getName(), game.getMoveNumber());
            FinalPanel finalPanel = new FinalPanel();
            finalPanel.run();
        } catch (SQLException e) {
            dbHelper.showErrorMessage(e);
        }
    }
}


