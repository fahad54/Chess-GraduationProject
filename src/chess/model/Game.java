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
public class Game {

    private final GameController controller;
    private final Player white;
    private final Player black;
    private final Board board;
    private boolean isWhiteTurn = true;

    public Set<Move> getMoveList() {
        return board.getMoveList();
    }

    public Game(Player white, Player black, GameController c) {
        this.white = white;
        this.black = black;
        controller = c;
        board = new Board(this);
        board.populate();
        controller.setNotice(new BasicNotice("White to Move", ""));

    }

    public TeamEnum getTurn() {
        if (isWhiteTurn) {
            return TeamEnum.WHITE;
        } else {
            return TeamEnum.BLACK;
        }
    }

    public void setTeamForfeitsOnTime(TeamEnum team) {
        Notice n = null;
        if (team == TeamEnum.WHITE) {
            board.setGameState(GameState.WHITE_FORFEITS_TIME);
            n = new BasicNotice("Black wins", "White forfeits on time.");
        }

        if (team == TeamEnum.BLACK) {
            board.setGameState(GameState.BLACK_FORFEITS_TIME);
            n = new BasicNotice("White wins", "Black forfeits on time.");
        }
        controller.setNotice(n);
    }

    public void setTeamResigns(TeamEnum team) {
        if (team == TeamEnum.WHITE) {
            board.setGameState(GameState.WHITE_RESIGNS);
        }
        if (team == TeamEnum.BLACK) {
            board.setGameState(GameState.BLACK_RESIGNS);
        }
    }

    public void start() {
        Notice n = null;
        while (true) {
            GameState state = board.getGameState();
            if (state == GameState.NOT_STARTED) {
                executeTurn();
            }
            if (state == GameState.UNFINISHED) {
                executeTurn();
            }
            if (state == GameState.STALEMATE) {
                n = new BasicNotice("Game Drawn", "Stalemate");
                controller.setGameOver();
                break;
            }
            if (state == GameState.WHITE_IS_MATED) {
                n = new BasicNotice("Black wins", "White is Checkmated.");
                controller.setGameOver();

                break;
            }
            if (state == GameState.BLACK_IS_MATED) {
                n = new BasicNotice("White wins.", "Black is Checkmated.");
                controller.setGameOver();

                break;
            }
            if (state == GameState.WHITE_FORFEITS_TIME) {
                n = new BasicNotice("Black wins", "White forfeits on time.");
                controller.setGameOver();

            }

            if (state == GameState.BLACK_FORFEITS_TIME) {
                n = new BasicNotice("White wins", "Black forfeits on time.");
                controller.setGameOver();

            }

        }
        controller.setNotice(n);

    }

    public void executeTurn() {
        Move move;
        while (true) {
            if (isWhiteTurn) {
                white.prepareToMove();
                move = white.makeMove(board);
            } else {
                black.prepareToMove();
                move = black.makeMove(board);
            }
            boolean legitMove = board.isTrulyLegalMove(move, true);

            if (legitMove) {
                PGNMove refinedMove = board.refineMoveObject(move);
                board.makeMove(refinedMove);
                isWhiteTurn = !isWhiteTurn;
                controller.updateBoard();
                controller.setNoticeTitle(board.getLastNoticeTitle());
                controller.setNoticeBody(board.getLastNoticeBody());
                controller.setPreviousMove(refinedMove);
                controller.addMove(refinedMove);
                controller.updateTimers();
                controller.updateCapturedPieces(board.getCapturedPieces());
                break;
            } else {
                controller.setNoticeTitle(board.getLastNoticeTitle());
                controller.setNoticeBody(board.getLastNoticeBody());

            }
        }

    }

    public PGNMove refineMoveObject(Move move) {
        return board.refineMoveObject(move);
    }
    public PieceType askForPromotionType(TeamEnum team) {
        return controller.askForPromotionType(team);
    }
    public Board getBoard() {
        return board;
    }
}
