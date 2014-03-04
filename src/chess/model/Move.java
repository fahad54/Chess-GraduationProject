/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class Move {
    public final Coordinate origin;
    public final Coordinate dest;

    public Move(Coordinate origin, Coordinate destination) {
        this.origin = origin;
        dest = destination;
    }
}
