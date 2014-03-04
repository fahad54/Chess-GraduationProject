/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public enum TeamEnum {

    WHITE, BLACK, EMPTY;

    @Override
    public String toString() {
        if (this == WHITE) {
            return "White";

        }
        if (this == BLACK) {
            return "Black";
        }
        if (this == EMPTY) {
            return "Empty";
        }
        return "";
    }
}
