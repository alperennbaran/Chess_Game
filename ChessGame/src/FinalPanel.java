import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class FinalPanel extends javax.swing.JFrame {
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTable tblPlayers;
    private java.awt.TextField txtSearch;

    DefaultTableModel model;
    DbHelper dbHelper = new DbHelper();

    public FinalPanel() {
        initComponents();
        model = (DefaultTableModel)tblPlayers.getModel();
        try{
            ArrayList<DbPlayer> dbPlayers = getPlayers();
            for(DbPlayer dbPlayer : dbPlayers){
                Object [] row = {dbPlayer.getId(), dbPlayer.getPlayer1(), dbPlayer.getPlayer2(),dbPlayer.getWinner(), dbPlayer.getMoveNumber()};
                model.addRow(row);
            }
        }
        catch(SQLException exception){
            dbHelper.showErrorMessage(exception);
        }
    }

    public ArrayList<DbPlayer> getPlayers() throws SQLException{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<DbPlayer> dbPlayers = null;

        try{
            connection = dbHelper.getConnection();
            statement = connection.createStatement();
            updateIDSequence(connection);
            resultSet = statement.executeQuery("select * from players");
            dbPlayers = new ArrayList<DbPlayer>();
            while(resultSet.next()){
                dbPlayers.add(new DbPlayer(
                        resultSet.getInt("ID"),
                        resultSet.getString("Player1"),
                        resultSet.getString("Player2"),
                        resultSet.getString("Winner"),
                        resultSet.getInt("MoveNumber")
                ));
            }
        }
        catch(SQLException exception){
            dbHelper.showErrorMessage(exception);
            return null;  // Eğer kod Database'e bağlanamazsa catch ve finally bloklarından çıkamaz ve players döndüremez. Bu yüzden burada return null yazarak kodun doğru çalışmasını sağlıyoruz.

        }
        finally{
            statement.close();
            connection.close();
        }
        return dbPlayers;
    }
    private static void updateIDSequence(Connection connection) throws SQLException {
        // SQL sorgusunu çalıştırma
        /*String sql = "SET @count = 0; UPDATE players SET ID = @count := @count + 1;";
        connection.createStatement().executeUpdate(sql);  Bu ifade çalışmaz çünkü MySQL, birden çok SQL ifadesini aynı sorguda
                                                     çalıştırmanıza izin vermez. Sorguları ayrı ayrı çalıştırmak gerekmektedir.
                                                     Doğrusu aşağıdaki gibidir.*/
        // İlk sorguyu çalıştırma
        String sql1 = "SET @count = 0;";
        connection.createStatement().executeUpdate(sql1);

        // İkinci sorguyu çalıştırma
        String sql2 = "UPDATE players SET ID = @count := @count + 1;";
        connection.createStatement().executeUpdate(sql2);
    }


    private void initComponents() { // initComponents() metodu ile bileşenlerin özelliklerini belirliyoruz.

        tblPlayers = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSearch = new java.awt.TextField();
        lblSearch = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblPlayers.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Player1", "Player2" , "Winner" , "MoveNumber"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false,false,false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPlayers); // tblPlayers isimli tabloyu scrollPane içine yerleştiriyoruz.
        if (tblPlayers.getColumnModel().getColumnCount() > 0) {
            tblPlayers.getColumnModel().getColumn(0).setResizable(false);
            tblPlayers.getColumnModel().getColumn(1).setResizable(false);
            tblPlayers.getColumnModel().getColumn(2).setResizable(false);
            tblPlayers.getColumnModel().getColumn(3).setResizable(false);
            tblPlayers.getColumnModel().getColumn(4).setResizable(false);
            tblPlayers.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblPlayers.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblPlayers.getColumnModel().getColumn(2).setPreferredWidth(75);
            tblPlayers.getColumnModel().getColumn(3).setPreferredWidth(75);
            tblPlayers.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblPlayers.setSize(500,500);
        }

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }

        });
            lblSearch.setText("Name : ");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());// getContentPane() metodu ile içerisindeki tüm bileşenleri alıyoruz. Bu bileşenlerin layoutunu değiştirmek için layout değişkenine atıyoruz.
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(          // layout değişkeninin horizontal eksendeki layoutunu değiştirmek için kullanıyoruz.
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(113, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSearch))
                                .addGap(34, 34, 34)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();

    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {// txtSearchKeyReleased() metodu ile arama kutusuna yazılan değer değiştiğinde çalışacak kodları yazıyoruz.
        String searchKey = txtSearch.getText();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        tblPlayers.setRowSorter(tableRowSorter);

    /*RowFilter<DefaultTableModel, Object> rowFilter = new RowFilter<DefaultTableModel, Object>() {
        @Override
        public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
            String name = entry.getStringValue(1); // name sütununun indeksini temsil etmelidir
            return name.toLowerCase().contains(searchKey.toLowerCase());
        }
    };                     Ya bu yapılacak arama filtrelemesinde caseSensitive kaldırmak için ya da aşağıdaki
    */

        tableRowSorter.setRowFilter(RowFilter.regexFilter(searchKey.toUpperCase().trim()));


    }

    public static void main(String[] args) {
        run();
    }
    public static void run() {
        new FinalPanel().setVisible(true);
    }
}
