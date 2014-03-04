/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class PGNMove {

    public final Coordinate origin;
    public final Coordinate dest;
    public final PieceType pieceType;
    public final boolean isLegal;
    public final boolean isCaptureMove;
    public final boolean isCheck;
    public final PieceType promotionType;
    public final boolean isCastleQueenSide;
    public final boolean isCastleKingSide;
    public final boolean isMate;
    public final boolean shouldIncludeRank;
    public final boolean shouldIncludeFile;

    private PGNMove(Builder b) {
        origin = b.origin;
        dest = b.destination;
        pieceType = b.pieceType;
        isCaptureMove = b.isCaptureMove;
        promotionType = b.promotionType;
        isCheck = b.isCheck;
        isLegal = b.isLegal;
        isCastleQueenSide = b.isCastleQueenSide;
        isCastleKingSide = b.isCastleKingSide;
        isMate = b.isMate;
        shouldIncludeFile = b.shouldIncludeFile;
        shouldIncludeRank = b.shouldIncludeRank;
    }

    public String toAlgebraicString() {
        if (isCastleKingSide) {
            return "O-O";
        }
        if (isCastleQueenSide) {
            return "O-O-O";
        }
        StringBuilder sb = new StringBuilder();
        if (pieceType == PieceType.PAWN) {

        }
        if (pieceType == PieceType.KNIGHT) {
            sb.append("N");
        }
        if (pieceType == PieceType.ROOK) {
            sb.append("R");
        }
        if (pieceType == PieceType.BISHOP) {
            sb.append("B");
        }
        if (pieceType == PieceType.QUEEN) {
            sb.append("Q");
        }
        if (pieceType == PieceType.KING) {
            sb.append("K");
        }
        if (shouldIncludeRank) {
            sb.append(origin.toStringX());
        }
        if (shouldIncludeFile) {
            sb.append(origin.toStringY());
        }
        if (isCaptureMove) {
            sb.append("x");
        }

        sb.append(dest.toString());

        if (promotionType != PieceType.EMPTY) {
            sb.append("=");
            if (promotionType == PieceType.KNIGHT) {
                sb.append("N");
            }
            if (promotionType == PieceType.ROOK) {
                sb.append("R");
            }
            if (promotionType == PieceType.BISHOP) {
                sb.append("B");
            }
            if (promotionType == PieceType.QUEEN) {
                sb.append("Q");
            }
        }
        if (isMate) {
            sb.append("#");
        } else if (isCheck) {
            sb.append("+");
        }




        return sb.toString();
    }

    public static class Builder {
        // Req'd parameters

        private final Coordinate origin;
        private final Coordinate destination;
        private final PieceType pieceType;

        // Optional Parameters

        private PieceType promotionType = PieceType.EMPTY;
        private boolean isCaptureMove = false;
        private boolean isCheck = false;
        private boolean isCastleQueenSide = false;
        private boolean isCastleKingSide = false;
        private boolean isMate = false;
        private boolean isLegal = false;
        private boolean shouldIncludeRank = false;
        private boolean shouldIncludeFile = false;

        public Builder(Coordinate origin, Coordinate destination, PieceType pieceType) {
            this.origin = origin;
            this.destination = destination;
            this.pieceType = pieceType;
        }

        public Builder promotionType(PieceType pt) {
            promotionType = pt;
            return this;
        }
        public Builder legal() {
            isLegal = true;
            return this;
        }
        public Builder includeRank() {
            shouldIncludeRank = true;
            return this;
        }

        public Builder includeFile() {
            shouldIncludeFile = true;
            return this;
        }

        public Builder captureMove() {
            isCaptureMove = true;
            return this;
        }

        public Builder check() {
            isCheck = true;
            return this;
        }

        public Builder castleQueenSide() {
            isCastleQueenSide = true;
            return this;
        }

        public Builder castleKingSide() {
            isCastleKingSide = true;
            return this;
        }

        public Builder mate() {
            isMate = true;
            return this;
        }

        public PGNMove build() {
            return new PGNMove(this);
        }
    }
}
