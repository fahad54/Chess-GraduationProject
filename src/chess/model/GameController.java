/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

import java.util.Set;

/**
 *
 * @author Fahad
 */
public interface GameController {

    public Board getBoard();
    public void updateBoard();
    public void addMove(PGNMove move);
    public PGNMove refineMoveObject(Move move);
    public void setMoveList(Set<PGNMove> moveList);
    public void setGameOver();
    public void timeHasRunOut(TeamEnum teamWhoForfeits);
    public void updateTimers();
    public boolean isDrawPossibleMovesOption();
    public void updateCapturedPieces(Set<Piece> set);
    public int getTimeRemaining(TeamEnum teamInQuestion);
    public TimeConditions getTimeConditions();
    public boolean gameHasStarted();
    public boolean isDrawBoardControlOption();
    public void prepareToMove();
    public TeamEnum getWhoseTurnItIs();
    public void setIsHistoryWindowVisible(boolean flag);
    public Move getUserMove();
    public void setNotice(Notice n);
    public void setNoticeTitle(String t);
    public void setNoticeBody(String t);
    public void setPreviousMove(PGNMove prevMove);

    public PieceType askForPromotionType(TeamEnum team);

}
