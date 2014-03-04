package chess.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Fahad
 */
public class Board {

    private final Slot[][] boardArray;
    private final Set<Piece> capturedWhite;
    private final Set<Piece> capturedBlack;
    private final Set<Piece> whitePieces;
    private final Set<Piece> blackPieces;
    private final Set<Move> moveList;
    private boolean isWhitesTurn = false;
    private boolean blackCanCastleKingside;
    private boolean blackCanCastleQueenside;
    private boolean whiteCanCastleKingside;
    private boolean whiteCanCastleQueenside;
    private Move lastMove;
    private Coordinate checkCulpritCoord;
    private GameState gameState;
    private String lastNoticeTitle;
    private boolean noMovesMade = true;
    private String lastNoticeBody;
    private Game game;

    /**
     * This method creates a blank board object. Call populate() to set up a
     * regular chess game on this board.
     */
    public Board(Game g) {
        if (g != null) {
            game = g;
        }
        gameState = GameState.NOT_STARTED;

        // Creates a blank board.

        boardArray = new Slot[8][8];
        boolean lightDarkToggle = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (lightDarkToggle) {
                    boardArray[i][j] = new Slot(SlotType.DARK, new Coordinate(i, j));
                } else {
                    boardArray[i][j] = new Slot(SlotType.LIGHT, new Coordinate(i, j));
                }
                lightDarkToggle = !lightDarkToggle;
            }
            lightDarkToggle = !lightDarkToggle;
        }

        whitePieces = new HashSet<Piece>();
        blackPieces = new HashSet<Piece>();
        capturedWhite = new HashSet<Piece>();
        capturedBlack = new HashSet<Piece>();
        moveList = new LinkedHashSet<Move>();
        lastNoticeTitle = "White to Move.";
        lastNoticeBody = "";
        checkCulpritCoord = null;
    }

    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    /**
     * This method populates the board object with a randomly generated Chess960
     * setup.
     */
    public void populate960() {
    }

    /**
     * This method populates the board object with the standard setup for a
     * chess game.
     */
    public void populate() {
        getSlotObject(1, 1).setPiece(new Piece(PieceType.ROOK, TeamEnum.BLACK));
        getSlotObject(2, 1).setPiece(new Piece(PieceType.KNIGHT, TeamEnum.BLACK));
        getSlotObject(3, 1).setPiece(new Piece(PieceType.BISHOP, TeamEnum.BLACK));
        getSlotObject(4, 1).setPiece(new Piece(PieceType.QUEEN, TeamEnum.BLACK));
        getSlotObject(5, 1).setPiece(new Piece(PieceType.KING, TeamEnum.BLACK));
        getSlotObject(6, 1).setPiece(new Piece(PieceType.BISHOP, TeamEnum.BLACK));
        getSlotObject(7, 1).setPiece(new Piece(PieceType.KNIGHT, TeamEnum.BLACK));
        getSlotObject(8, 1).setPiece(new Piece(PieceType.ROOK, TeamEnum.BLACK));

        getSlotObject(1, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));
        getSlotObject(2, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));
        getSlotObject(3, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));
        getSlotObject(4, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));
        getSlotObject(5, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));
        getSlotObject(6, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));
        getSlotObject(7, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));
        getSlotObject(8, 2).setPiece(new Piece(PieceType.PAWN, TeamEnum.BLACK));

        getSlotObject(1, 8).setPiece(new Piece(PieceType.ROOK, TeamEnum.WHITE));
        getSlotObject(2, 8).setPiece(new Piece(PieceType.KNIGHT, TeamEnum.WHITE));
        getSlotObject(3, 8).setPiece(new Piece(PieceType.BISHOP, TeamEnum.WHITE));
        getSlotObject(4, 8).setPiece(new Piece(PieceType.QUEEN, TeamEnum.WHITE));
        getSlotObject(5, 8).setPiece(new Piece(PieceType.KING, TeamEnum.WHITE));
        getSlotObject(6, 8).setPiece(new Piece(PieceType.BISHOP, TeamEnum.WHITE));
        getSlotObject(7, 8).setPiece(new Piece(PieceType.KNIGHT, TeamEnum.WHITE));
        getSlotObject(8, 8).setPiece(new Piece(PieceType.ROOK, TeamEnum.WHITE));

        getSlotObject(1, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));
        getSlotObject(2, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));
        getSlotObject(3, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));
        getSlotObject(4, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));
        getSlotObject(5, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));
        getSlotObject(6, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));
        getSlotObject(7, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));
        getSlotObject(8, 7).setPiece(new Piece(PieceType.PAWN, TeamEnum.WHITE));

        for (Slot[] column : boardArray) {
            for (Slot s : column) {
                if (s.hasWhitePiece()) {
                    whitePieces.add(s.getPiece());
                }
                if (s.hasBlackPiece()) {
                    blackPieces.add(s.getPiece());
                }
            }
        }

        blackCanCastleKingside = true;
        whiteCanCastleQueenside = true;
        blackCanCastleQueenside = true;
        whiteCanCastleKingside = true;

    }

    /**
     * Private convenience method that adjusts the coordinate system into
     * something humans can understand, and returns the Slot object at the given
     * coordinate. Note: Coordinate System Origin lies at top left and is (1, 1)
     *
     * @param row The row to access
     * @param col The column to access
     * @return returns the Slot object located at the specified location in the
     * array
     */
    public Slot getSlotObject(int row, int col) {
        col = col - 1;
        row = row - 1;
        return boardArray[col][row];
    }

    public Slot getSlotObject(Coordinate c) {
        return getSlotObject(c.x, c.y);
    }

    /**
     * Returns a copy of this board's array.
     *
     * @return A copy of this board's array, of type Slot[][]
     */
    public Slot[][] getArray() {
        return boardArray.clone();
    }

    public Set<Piece> getCapturedPieces() {
        Set<Piece> resultSet = new HashSet<Piece>();
        resultSet.addAll(capturedWhite);
        resultSet.addAll(capturedBlack);

        return resultSet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                sb.append(boardArray[i][j].toString());
                sb.append(" | ");

            }
            sb.append("\n");
            sb.append("---------------------------------------");
            sb.append("\n");
        }

        return sb.toString();
    }

    public Set<Move> getMoveList() {
        return moveList;
    }
    public TeamEnum otherTeam(TeamEnum team) {
        if (team == TeamEnum.WHITE) {
            return TeamEnum.BLACK;
        }
        if (team == TeamEnum.BLACK) {
            return TeamEnum.WHITE;
        }
        return TeamEnum.EMPTY;
    }
    /**
     * This function takes a Move object with only the required fields (origin
     * and destination) and adds color to it by adding things only the board
     * object can know, such as whether it is a check move, a checkmate, a
     * promotion, etc. This method ignores the legality of the move, and
     * absolutely assumes that the move is legal
     *
     * @param move
     * @return a new, improved Move object.
     */
    public PGNMove refineMoveObject(Move move) {
        Coordinate origin = move.origin;
        Coordinate destination = move.dest;
        Slot originSlot = getSlotObject(origin.x, origin.y);
        Piece pieceToMove = originSlot.getPiece();
        Slot destinationSlot = getSlotObject(move.dest);
        PGNMove.Builder b = new PGNMove.Builder(origin, destination, pieceToMove.getType());
        b.legal();
        TeamEnum attackingTeam = pieceToMove.getTeam();
        TeamEnum defendingTeam = otherTeam(attackingTeam);
        boolean capMove = false;
        // Deal with Castling stuff
        if (isMoveCastleQueenside(move)) {
            b.castleQueenSide();
        }
        if (isMoveCastleKingside(move)) {
            b.castleKingSide();
        }
        if (doesMovePutTeamInCheck(move, defendingTeam, true)) {
            b.check();
        }
        if (isMovePromotionMove(move)) {
            b.promotionType(game.askForPromotionType(attackingTeam));
        }
        if (isMoveCheckMate(move, true)) {
            b.mate();
            if (defendingTeam == TeamEnum.WHITE) {
                gameState = GameState.WHITE_IS_MATED;
            }
            if (defendingTeam == TeamEnum.BLACK) {
                gameState = GameState.BLACK_IS_MATED;
            }
        }

        if (destinationSlot.getPiece().getType() != PieceType.EMPTY) {
            b.captureMove();
            capMove = true;
        }

        // Disambiguate

        PieceType t = pieceToMove.getType();
        if (t == PieceType.PAWN
                && capMove) {
            b.includeRank();
        }
        if (t == PieceType.KNIGHT
                || t == PieceType.ROOK) {
            b.includeRank();
        }
        return b.build();
    }

    /**
     * This move will make the specified move and apply the change to the board.
     * If the move is possible, it will return true. If not, it will not make
     * the move and return false.
     *
     * @param move The move to make.
     *
     */
    public void makeMove(PGNMove move) {


        Coordinate origin = move.origin;
        Slot originSlot = getSlotObject(origin.x, origin.y);
        Piece pieceToMove = originSlot.getPiece();
        Slot destinationSlot = getSlotObject(move.dest);
        TeamEnum teamToMove = pieceToMove.getTeam();

        // Deal with captured stuff...


        // Deal with Castling stuff
        if (move.isCastleKingSide && pieceToMove.isWhite()) {
            Slot rookDest = getSlotObject(6, 8);
            Slot rookStart = getSlotObject(8, 8);
            rookDest.setPiece(rookStart.getPiece());
            rookStart.clear();
        }
        if (move.isCastleKingSide && pieceToMove.isBlack()) {
            Slot rookDest = getSlotObject(6, 1);
            Slot rookStart = getSlotObject(8, 1);
            rookDest.setPiece(rookStart.getPiece());
            rookStart.clear();
        }
        if (move.isCastleQueenSide && pieceToMove.isWhite()) {
            Slot rookDest = getSlotObject(4, 8);
            Slot rookStart = getSlotObject(1, 8);
            rookDest.setPiece(rookStart.getPiece());
            rookStart.clear();
        }
        if (move.isCastleQueenSide && pieceToMove.isBlack()) {
            Slot rookDest = getSlotObject(4, 1);
            Slot rookStart = getSlotObject(1, 1);
            rookDest.setPiece(rookStart.getPiece());
            rookStart.clear();
        }

        if (whiteCanCastle()) {
            if (pieceToMove.getTeam() == TeamEnum.WHITE) {
                if (pieceToMove.isRook()
                        && origin.x == 1 && origin.y == 8) {
                    whiteCanCastleQueenside = false;
                }
                if (pieceToMove.isRook()
                        && origin.x == 8 && origin.y == 8) {
                    whiteCanCastleKingside = false;
                }
                if (pieceToMove.isKing()
                        && origin.x == 5 && origin.y == 8) {
                    whiteCanCastleKingside = false;
                    whiteCanCastleQueenside = false;
                }
            }
        }
        if (blackCanCastle()) {
            if (pieceToMove.isBlack()) {
                if (pieceToMove.isRook()
                        && origin.x == 1 && origin.y == 1) {
                    blackCanCastleQueenside = false;
                }
                if (pieceToMove.isRook()
                        && origin.x == 8 && origin.y == 1) {
                    blackCanCastleKingside = false;
                }
                if (pieceToMove.isKing()
                        && origin.x == 5 && origin.y == 1) {
                    blackCanCastleKingside = false;
                    blackCanCastleQueenside = false;
                }
            }

        }

        originSlot.clear();
        blackPieces.remove(originSlot.getPiece());
        whitePieces.remove(originSlot.getPiece());
        Piece capturedPiece;
        if (move.isCaptureMove) {
            capturedPiece = destinationSlot.getPiece();
            if (capturedPiece.getTeam() == TeamEnum.WHITE) {
                capturedWhite.add(capturedPiece);
            }
            if (capturedPiece.getTeam() == TeamEnum.BLACK) {
                capturedBlack.add(capturedPiece);
            }
        }
        if (move.promotionType != PieceType.EMPTY) {
            Piece newPiece = new Piece(move.promotionType, teamToMove);
            destinationSlot.setPiece(newPiece);
        } else {
            destinationSlot.setPiece(pieceToMove);

        }
        isWhitesTurn = !isWhitesTurn;
        moveList.add(lastMove);
        if (noMovesMade) {
            gameState = GameState.UNFINISHED;
            noMovesMade = false;
        }



    }

    public Move getLastMove() {
        return lastMove;
    }

    public boolean blackCanCastle() {
        if (blackCanCastleQueenside) {
            return true;
        }
        if (blackCanCastleKingside) {
            return true;
        }
        return false;
    }

    public boolean whiteCanCastle() {
        if (whiteCanCastleQueenside) {
            return true;
        }
        if (whiteCanCastleKingside) {
            return true;
        }
        return false;
    }

    public boolean isTeamCheckmated(TeamEnum teamInQuestion) {

        boolean noMoves = true;
        Coordinate kingCoord;
        Set<Move> possibleMoves = new HashSet<Move>();
        Set<Coordinate> set;
        if (isTeamInCheck(teamInQuestion)) {

            kingCoord = getKingCoordinate(teamInQuestion);
            for (Slot aPiece : getSlots(teamInQuestion)) {
                set = getLegalDestinations(aPiece);
                for (Coordinate c : set) {
                    Move moveToAdd = new Move(aPiece.getCoordinate(), c);
                    possibleMoves.add(moveToAdd);

                }
            }
            for (Move m : possibleMoves) {
                if (!doesMovePutTeamInCheck(m, teamInQuestion, false)) {
                    noMoves = false;
                }
            }
            if (noMoves) {
                return true;
            }

        }
        return false;
    }

    public boolean isMoveCheckMate(Move move, boolean updateNotice) {

        // Tentatively make the move

        Coordinate origin = move.origin;
        Coordinate destination = move.dest;

        Piece originBackup = getSlotObject(origin).getPiece();
        Piece destBackup = getSlotObject(destination).getPiece();
        TeamEnum attackingTeam = originBackup.getTeam();
        TeamEnum defendingTeam = otherTeam(attackingTeam);
        getSlotObject(origin).clear();
        getSlotObject(destination).setPiece(originBackup);
        boolean retVal = false;

        if (isTeamCheckmated(defendingTeam)) {
            retVal = true;
            if (updateNotice) {
                if (defendingTeam == TeamEnum.WHITE) {
                    lastNoticeTitle = "Black Wins";
                    lastNoticeBody = "White's King is Checkmated";
                    gameState = GameState.WHITE_IS_MATED;

                }
                if (defendingTeam == TeamEnum.BLACK) {
                    lastNoticeTitle = "White Wins";
                    lastNoticeBody = "Black's King is Checkmated";
                    gameState = GameState.BLACK_IS_MATED;

                }

            }

        }

        // Restore things

        getSlotObject(origin).setPiece(originBackup);
        getSlotObject(destination).setPiece(destBackup);

        return retVal;
    }

    public boolean isMoveStaleMate(Move move, boolean updateNotice) {
        // Tentatively make the move

        Coordinate origin = move.origin;
        Coordinate destination = move.dest;
        Piece originBackup = getSlotObject(origin).getPiece();
        Piece destBackup = getSlotObject(destination).getPiece();
        getSlotObject(origin).clear();
        getSlotObject(destination).setPiece(originBackup);

        boolean retVal = false;

        // White

        if (isTeamStalemated(TeamEnum.WHITE, false)) {
            retVal = true;
            if (updateNotice) {
                lastNoticeTitle = "Game Drawn";
                lastNoticeBody = "White is stalemated and cannot legally move.";
            }
        }

        // Black

        if (isTeamStalemated(TeamEnum.BLACK, false)) {
            retVal = true;
            if (updateNotice) {
                lastNoticeTitle = "Game Drawn";
                lastNoticeBody = "Black is stalemated and cannot legally move.";
            }
        }



        // Restore things
        getSlotObject(origin).setPiece(originBackup);
        getSlotObject(destination).setPiece(destBackup);

        return retVal;
    }

    public boolean isTeamStalemated(TeamEnum teamInQuestion, boolean updateNotice) {
        boolean noMoves = true;
        Coordinate kingCoord;
        Set<Move> possibleMoves = new HashSet<Move>();
        Set<Coordinate> set = new HashSet<Coordinate>();

        if (!isTeamInCheck(teamInQuestion)) {

            kingCoord = getKingCoordinate(teamInQuestion);
            for (Slot aPiece : getSlots(teamInQuestion)) {
                set = getLegalDestinations(aPiece);
                for (Coordinate c : set) {
                    Move moveToAdd = new Move(aPiece.getCoordinate(), c);
                    possibleMoves.add(moveToAdd);

                }
            }
            for (Move m : possibleMoves) {
                if (!doesMovePutTeamInCheck(m, teamInQuestion, false)) {
                    noMoves = false;
                }
            }
            if (noMoves) {
                return true;
            }

        }
        return false;
    }

    public Set<Piece> getTeamPieces(TeamEnum team) {
        if (team == TeamEnum.WHITE) {
            return whitePieces;
        }
        if (team == TeamEnum.BLACK) {
            return blackPieces;
        }
        return null;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * This method will return a Set<Coordinate> based containing the possible
     * moves a given piece in the given position could make. This method
     * completely disregards the legality of these moves however, and so its
     * results must be filtered by the isLegalMove() method, or by simply
     * calling getLegalMoves().
     *
     * @param piece
     * @param startPoint
     * @return The above described Set<Coordinate>
     */
    private Set<Coordinate> getPossibleMoves(Piece piece, Coordinate startPoint) {
        PieceType type = piece.getType();
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        int x = startPoint.x;
        int y = startPoint.y;

        if (type == PieceType.PAWN) {
            if (piece.getTeam() == TeamEnum.WHITE) {
                if (y == 7) {
                    resultSet.add(new Coordinate(x, y - 2));
                }
                if (y != 8) {
                    resultSet.add(new Coordinate(x, y - 1));
                    if (x == 1) {
                        resultSet.add(new Coordinate(x + 1, y - 1));
                    }
                    if (x == 8) {
                        resultSet.add(new Coordinate(x - 1, y - 1));
                    }
                    if (x != 1 && x != 8) {
                        resultSet.add(new Coordinate(x + 1, y - 1));
                        resultSet.add(new Coordinate(x - 1, y - 1));
                    }
                }
            }
            if (piece.getTeam() == TeamEnum.BLACK) {
                if (y == 2) {
                    resultSet.add(new Coordinate(x, y + 2));
                }
                if (y != 8) {
                    resultSet.add(new Coordinate(x, y + 1));
                    if (x == 1) {
                        resultSet.add(new Coordinate(x + 1, y + 1));
                    }
                    if (x == 8) {
                        resultSet.add(new Coordinate(x - 1, y + 1));
                    }
                    if (x != 1 && x != 8) {
                        resultSet.add(new Coordinate(x + 1, y + 1));
                        resultSet.add(new Coordinate(x - 1, y + 1));
                    }
                }
            }
            resultSet = removeIllegalCoordinatesFromSet(resultSet);
            return resultSet;
        }
        if (type == PieceType.ROOK) {
            resultSet.addAll(getAllPointsOnSameRow(startPoint));
            resultSet.addAll(getAllPointsOnSameColumn(startPoint));
            resultSet = removeCoordinateFromSet(startPoint, resultSet);
            return resultSet;
        }
        if (type == PieceType.BISHOP) {
            resultSet.addAll(getAllPointsInPositiveDiagonalLine(startPoint));
            resultSet.addAll(getAllPointsInNegativeDiagonalLine(startPoint));
            resultSet = removeCoordinateFromSet(startPoint, resultSet);

            return resultSet;
        }
        if (type == PieceType.KNIGHT) {
            resultSet.add(new Coordinate(x - 2, y - 1));
            resultSet.add(new Coordinate(x - 1, y - 2));
            resultSet.add(new Coordinate(x - 2, y + 1));
            resultSet.add(new Coordinate(x - 1, y + 2));

            resultSet.add(new Coordinate(x + 2, y - 1));
            resultSet.add(new Coordinate(x + 1, y - 2));
            resultSet.add(new Coordinate(x + 2, y + 1));
            resultSet.add(new Coordinate(x + 1, y + 2));

            resultSet = removeIllegalCoordinatesFromSet(resultSet);
            return resultSet;


        }
        if (type == PieceType.QUEEN) {
            resultSet.addAll(getAllPointsOnSameRow(startPoint));
            resultSet.addAll(getAllPointsOnSameColumn(startPoint));
            resultSet.addAll(getAllPointsInPositiveDiagonalLine(startPoint));
            resultSet.addAll(getAllPointsInNegativeDiagonalLine(startPoint));
            resultSet = removeCoordinateFromSet(startPoint, resultSet);
            return resultSet;
        }
        if (type == PieceType.KING) {

            // Regular Moves

            resultSet.add(new Coordinate(x - 1, y - 1));
            resultSet.add(new Coordinate(x - 1, y + 1));
            resultSet.add(new Coordinate(x - 1, y));
            resultSet.add(new Coordinate(x, y - 1));

            resultSet.add(new Coordinate(x, y + 1));
            resultSet.add(new Coordinate(x + 1, y - 1));
            resultSet.add(new Coordinate(x + 1, y + 1));
            resultSet.add(new Coordinate(x + 1, y));

            // Castling

            if (piece.getTeam() == TeamEnum.WHITE) {
                resultSet.add(new Coordinate(7, 8));
                resultSet.add(new Coordinate(3, 8));

            }
            if (piece.getTeam() == TeamEnum.BLACK) {
                resultSet.add(new Coordinate(7, 1));
                resultSet.add(new Coordinate(3, 1));

            }

            resultSet = removeIllegalCoordinatesFromSet(resultSet);
            return resultSet;
        }

        return resultSet;
    }

    /**
     * This method will return a Set<Coordinate> based containing the possible
     * moves a given piece in the given position could make. This method
     * completely disregards the legality of these moves however, and so its
     * results must be filtered by the isLegalMove() method, or by simply
     * calling getLegalMoves().
     *
     * @param piece
     * @param startPoint
     * @return The above described Set<Coordinate>
     */
    public Set<Coordinate> getPossibleMoves(Coordinate c) {
        Slot slot = getSlotObject(c);
        return getPossibleMoves(slot.getPiece(), slot.getCoordinate());
    }

    private boolean isTeamInCheck(TeamEnum defendingTeam) {
        Coordinate defendingKing = getKingCoordinate(defendingTeam);
        TeamEnum attackingTeam = TeamEnum.EMPTY;
        if (defendingTeam == TeamEnum.WHITE) {
            attackingTeam = TeamEnum.BLACK;
        }
        if (defendingTeam == TeamEnum.BLACK) {
            attackingTeam = TeamEnum.WHITE;
        }
        for (int i = 0; i < 8; i++) {
            for (int ii = 0; ii < 8; ii++) {
                Slot slot = boardArray[i][ii];
                if (slot.getPiece().getTeam() == attackingTeam) {
                    Set<Coordinate> targets = getLegalDestinations(slot);

                    for (Coordinate target : targets) {

                        if (defendingKing.x == target.x
                                && defendingKing.y == target.y) {
                            // King's in trouble
                            checkCulpritCoord = slot.getCoordinate();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Set<Coordinate> removeIllegalCoordinatesFromSet(Set<Coordinate> originalSet) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        resultSet.addAll(originalSet);
        for (Iterator<Coordinate> i = resultSet.iterator(); i.hasNext();) {
            Coordinate coord = i.next();
            int x = coord.x;
            int y = coord.y;
            if (x < 1 || x > 8 || y < 1 || y > 8) {
                i.remove();
            }
        }
        return resultSet;
    }

    private Set<Coordinate> removeCoordinateFromSet(Coordinate coord, Set<Coordinate> originalSet) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        resultSet.addAll(originalSet);
        int row = coord.x;
        int col = coord.y;
        for (Iterator<Coordinate> i = resultSet.iterator(); i.hasNext();) {
            coord = i.next();
            if (coord.x == row
                    && coord.y == col) {
                i.remove();
            }
        }
        return resultSet;
    }

    /**
     * Does what it says. Returns a Set<Coordinate> with all the points in a
     * positive (m = 1) diagonal line based off the given point
     *
     * @param c The given point
     * @return Set<Coordinate> with the points described above
     */
    private Set<Coordinate> getAllPointsInPositiveDiagonalLine(Coordinate c) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        Set<Coordinate> bigSet = new HashSet<Coordinate>();
        int x = c.x;
        int y = c.y;
        int newX;
        int newY;

        for (int i = 1; i < 9; i++) {
            newX = x + i;
            newY = y - i;
            bigSet.add(new Coordinate(newX, newY));
            newX = x - i;
            newY = y + i;
            bigSet.add(new Coordinate(newX, newY));
        }

        resultSet.addAll(bigSet);
        resultSet = removeIllegalCoordinatesFromSet(resultSet);
        return resultSet;
    }

    /**
     * Does what it says. Returns a Set<Coordinate> with all the points in a
     * negative (m = -1) diagonal line based off the given point
     *
     * @param c The given point
     * @return A Set<Coordinate> with the points described above
     */
    private Set<Coordinate> getAllPointsInNegativeDiagonalLine(Coordinate c) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        int x = c.x;
        int y = c.y;
        int newX;
        int newY;

        for (int i = 1; i < 9; i++) {
            newX = x + i;
            newY = y + i;
            resultSet.add(new Coordinate(newX, newY));


            newX = x - i;
            newY = y - i;
            resultSet.add(new Coordinate(newX, newY));

        }
        resultSet.addAll(resultSet);
        resultSet = removeIllegalCoordinatesFromSet(resultSet);
        return resultSet;
    }

    /**
     * Private convenience method. Returns all points on the same row as the
     * specified point, including the specified point.
     *
     * @param c The specified point
     * @return a Set<Coordinate> containing the points on the same row
     */
    private Set<Coordinate> getAllPointsOnSameRow(Coordinate c) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        int y = c.y;
        for (int x = 1; x < 9; x++) {
            resultSet.add(new Coordinate(x, y));
        }

        return resultSet;
    }

    /**
     * Private convenience method. Returns all points on the same column as the
     * specified point, including the specified point.
     *
     * @param c The specified point
     * @return a Set<Coordinate> containing the points on the same column
     */
    private Set<Coordinate> getAllPointsOnSameColumn(Coordinate c) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        int x = c.x;
        for (int y = 1; y < 9; y++) {
            resultSet.add(new Coordinate(x, y));
        }

        return resultSet;
    }

    public Set<Slot> getSlots(TeamEnum team) {
        Set<Slot> resultSet = new HashSet<Slot>();
        for (int i = 1; i < 9; i++) {
            for (int ii = 1; ii < 9; ii++) {
                Slot s = getSlotObject(i, ii);
                if (team == TeamEnum.WHITE) {
                    if (s.hasWhitePiece()) {
                        resultSet.add(s);
                    }
                }
                if (team == TeamEnum.BLACK) {
                    if (s.hasBlackPiece()) {
                        resultSet.add(s);
                    }
                }

            }
        }
        return resultSet;
    }

    public Set<Piece> getPieces(TeamEnum teamToGet) {
        if (teamToGet == TeamEnum.WHITE) {
            return whitePieces;
        }
        if (teamToGet == TeamEnum.BLACK) {
            return blackPieces;
        }
        return null;
    }

    public Set<Coordinate> getLegalDestinations(Coordinate c) {
        return getLegalDestinations(getSlotObject(c));
    }

    public Set<Coordinate> getLegalDestinations(Slot s) {
        Set<Coordinate> possibleMoves = getPossibleMoves(s.getPiece(), s.getCoordinate());

        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        for (Coordinate coord : possibleMoves) {
            Move possibleMove = new Move(s.getCoordinate(), coord);
            if (isLegalMove(possibleMove)) {
                resultSet.add(possibleMove.dest);
            }
        }
        return resultSet;
    }

    private boolean isMoveCastleQueenside(Move move) {

        Coordinate start = move.origin;
        Piece startPiece = getSlotObject(start).getPiece();
        PieceType startType = startPiece.getType();
        TeamEnum startTeam = startPiece.getTeam();
        Coordinate dest = move.dest;
        Piece destPiece = getSlotObject(dest).getPiece();
        TeamEnum destTeam = destPiece.getTeam();

        int backRank = 0;

        if (startTeam == TeamEnum.BLACK) {
            if (!blackCanCastleQueenside) {
                return false;
            }
            backRank = 1;
        }
        if (startTeam == TeamEnum.WHITE) {
            if (!whiteCanCastleQueenside) {
                return false;
            }
            backRank = 8;
        }

        if (startType == PieceType.KING
                && start.x == 5
                && dest.x == 3) {

            if (destTeam == TeamEnum.EMPTY) {
                Slot slot1 = getSlotObject(2, backRank);
                Slot slot2 = getSlotObject(3, backRank);
                Slot slot3 = getSlotObject(4, backRank);
                if (slot1.isEmpty()
                        && slot2.isEmpty()
                        && slot3.isEmpty()) {
                    return true;
                }
            }

        }

        return false;
    }

    private boolean isMoveCastleKingside(Move move) {

        Coordinate start = move.origin;
        Piece startPiece = getSlotObject(start).getPiece();
        PieceType startType = startPiece.getType();
        TeamEnum startTeam = startPiece.getTeam();
        Coordinate dest = move.dest;
        Piece destPiece = getSlotObject(dest).getPiece();
        TeamEnum destTeam = destPiece.getTeam();

        int backRank = 0;

        if (startTeam == TeamEnum.BLACK) {
            if (!blackCanCastleKingside) {
                return false;
            }
            backRank = 1;
        }
        if (startTeam == TeamEnum.WHITE) {
            if (!whiteCanCastleKingside) {
                return false;
            }
            backRank = 8;
        }

        if (startType == PieceType.KING
                && start.x == 5
                && dest.x == 7) {

            if (destTeam == TeamEnum.EMPTY) {
                Slot slot1 = getSlotObject(6, backRank);
                Slot slot2 = getSlotObject(7, backRank);
                if (slot1.isEmpty()
                        && slot2.isEmpty()) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * This method returns true or false, depending on whether or not the
     * specified move is legal. This function does not consider the rules of
     * check. Run isTeamInCheck after tentatively making this move to determine
     * is full legality.
     *
     * @param move The Move to be considered
     * @return a primitive boolean either true or false
     */
    public boolean isLegalMove(Move move) {
        // Get my object handles
        Coordinate start = move.origin;
        Piece startPiece = getSlotObject(start).getPiece();
        PieceType startType = startPiece.getType();
        TeamEnum startTeam = startPiece.getTeam();
        Coordinate dest = move.dest;
        Piece destPiece = getSlotObject(dest).getPiece();
        PieceType destType = destPiece.getType();
        TeamEnum destTeam = destPiece.getTeam();


        // Check if the move is a Castle
        if (startType == PieceType.KING
                && start.x == 5
                && (dest.x == 7 || dest.x == 3)) {

            if (startTeam == TeamEnum.WHITE
                    && destTeam == TeamEnum.EMPTY) {
                Slot slot1 = getSlotObject(6, 8);
                Slot slot2 = getSlotObject(7, 8);
                Slot slot3;

                if (slot1.isEmpty()
                        && slot2.isEmpty()
                        && whiteCanCastleKingside) {
                    return true;
                }
                slot1 = getSlotObject(2, 8);
                slot2 = getSlotObject(3, 8);
                slot3 = getSlotObject(4, 8);
                if (slot1.isEmpty()
                        && slot2.isEmpty()
                        && slot3.isEmpty()
                        && whiteCanCastleQueenside) {
                    return true;
                }
            }
            if (startTeam == TeamEnum.BLACK
                    && destTeam == TeamEnum.EMPTY) {
                Slot slot1 = getSlotObject(6, 1);
                Slot slot2 = getSlotObject(7, 1);
                Slot slot3;

                if (slot1.isEmpty()
                        && slot2.isEmpty()
                        && blackCanCastleKingside) {
                    return true;
                }
                slot1 = getSlotObject(2, 1);
                slot2 = getSlotObject(3, 1);
                slot3 = getSlotObject(4, 1);
                if (slot1.isEmpty()
                        && slot2.isEmpty()
                        && slot3.isEmpty()
                        && blackCanCastleQueenside) {
                    return true;
                }
            }
            return false;
        }

        // the player is trying to move his piece
        // to a piece he already occupies. it's an illegal move.
        if (startTeam == destTeam) {
            return false;
        }

        Set<Coordinate> possibles = getPossibleMoves(startPiece, start);

        // If it's a knight, we needn't worry about jumping over pieces

        if (startType == PieceType.KNIGHT) {
            for (Coordinate destination : possibles) {
                if (destination.x == dest.x
                        && destination.y == dest.y) {
                    return true;
                }
            }
            return false;
        }

        if (startType == PieceType.BISHOP) {
            for (Coordinate destination : possibles) {
                if (destination.x == dest.x
                        && destination.y == dest.y) {
                    Set<Coordinate> tweeners = getAllPointsBetweenPointsDiagonal(start, dest);
                    for (Coordinate c : tweeners) {
                        Slot slot = getSlotObject(c);
                        if (!slot.isEmpty()) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        if (startType == PieceType.ROOK) {
            for (Coordinate destination : possibles) {
                if (destination.x == dest.x
                        && destination.y == dest.y) {
                    Set<Coordinate> tweeners = new HashSet<Coordinate>();
                    tweeners.addAll(getAllPointsBetweenPointsHorizontal(start, dest));
                    tweeners.addAll(getAllPointsBetweenPointsVertical(start, dest));
                    for (Coordinate c : tweeners) {
                        Slot slot = getSlotObject(c);
                        if (!slot.isEmpty()) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        if (startType == PieceType.PAWN) {
            for (Coordinate destination : possibles) {
                if (destination.x == dest.x
                        && destination.y == dest.y) {

                    // Non-capturing moves first

                    if (start.x == dest.x) {
                        if (destPiece.getType() == PieceType.EMPTY) {
                            return true;
                        }
                        return false;

                    }

                    // Capturing Moves

                    if (Math.abs(start.x - dest.x) == 1) { // Translation: if we're moving one file over
                        if (destPiece.getType() != PieceType.EMPTY) {
                            return true;
                        }
                        return false;
                    }
                }

            }
            return false;
        }
        if (startType == PieceType.QUEEN) {
            for (Coordinate destination : possibles) {
                if (destination.x == dest.x
                        && destination.y == dest.y) {
                    Set<Coordinate> tweeners = new HashSet<Coordinate>();
                    tweeners.addAll(getAllPointsBetweenPointsHorizontal(start, dest));
                    for (Coordinate c : tweeners) {
                        Slot slot = getSlotObject(c);
                        if (!slot.isEmpty()) {
                            return false;
                        }
                    }
                    tweeners.clear();
                    tweeners.addAll(getAllPointsBetweenPointsVertical(start, dest));
                    for (Coordinate c : tweeners) {
                        Slot slot = getSlotObject(c);
                        if (!slot.isEmpty()) {
                            return false;
                        }
                    }
                    tweeners.clear();
                    tweeners.addAll(getAllPointsBetweenPointsDiagonal(start, dest));
                    for (Coordinate c : tweeners) {
                        Slot slot = getSlotObject(c);
                        if (!slot.isEmpty()) {
                            return false;
                        }
                    }

                    return true;
                }
            }
            return false;
        }
        if (startType == PieceType.KING) {
            for (Coordinate destination : possibles) {
                if (dest.x == destination.x
                        && dest.y == destination.y) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Set<Coordinate> getTrulyLegalMoves(Coordinate c) {
        Set<Coordinate> resultSet = getLegalDestinations(c);
        Move move;
        for (Iterator<Coordinate> i = resultSet.iterator(); i.hasNext();) {
            Coordinate coord = i.next();
            move = new Move(c, coord);
            if (!isTrulyLegalMove(move, false)) {
                i.remove();
            }
        }
        return resultSet;
    }

    public Set<Coordinate> getTrulyLegalMoves(Slot slot) {
        return getTrulyLegalMoves(slot.getCoordinate());
    }

    public boolean isTrulyLegalMove(Move move, boolean updateNotice) {
        TeamEnum attackingTeam = getSlotObject(move.origin).getPiece().getTeam();

        if (isLegalMove(move)) {
            if (!doesMovePutTeamInCheck(move, attackingTeam, updateNotice)) {
                lastNoticeBody = "";
                lastNoticeTitle = getTeamToMove() + " to Move.";
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns whether or not a piece in question is being attacked
     * by another piece. This move does NOT take into account pinned pieces.
     * Even a pinned piece will be registered as "attacking"
     *
     * @param pieceInQuestion
     * @return
     */
    private boolean isPieceAttacked(Coordinate pieceInQuestion) {
        // Iterate through all of the slots, first asking whether they are controlled
        // by the enemy team. If so, we gather their potential moves in a list,
        // and then cross-check that list with our coordinate in question.
        // If there's a match, the pieces is being attacked. return true
        // If not, return false
        TeamEnum opposingTeam = getSlotObject(pieceInQuestion).getPiece().getTeam();
        for (Slot[] column : boardArray) {
            for (Slot slot : column) {
                if (slot.getPiece().getTeam() == opposingTeam) {
                    Set<Coordinate> targets = getLegalDestinations(slot);
                    for (Coordinate target : targets) {
                        if (target.x == pieceInQuestion.x
                                && target.y == pieceInQuestion.y) {
                            return true;
                        }
                    }
                }

            }
        }
        // Claim Victory
        return false;
    }

    /**
     * This method does what it looks like. Determines if a piece is pinned to
     * its king, and returns true or false.
     *
     * @param pieceInQuestion
     * @return The above described boolean primitive.
     */
    private boolean isPiecePinned(Coordinate pieceInQuestion) {
        Slot slotInQuestion = getSlotObject(pieceInQuestion);
        Piece backupCopy = slotInQuestion.getPiece();

        // Remove the piece in question
        slotInQuestion.clear();

        // Check to see if that puts the same team's king in check
        // and save the boolean (if we returned it now, we would not be
        // able to restore the piece
        TeamEnum defendingTeam = slotInQuestion.getPiece().getTeam();

        boolean isPinned = isPieceAttacked(getKingCoordinate(defendingTeam));

        // Restore the piece. Like it never happened...
        slotInQuestion.setPiece(backupCopy);

        // Claim Victory
        return isPinned;
    }

    /**
     * This function returns the slot object on which the King of the specified
     * team resides. If king isn't found (which means I screwed up) return value
     * is null. A.K.A, the White King.
     *
     * @param team Which King?
     * @return The Coordinate object previously discussed.
     */
    private Coordinate getKingCoordinate(TeamEnum team) {
        int count = 0;
        for (int y = 1; y < 9; y++) {
            for (int x = 1; x < 9; x++) {
                Slot slot = getSlotObject(x, y);
                if (slot.getPiece().getTeam() == team) {
                    if (slot.getPiece().getType() == PieceType.KING) {
                        return slot.getCoordinate();
                    }
                }
                count++;
            }
        }
        return null;
    }

    /**
     * This function returns the slot object on which the Queen of the specified
     * team resides. If the queen isn't found, return value is null.
     *
     * @param team Which Queen?
     * @return The Coordinate object previously discussed.
     */
    private Coordinate getQueenCoordinate(TeamEnum team) {
        for (Slot[] column : boardArray) {
            for (Slot slot : column) {
                if (slot.getPiece().getTeam() == team) {
                    if (slot.getPiece().getType() == PieceType.QUEEN) {
                        return slot.getCoordinate();
                    }
                }
            }
        }
        return null;
    }

    private Set<Coordinate> getAllPointsBetweenPointsHorizontal(Coordinate a, Coordinate b) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        if (b.y != a.y) {
            return resultSet;
        }
        int y = b.y;
        int larger = Math.max(b.x, a.x);
        int smaller = Math.min(b.x, a.x);
        int difference = larger - smaller;
        for (int x = 1; x < difference; x++) {
            int newX = smaller + x;
            if (newX > smaller && newX < larger) {
                resultSet.add(new Coordinate(newX, y));
            }
        }
        resultSet = removeIllegalCoordinatesFromSet(resultSet);
        return resultSet;
    }

    private Set<Coordinate> getAllPointsBetweenPointsVertical(Coordinate a, Coordinate b) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();
        if (b.x != a.x) {
            return resultSet;
        }
        int x = b.x;
        int larger = Math.max(b.y, a.y);
        int smaller = Math.min(b.y, a.y);
        int difference = larger - smaller;
        for (int y = 1; y < difference; y++) {
            int newY = smaller + y;
            if (newY > smaller && newY < larger) {
                resultSet.add(new Coordinate(x, newY));
            }
        }
        resultSet = removeIllegalCoordinatesFromSet(resultSet);
        return resultSet;
    }

    private Set<Coordinate> getAllPointsBetweenPointsDiagonal(Coordinate a, Coordinate b) {
        Set<Coordinate> resultSet = new HashSet<Coordinate>();

        // Determine slope orientation

        boolean posSlope = true;
        if (a.x > b.x) {
            if (a.y > b.y) {
                // Negative Slope
                posSlope = false;
            }
            if (a.y < b.y) {
                // Positive Slope
                posSlope = true;
            }
        }
        if (a.x < b.x) {
            if (a.y > b.y) {
                // Positive Slope
                posSlope = true;
            }
            if (a.y < b.y) {
                // Negative Slope
                posSlope = false;
            }
        }

        int smallerX = Math.min(a.x, b.x);
        int smallerY = Math.min(a.y, b.y);
        int largerX = Math.max(a.x, b.x);
        int largerY = Math.max(a.y, b.y);
        int difference = largerX - smallerX;

        if (largerX - smallerX != largerY - smallerY) {
            return resultSet;
        }

        if (!posSlope) {
            for (int n = 1; n < difference; n++) {
                int newY = smallerY + n;
                int newX = smallerX + n;
                resultSet.add(new Coordinate(newX, newY));
            }
        }
        if (posSlope) {
            for (int n = 1; n < difference; n++) {
                int newY = smallerY + n;
                int newX = largerX - n;


                resultSet.add(new Coordinate(newX, newY));

            }
        }
        resultSet = removeIllegalCoordinatesFromSet(resultSet);
        return resultSet;
    }

    private boolean doesMovePutTeamInCheck(Move move, TeamEnum teamInQuestion, boolean updateNotice) {

        // We'll have to deal with castling differently, considering all inbetween moves as well, and moving
        // the rooks appropriately
        Coordinate origin = move.origin;
        Coordinate destination = move.dest;
        Piece originBackup = getSlotObject(origin).getPiece();
        Piece destBackup = getSlotObject(destination).getPiece();

        getSlotObject(origin).clear();
        getSlotObject(destination).setPiece(originBackup);
        boolean retVal = isTeamInCheck(teamInQuestion);

        if (retVal && updateNotice) {
            TeamEnum teamWhosMoving = originBackup.getTeam();
            Piece culprit = getSlotObject(checkCulpritCoord).getPiece();
            if (teamWhosMoving == teamInQuestion) {
                lastNoticeTitle = "Illegal Move";
                lastNoticeBody = teamWhosMoving.toString() + "'s King "
                        + "would be in Check by the " + culprit + " on " + checkCulpritCoord;
            }
            if (teamWhosMoving != teamInQuestion) {
                Notice n = new CheckNotice(originBackup, destination);
                lastNoticeTitle = n.getTitle();
                lastNoticeBody = n.getBody();

            }
        }

        // Restore things

        getSlotObject(origin).setPiece(originBackup);
        getSlotObject(destination).setPiece(destBackup);

        return retVal;

    }

    String getLastNoticeTitle() {
        return lastNoticeTitle;
    }

    String getLastNoticeBody() {
        return lastNoticeBody;
    }

    private String getTeamToMove() {
        if (isWhitesTurn) {
            return "White";
        } else {
            return "Black";
        }
    }

    private boolean isMovePromotionMove(Move move) {
        Coordinate start = move.origin;
        Piece startPiece = getSlotObject(start).getPiece();
        PieceType startType = startPiece.getType();
        TeamEnum attackingTeam = startPiece.getTeam();
        Coordinate dest = move.dest;


        if (startType == PieceType.PAWN) {

            if (attackingTeam == TeamEnum.WHITE) {
                if (dest.y == 1) {
                    return true;
                }
            }
            if (attackingTeam == TeamEnum.BLACK) {
                if (dest.y == 8) {
                    return true;
                }
            }
            return false;

        }
        return false;
    }

    public PieceType askForPromotionType(TeamEnum team) {
        return game.askForPromotionType(team);
    }

    public static Set<PGNMove> convertSetToPGN(Set<Move> inputSet) {
        Set<PGNMove> resultSet = new LinkedHashSet<PGNMove>();

        // TODO some stuff

        return resultSet;
    }
}
