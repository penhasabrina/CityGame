package game.server;

import game.GameBoot;
import jade.gui.GuiEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerGui extends JFrame {
    static final int GUI_ON_CLOSE = 1;

    public static final int GRID_ROWS = 9;
    public static final int GRID_COLS = 9;
    public static final int GRID_CELL_WIDTH = 64;
    public static final int GRID_CELL_HEIGHT = 64;

    private ServerAg myAgent;

    public ServerGui(ServerAg myAgent) throws HeadlessException {
        this.myAgent = myAgent;
    }

    public void initGui() {
        initFrameParams();
        initLogArea();
        initCmdPanel();
        initBoard();

        pack();
        setVisible(true);
        showMessage(getTitle() + " started.");
    }

    private void initBoard() {
        city1Board = newCityBoard("City1 Board");
        city2Board = newCityBoard("City2 Board");

        var panel = new JPanel(new GridLayout(1,2));
        panel.setBorder(new TitledBorder("Board"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(city1Board);
        panel.add(city2Board);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private JPanel newCityBoard(String title) {
        var cityBoard = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, -1, -1));
        cityBoard.setBorder(new TitledBorder(title));
        for(int i=0; i<GRID_ROWS; i++) {
            for(int j=0; j<GRID_ROWS; j++) {
                var lbl = new JLabel();
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setVerticalAlignment(JLabel.CENTER);
                lbl.setPreferredSize(new Dimension(GRID_CELL_WIDTH, GRID_CELL_HEIGHT));
                lbl.setText(String.format("%d,%d", i, j));
                cityBoard.add(lbl);
            }
        }
        return cityBoard;
    }

    private void initCmdPanel() {
        var bar = new JToolBar("Command Bar", JToolBar.HORIZONTAL);
        bar.setBorder(new TitledBorder("Command Bar"));
        bar.setPreferredSize(new Dimension(getWidth(), 60));

        getContentPane().add(bar, BorderLayout.NORTH);
    }

    private void initFrameParams() {
        setTitle("CityGame Server GUI");
        var icon = GameBoot.loadIcon("img/ico-city.jpeg", "CityGame Icon");
        setIconImage((icon==null) ? null : icon.getImage());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(getContentPane(), "Exit Server?", "Warning", JOptionPane.YES_NO_OPTION);
                if(res==JOptionPane.YES_OPTION) {
                    myAgent.postGuiEvent(new GuiEvent(e.getSource(), GUI_ON_CLOSE));
                    showMessage(getTitle() + " finished.");
                    dispose();
                }
            }
        });
    }

    private void initLogArea() {
        logArea = new JTextArea(6, 120);
        logArea.setLineWrap(false);
        logArea.setEditable(false);
        logArea.setBorder(new TitledBorder("Log Area"));

        var scroll = new JScrollPane(logArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        getContentPane().add(scroll, BorderLayout.SOUTH);
    }

    public void showMessage(String message) {
        logArea.append(message);
        logArea.append("\n");
        System.out.println(message);
    }

    JTextArea logArea;
    JPanel city1Board;
    JPanel city2Board;

}
