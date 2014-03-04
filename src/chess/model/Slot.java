/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class Slot {

    private final SlotType type;
    private final Coordinate coord;

    private Piece piece;

    Slot(SlotType slotType, Coordinate c) {
        type = slotType;
        coord = c;
        piece = new Piece(PieceType.EMPTY, TeamEnum.EMPTY);
    }

    public void setPiece(Piece p) {
        this.piece = p;
    }
    public Piece getPiece() {
        return this.piece;
    }
    public void clear() {
        piece = new Piece();
    }
    public boolean isEmpty() {
        if (piece.getType() == PieceType.EMPTY) {
            return true;
        }
        return false;
    }
    public boolean hasWhitePiece() {
        if (piece.getTeam() == TeamEnum.WHITE) {
            return true;
        } else {
            return false;
        }
    }
    public boolean hasBlackPiece() {
        if (piece.getTeam() == TeamEnum.BLACK) {
            return true;
        } else {
            return false;
        }
    }
    public Coordinate getCoordinate() {
        // Can't return the true coordinate cuz things get fritzy. Return coord + 1, so we got
        // no zeroes, and also, switch the x and y values, cuz arrays are upside down =/

        return new Coordinate(coord.y + 1, coord.x + 1);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.type == SlotType.LIGHT) sb.append("L");
        if (this.type == SlotType.DARK)  sb.append("D");

        if (this.piece.getType() == PieceType.EMPTY) sb.append("x");
        if (this.piece.getType() == PieceType.PAWN) sb.append("P");
        if (this.piece.getType() == PieceType.KNIGHT) sb.append("K");
        if (this.piece.getType() == PieceType.ROOK) sb.append("R");
        if (this.piece.getType() == PieceType.BISHOP) sb.append("B");
        if (this.piece.getType() == PieceType.QUEEN) sb.append("Q");
        if (this.piece.getType() == PieceType.KING) sb.append("K");

        return sb.toString();

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Slot clone = new Slot(type, coord);
        clone.setPiece(piece);
        return clone;
    }

}
