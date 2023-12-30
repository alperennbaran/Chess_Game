import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public final class Gui{
    public final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final Board board;
    private final Game game;
    Player player0;
    Player player1;

    private Cell startCell;
    private Cell destinationCell;
    private Piece selectedPiece;

    private final static Dimension OUTER_FRAME_DIMENTION = new Dimension(600,600);
    private final static Dimension BOARD_PANEL_DIMENTION= new Dimension(400,350);
    private final static Dimension TITLE_PANEL_DIMENTION = new Dimension(10,10);
    public Gui(){
        this.gameFrame= new JFrame("Jchess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar );


        this.board = new Board();
        this.game = new Game();
        this.player0 = new Player(true);
        this.player1 = new Player(false);
        game.initialize(player0, player1);

        this.gameFrame.setSize(OUTER_FRAME_DIMENTION);
        this.boardPanel= new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }
    private Component createFileMenu() {

        //menu oluşturduk
        final JMenu fileMenu = new JMenu("File");

        //yaplıan hamleleri pgn formatında yazdırmak için menu tuşu
        final JMenuItem openPGN = new JMenuItem("Load PGN file");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that pgn file!!");
            }
        });
        fileMenu.add(openPGN);

        // çıkış için exit menu tuşu
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    // satranç tahtasıyla eşleşen bir panel
    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;
        BoardPanel(){

            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i< 64; i++){
                final TilePanel tilePanel= new TilePanel(this,i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }

            setPreferredSize(BOARD_PANEL_DIMENTION);
            validate();
        }

        public void drawBoard(Board board){
            removeAll();
            for (TilePanel tilePanel: boardTiles){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    // bizdeki cell ile eşleşen panel
    private class TilePanel extends JPanel{
        private final int titleId;
        TilePanel(final BoardPanel boardPanel,final int titleId){

            super(new GridBagLayout());
            this.titleId = titleId;

            setPreferredSize(TITLE_PANEL_DIMENTION);
            assignTilePieceIcon(board);
            assingTileColor();

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    //sağ tık ise her şeyi sıfırla
                    if (isRightMouseButton(e)){

                        startCell = null;
                        destinationCell = null;
                        selectedPiece = null;

                    //sol tık ise hamle yap
                    }else if (isLeftMouseButton(e)){

                        //ilk tıklamada start cell oluştur
                        if (startCell == null){

                            startCell = board.getCell(titleId);
                            selectedPiece = startCell.getPiece();
                            System.out.println("start x "+startCell.getX()+" y "+startCell.getY()+" seçilen taş: "+startCell.getPiece().getType()); //çaşılıyor mu diye bakmak için

                            if (selectedPiece ==null){
                                startCell =null;
                            }

                        }else {
                            //ikinci tıklamada destination cell oluştur
                            destinationCell = board.getCell(titleId);
                            System.out.println("dest x "+destinationCell.getX()+" y "+destinationCell.getY());


                            if (startCell.getPiece().isWhite()==game.getCurrentTurn().whiteSide){

                                if (selectedPiece.canMove(startCell,destinationCell,board)){

                                    Move move = new Move(startCell,destinationCell,player0);
                                    game.makeMove(move, game.getCurrentTurn());


                                    startCell = null;
                                    destinationCell = null;
                                    selectedPiece = null;

                                }else {
                                    System.out.println("canMove false döndü");
                                }


                            }else {
                                System.out.println("seçilen taş senin değil");
                            }
                        }

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(board);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            validate();
        }

        public void drawTile(Board board){
            assingTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }

        //tahtanın renkleri için
        private void assingTileColor() {
            Color gri = new Color(149, 141, 148, 255);

            int row = titleId / 8;
            int col = titleId % 8;
            // satır ve sütuna göre renkler
            if (row % 2 == 0) {
                if (col % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(gri);
                }
            } else {
                if (col % 2 == 0) {
                    setBackground(gri);
                } else {
                    setBackground(Color.WHITE);
                }
            }
        }

        private void assignTilePieceIcon(Board board){
            this.removeAll();
            //Hücrede taş varsa iconunu bulunduğu kareye koy
            if (!(board.getCell(titleId).getPiece()==null)){
               try {
                   BufferedImage image = ImageIO.read(new File(board.getCell(titleId).getPiece().getPath()));
                   add(new JLabel(new ImageIcon(image)));
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        }
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
    }
}