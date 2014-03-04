/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.WindowConstants;
import chess.model.DoubleMove;
import chess.model.GameController;
import chess.model.PGNMove;
import chess.model.Notice;
import chess.model.Piece;
import chess.model.TeamEnum;
import chess.model.TimeConditions;
/**
 *
 * @author Fahad
 */
public class StatsWindow extends javax.swing.JFrame implements WindowListener {

    private static final int TIME_INTERVAL = 100;
    private final DefaultListModel model;
    private Set<PGNMove> moveList;
    private final GameController controller;
    private int whiteTime;
    private int blackTime;
    private Timer whiteTimer, blackTimer;
    private TimeConditions timeConditions;


    /** Creates new form HistoryWindow */
    public StatsWindow(GameController c) {
        model = new DefaultListModel();
        controller = c;
        initComponents();
        initialize();

    }

    private void initialize() {
        setResizable(false);
        setAlwaysOnTop(true);
        whiteTime = 0;
        blackTime = 0;
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        addWindowListener(this);
        moveList = new LinkedHashSet<PGNMove>();
        historyList.setModel(model);


    }

    public void startGame(TimeConditions tc) {
        timeConditions = tc;
        whiteTime = timeConditions.whiteTime;
        blackTime = timeConditions.blackTime;
        blackTimeLabel.setText(Integer.toString(whiteTime));
        whiteTimeLabel.setText(Integer.toString(blackTime));
        // Convert minutes to Milliseconds
        if (timeConditions.isMinutes) {
            whiteTime = whiteTime * 60000;
            blackTime = blackTime * 60000;
        }
        // Convert Seconds to Milliseconds
        else {
            whiteTime = whiteTime * 1000;
            blackTime = blackTime * 1000;
        }



        TimerTask whiteTask = new TimerTask() {

            @Override
            public void run() {
                updateTimeWhite();
            }
        };
        TimerTask blackTask = new TimerTask() {

            @Override
            public void run() {
                updateTimeBlack();
            }
        };
        whiteTimer = new Timer("whiteTimer");
        whiteTimer.scheduleAtFixedRate(whiteTask, 0, TIME_INTERVAL);
        blackTimer = new Timer("blackTimer");
        blackTimer.scheduleAtFixedRate(blackTask, 0, TIME_INTERVAL);
    }

    public void turnMade() {
        TeamEnum team = controller.getWhoseTurnItIs();
        manageCheckBoxes(team);
        manageBonuses(team);

    }

    private void manageCheckBoxes(TeamEnum teamWhoseTurnItIs) {
        if (teamWhoseTurnItIs == TeamEnum.WHITE) {
            whiteCheckBox.setSelected(true);
            blackCheckBox.setSelected(false);
        }
        if (teamWhoseTurnItIs == TeamEnum.BLACK) {
            blackCheckBox.setSelected(true);
            whiteCheckBox.setSelected(false);
        }
    }

    private void manageBonuses(TeamEnum teamWhoseTurnItIs) {
        // If it's white's turn, black just moved, so give him the bonus
        if (teamWhoseTurnItIs == TeamEnum.WHITE) {
            blackTime = blackTime + (timeConditions.bonus * 1000);
        }
        // If it's black's turn, white just moved, so give him the bonus
        if (teamWhoseTurnItIs == TeamEnum.BLACK) {
            whiteTime = whiteTime + (timeConditions.bonus * 1000);
        }
        whiteTimeLabel.setText(convertMillisToMinutes(whiteTime));
        blackTimeLabel.setText(convertMillisToMinutes(blackTime));


    }
    public void cancelTimers() {
        whiteTimer.cancel();
        blackTimer.cancel();
    }
    private void updateTimeWhite() {
        if (controller.gameHasStarted()) {
            if (controller.getWhoseTurnItIs() == TeamEnum.WHITE) {
                whiteTime = whiteTime - TIME_INTERVAL;
                whiteTimeLabel.setText(convertMillisToMinutes(whiteTime));
                if (whiteTime <= 0) {
                    controller.timeHasRunOut(TeamEnum.WHITE);
                    whiteTimer.cancel();
                    blackTimer.cancel();
                }
            }
        }

    }

    private void updateTimeBlack() {
        if (controller.getWhoseTurnItIs() == TeamEnum.BLACK) {
            blackTime = blackTime - TIME_INTERVAL;
            blackTimeLabel.setText(convertMillisToMinutes(blackTime));
            if (blackTime <= 0) {
                controller.timeHasRunOut(TeamEnum.BLACK);
                blackTimer.cancel();
                whiteTimer.cancel();
            }
        }
    }

    private String convertMillisToMinutes(int millis) {
        if (millis > 60000) {
            long mins = TimeUnit.MILLISECONDS.toMinutes(millis);
            long secs = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
            if (secs < 10) {
                return String.format("%d:0%d", mins, secs);
            } else {
                return String.format("%d:%d",
                    mins, secs);
            }

        } else {
            double val = (double) millis / 1000;
            return Double.toString(val);

        }



    }

    public void setMoveList(Set<PGNMove> moveList) {
        this.moveList = moveList;
        updateListModel();
    }

    public void setNoticeTitle(String t) {
        noticeTitleLabel.setText(t);
    }

    public void setNoticeBody(String t) {
        noticeDetailTextPane.setText(t);
    }

    public void clearMoveList() {
        model.clear();
        moveList.clear();
    }

    public void addMove(PGNMove move) {
        moveList.add(move);
        updateListModel();
    }

    private Set<DoubleMove> buildSpecialListFromList() {
        Set<DoubleMove> resultSet = new LinkedHashSet<DoubleMove>();
        boolean odd = true;
        DoubleMove workhorse = new DoubleMove();
        for (PGNMove move : moveList) {
            if (odd) {
                workhorse.whiteMove = move;
                resultSet.add(workhorse);
            } else {
                workhorse.blackMove = move;
                resultSet.add(workhorse);
                workhorse = new DoubleMove();
            }

            odd = !odd;
        }


        return resultSet;

    }

    public void setNotice(Notice n) {
        noticeTitleLabel.setText(n.getTitle());
        noticeDetailTextPane.setText(n.getBody());
        repaint();
    }

    /**
     * Takes this class' moveList object and turns it into something we can
     * present in the JList. This method, along with Move.toAlgebraicString(), does the heavy lifting.
     */
    private void updateListModel() {

        //specialList.clear();
        Set<DoubleMove> specialList = buildSpecialListFromList();
        int i = 1;
        model.clear();
        for (DoubleMove dm : specialList) {
            if (dm.blackMove == null) {
                model.addElement(i + ". " + dm.whiteMove.toAlgebraicString());
            } else if (dm.whiteMove == null) {
                model.addElement(" " + dm.blackMove.toAlgebraicString());
            } else {
                model.addElement(i + ". " + dm.whiteMove.toAlgebraicString() + " " + dm.blackMove.toAlgebraicString());
            }
            i++;
        }

        historyList.repaint();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        historyList = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        whiteTimeLabel = new javax.swing.JLabel();
        blackTimeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        noticeDetailTextPane = new javax.swing.JTextPane();
        noticeTitleLabel = new javax.swing.JLabel();
        whiteCheckBox = new javax.swing.JCheckBox();
        blackCheckBox = new javax.swing.JCheckBox();
        prisonView = new chess.PrisonView();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(chess.ChessApp.class).getContext().getResourceMap(StatsWindow.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        historyList.setName("historyList"); // NOI18N
        jScrollPane1.setViewportView(historyList);

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        whiteTimeLabel.setFont(resourceMap.getFont("whiteTimeLabel.font")); // NOI18N
        whiteTimeLabel.setText(resourceMap.getString("whiteTimeLabel.text")); // NOI18N
        whiteTimeLabel.setName("whiteTimeLabel"); // NOI18N

        blackTimeLabel.setFont(resourceMap.getFont("blackTimeLabel.font")); // NOI18N
        blackTimeLabel.setText(resourceMap.getString("blackTimeLabel.text")); // NOI18N
        blackTimeLabel.setName("blackTimeLabel"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane2.setEnabled(false);
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        noticeDetailTextPane.setEditable(false);
        noticeDetailTextPane.setName("noticeDetailTextPane"); // NOI18N
        jScrollPane2.setViewportView(noticeDetailTextPane);

        noticeTitleLabel.setFont(resourceMap.getFont("noticeTitleLabel.font")); // NOI18N
        noticeTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noticeTitleLabel.setText(resourceMap.getString("noticeTitleLabel.text")); // NOI18N
        noticeTitleLabel.setName("noticeTitleLabel"); // NOI18N

        whiteCheckBox.setText(resourceMap.getString("whiteCheckBox.text")); // NOI18N
        whiteCheckBox.setEnabled(false);
        whiteCheckBox.setName("whiteCheckBox"); // NOI18N

        blackCheckBox.setEnabled(false);
        blackCheckBox.setName("blackCheckBox"); // NOI18N

        prisonView.setName("prisonView"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(whiteCheckBox)
                                    .addComponent(blackCheckBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(blackTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(whiteTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(noticeTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(prisonView, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noticeTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(whiteTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(whiteCheckBox))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blackCheckBox)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(blackTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prisonView, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>
    // Variables declaration - do not modify
    private javax.swing.JCheckBox blackCheckBox;
    private javax.swing.JLabel blackTimeLabel;
    private javax.swing.JList historyList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane noticeDetailTextPane;
    private javax.swing.JLabel noticeTitleLabel;
    private chess.PrisonView prisonView;
    private javax.swing.JCheckBox whiteCheckBox;
    private javax.swing.JLabel whiteTimeLabel;
    // End of variables declaration

    public void windowOpened(WindowEvent we) {
    }

    public int getTime(TeamEnum teamInQuestion) {
        if (teamInQuestion == TeamEnum.WHITE) {
            return whiteTime;
        }
        if (teamInQuestion == TeamEnum.BLACK) {
            return blackTime;
        }
        return -1;
    }

    public void windowClosing(WindowEvent we) {
        controller.setIsHistoryWindowVisible(false);
    }

    public void windowClosed(WindowEvent we) {
    }

    public void windowIconified(WindowEvent we) {
    }

    public void windowDeiconified(WindowEvent we) {
    }

    public void windowActivated(WindowEvent we) {
    }

    public void windowDeactivated(WindowEvent we) {
    }

    public void setCapturedPieces(Set<Piece> set) {
        prisonView.setCapturedPieces(set);
    }
}
