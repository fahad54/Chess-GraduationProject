/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public interface Player {
    public Move makeMove(Board board);
    public void prepareToMove();
}
