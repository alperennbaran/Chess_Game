import java.sql.*;
import java.util.ArrayList;

public class DbCommands {
    public static void selectData() throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = helper.getConnection();
            statement = connection.createStatement(); // Bağlantıyla ilgili sorgu oluşturmak için statement nesnesi kullanıyoruz.
            resultSet = statement.executeQuery      // Oluşturulan sorgunun sonucunu almak için resultSet nesnesi oluşturuyoruz
                    ("SELECT ID,Player1,Player2,MoveNumber FROM players;");
            ArrayList<DbPlayer> dbPlayers = new ArrayList<>();
            while (resultSet.next()) {
                dbPlayers.add(new DbPlayer(
                        resultSet.getInt("ID"),
                        resultSet.getObject("Player1"),
                        resultSet.getObject("Player2"),
                        resultSet.getObject("Winner"),
                        resultSet.getInt("MoveNumber")));
            }

            System.out.println(dbPlayers.size());
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        } finally {
            connection.close();
        }
    }

    public static void insertData(String player0, String player1, String winner, int moveNumber) throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = helper.getConnection();
            String sql = "INSERT INTO players (Player1, Player2, Winner, MoveNumber) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, player0.toUpperCase().trim());
            statement.setString(2, player1.toUpperCase().trim());
            statement.setString(3, winner.toUpperCase().trim());
            statement.setInt(4, moveNumber);
            statement.executeUpdate(); // Sorguyu çalıştır

            System.out.println("Kayıt Eklendi.");
            System.out.println();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void updateData() throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            connection = helper.getConnection();
            String sql = "UPDATE players set Winner = ?,MoveNumber = ? where Player1 = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, Main.game.getWinner().getName());
            statement.setInt(2, Main.game.getMoveNumber());
            statement.setString(3, Main.player0.getName());
            System.out.println("Kayıt Güncellendi.");
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        } finally {
            statement.close();
            connection.close();
        }
    }

    public static void deleteData(int num) throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {
            connection = helper.getConnection();
            String sql = "DELETE FROM players where ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, num);
            System.out.println("Kayıt Silindi.");
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        } finally {
            statement.close();
            connection.close();
        }
    }
}
