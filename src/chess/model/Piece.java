/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class Piece {

    private final PieceType type;
    private final TeamEnum team;

    public Piece() {
        type = PieceType.EMPTY;
        team = TeamEnum.EMPTY;
        }
    public boolean isPawn() {
        if (type == PieceType.PAWN) return true;
        else return false;
    }
    public boolean isRook() {
        if (type == PieceType.ROOK) return true;
        else return false;
    }
    public boolean isKnight() {
        if (type == PieceType.KNIGHT) return true;
        else return false;
    }
    public boolean isBishop() {
        if (type == PieceType.BISHOP) return true;
        else return false;
    }
    public boolean isQueen() {
        if (type == PieceType.QUEEN) return true;
        else return false;
    }
    public boolean isKing() {
        if (type == PieceType.KING) return true;
        else return false;
    }
    public boolean isWhite() {
        if (team == TeamEnum.WHITE) return true;
        else return false;
    }
    public boolean isBlack() {
        if (team == TeamEnum.BLACK) return true;
        else return false;
    }
    public Piece(PieceType type, TeamEnum team) {
        this.type = type;
        this.team = team;
    }
    public PieceType getType() {
        return this.type;
    }
    public boolean isEmpty() {
        if (type == PieceType.EMPTY) {
            return true;
        }
        return false;
    }
    public TeamEnum getTeam() {
        return this.team;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (team == TeamEnum.WHITE) {
            sb.append("White ");
        }
        if (team == TeamEnum.BLACK) {
            sb.append("Black ");
        }
        if (team == TeamEnum.EMPTY) {
            sb.append("Empty Space");
        }
        sb.append(type.toString());
        return sb.toString();
    }


}
