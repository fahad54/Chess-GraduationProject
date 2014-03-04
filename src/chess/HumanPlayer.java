/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

import chess.model.*;
/**
 *
 * @author Fahad
 */
public class HumanPlayer implements Player {
    private final GameController controller;
    public HumanPlayer(GameController c) {
        controller = c;
    }
    public Move makeMove(Board board) {
        return controller.getUserMove();
    }
    public void prepareToMove() {
        controller.prepareToMove();
    }
}
