/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class DoubleMove {
    public PGNMove whiteMove;
    public PGNMove blackMove;
    public DoubleMove(PGNMove white, PGNMove black) {
        whiteMove = white;
        blackMove = black;
    }
    public DoubleMove() {

    }
    public void setWhite(PGNMove move) {
        whiteMove = move;
    }
    public void setBlack(PGNMove move) {
        blackMove = move;
    }
}
