/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public enum PieceType {
    EMPTY, PAWN, KNIGHT, ROOK, BISHOP, QUEEN, KING;

    @Override
    public String toString() {

        if (this == EMPTY) return "";
        if (this == PAWN)  return "Pawn";
        if (this == KNIGHT) return "Knight";
        if (this == ROOK) return "Rook";
        if (this == BISHOP) return "Bishop";
        if (this == QUEEN) return "Queen";
        if (this == KING) return "King";

        return "";
    }


}
