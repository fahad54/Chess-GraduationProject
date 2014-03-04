/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import chess.model.*;

/**
 *
 * @author Fahad
 */
public final class BoardView extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    private static final Color TRANS_RED = new Color(255, 0, 0, 75);
    private static final Color TRANS_GREEN = new Color(0, 255, 0, 50);
    private static final Color TRANS_BLUE = new Color(0, 0, 255, 150);
    private static final Color BARELY_TRANS_GRAY = new Color(175, 175, 175, 230);
    private static final Color TRANS_GRAY = new Color(175, 175, 175, 175);
    private static final Color TRANS_ORANGE = new Color(255, 140, 0, 100);
    private static final Color lightColor = Color.white;
    private static final Color darkColor = Color.black;
    private static BufferedImage whitePawnImage, whiteRookImage, whiteBishopImage, whiteKnightImage,
            whiteQueenImage, whiteKingImage;
    private static BufferedImage blackPawnImage, blackRookImage, blackBishopImage, blackKnightImage,
            blackQueenImage, blackKingImage;
    private static BufferedImage yellowSquare, greenSquare;
    private Coordinate moveStart, moveFinish, prevStart, prevFinish;
    private boolean getAMove = false;
    private Set<Coordinate> legals;
    private static final int FRAME_LENGTH = 600;
    private static final int GRID_LENGTH = FRAME_LENGTH / 8;
    private static final Rectangle FRAME_RECT = new Rectangle(0, 0, FRAME_LENGTH, FRAME_LENGTH);

    private BufferedImage figurineLayer;
    private BufferedImage boardLayer;
    private BufferedImage selectionLayer;
    private BufferedImage highlightLayer;
    private BufferedImage coachLayer;
    private BufferedImage illustrationLayer;
    private BufferedImage previousMoveLayer;
    private GameController controller;
    private boolean isBoardLive = true;

    public void setController(GameController gc) {
        this.controller = gc;
    }

    /** Creates new form ChessView */
    public BoardView() {
        initComponents();
        initLayers();
        initGraphics();
        legals = new HashSet<Coordinate>();

        drawImageIntoLayer(boardLayer, ImageFactory.loadBoardImage("resources/boards/white_blue.jpg", FRAME_LENGTH), FRAME_RECT);
        addMouseListener(this);
        addMouseMotionListener(this);
        Board board = new Board(null);
        board.populate();
        update(board);
    }
    private void initLayers() {
        boardLayer = new BufferedImage(FRAME_LENGTH, FRAME_LENGTH, BufferedImage.TYPE_4BYTE_ABGR);
        figurineLayer = new BufferedImage(FRAME_LENGTH, FRAME_LENGTH, BufferedImage.TYPE_4BYTE_ABGR);
        selectionLayer = new BufferedImage(FRAME_LENGTH, FRAME_LENGTH, BufferedImage.TYPE_4BYTE_ABGR);
        previousMoveLayer = new BufferedImage(FRAME_LENGTH, FRAME_LENGTH, BufferedImage.TYPE_4BYTE_ABGR);
        coachLayer = new BufferedImage(FRAME_LENGTH, FRAME_LENGTH, BufferedImage.TYPE_4BYTE_ABGR);
        illustrationLayer = new BufferedImage(FRAME_LENGTH, FRAME_LENGTH, BufferedImage.TYPE_4BYTE_ABGR);
        highlightLayer = new BufferedImage(FRAME_LENGTH, FRAME_LENGTH, BufferedImage.TYPE_4BYTE_ABGR);
    }
    private void initGraphics() {
        whitePawnImage = ImageFactory.getFigurineImage(PieceType.PAWN, TeamEnum.WHITE, GRID_LENGTH);
        whiteRookImage = ImageFactory.getFigurineImage(PieceType.ROOK, TeamEnum.WHITE, GRID_LENGTH);
        whiteBishopImage = ImageFactory.getFigurineImage(PieceType.BISHOP, TeamEnum.WHITE, GRID_LENGTH);
        whiteKnightImage = ImageFactory.getFigurineImage(PieceType.KNIGHT, TeamEnum.WHITE, GRID_LENGTH);
        whiteQueenImage = ImageFactory.getFigurineImage(PieceType.QUEEN, TeamEnum.WHITE, GRID_LENGTH);
        whiteKingImage = ImageFactory.getFigurineImage(PieceType.KING, TeamEnum.WHITE, GRID_LENGTH);

        blackPawnImage = ImageFactory.getFigurineImage(PieceType.PAWN, TeamEnum.BLACK, GRID_LENGTH);
        blackRookImage = ImageFactory.getFigurineImage(PieceType.ROOK, TeamEnum.BLACK, GRID_LENGTH);
        blackBishopImage = ImageFactory.getFigurineImage(PieceType.BISHOP, TeamEnum.BLACK, GRID_LENGTH);
        blackKnightImage = ImageFactory.getFigurineImage(PieceType.KNIGHT, TeamEnum.BLACK, GRID_LENGTH);
        blackQueenImage = ImageFactory.getFigurineImage(PieceType.QUEEN, TeamEnum.BLACK, GRID_LENGTH);
        blackKingImage = ImageFactory.getFigurineImage(PieceType.KING, TeamEnum.BLACK, GRID_LENGTH);
    }
    public void setIsBoardLive(boolean flag) {
        isBoardLive = flag;
    }

    private Rectangle getRectAtCoordinate(int x, int y) {
        int xx = x - 1;
        int yy = y - 1;

        return new Rectangle(xx * GRID_LENGTH, yy * GRID_LENGTH, GRID_LENGTH, GRID_LENGTH);
    }

    private Rectangle getRectAtCoordinate(Coordinate c) {
        return getRectAtCoordinate(c.x, c.y);
    }

    public void setPreviousMove(PGNMove m) {
        clearLayer(previousMoveLayer);
        prevStart = m.origin;
        prevFinish = m.dest;
        fillRectWithColorInImage(getRectAtCoordinate(prevStart), TRANS_GRAY, previousMoveLayer);
        fillRectWithColorInImage(getRectAtCoordinate(prevFinish), TRANS_GRAY, previousMoveLayer);

    }

    public void update(Board board) { //Updates the board when a move is made
        clearLayer(figurineLayer);//clear the current figures from the board
        Rectangle rect;
        BufferedImage imageToDraw;
        for (int i = 0; i < 8; i++) { //8x8 rectangles, 1st loop, 2-dimensional array
            for (int j = 0; j < 8; j++) { //8x8 rectangles, 2nd loop
                Slot slot = board.getSlotObject(i + 1, j + 1);//we ask the BOARD object for the slot, what piece is on this particular slot in the model
                rect = getRectAtCoordinate(i + 1, j + 1);//Then we get the rectangle
                imageToDraw = getAppropriateImage(slot.getPiece());//then we get the appropriate image to draw on that slot, it gets black or white piece accordingly
                if (imageToDraw != null) {
                    rect.height = rect.height - 5;//just to draw in the middle of the square due to java rendering
                    drawImageIntoLayer(figurineLayer, imageToDraw, rect);//draw the pieces through figurine layer.
                }
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(boardLayer, null, this);//draws board
        g2d.drawImage(selectionLayer, null, this);
        g2d.drawImage(illustrationLayer, null, this);//right click, for tutorials
        g2d.drawImage(previousMoveLayer, null, this);//red highlight prev. move layer
        g2d.drawImage(highlightLayer, null, this);//the green tiles created for move possibilities
        g2d.drawImage(coachLayer, null, this);//help with the game, stole from chessmaster
        g2d.drawImage(figurineLayer, null, this);//draws pieces, figures on board

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>
    // Variables declaration - do not modify
    // End of variables declaration

    private Coordinate getGridCoordinateFromRawCoordinate(int x, int y) {
        int xx = 0, yy = 0;
        double frameDouble = (double) FRAME_LENGTH;
        double q = (x * 8) / frameDouble;
        double w = (y * 8) / frameDouble;
        for (int i = 0; i < 8; i++) {
            if (q > i && q <= i + 1) {
                xx = i;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (w > i && w <= i + 1) {
                yy = i;
            }
        }
        xx++;
        yy++;
        return new Coordinate(xx, yy);
    }

    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON3) {
            Coordinate scaledCoord = getGridCoordinateFromRawCoordinate(me.getPoint().x, me.getPoint().y);
            fillRectWithColorInImage(getRectAtCoordinate(scaledCoord), TRANS_ORANGE, illustrationLayer);

        }
    }

    private void fillRectWithColorInImage(Rectangle r, Color color, BufferedImage canvas) {
        Graphics2D g2 = canvas.createGraphics();
        g2.setColor(color);
        g2.fill(r);
        g2.dispose();
        repaint();
    }

    public void highlightCoordinate(Coordinate c) {
        Rectangle daRect = getRectAtCoordinate(c.x, c.y);
        drawImageIntoLayer(selectionLayer, greenSquare, daRect);
    }

    private BufferedImage getAppropriateImage(Piece p) {
        PieceType type = p.getType();
        TeamEnum team = p.getTeam();
        BufferedImage imageToDraw = null;
        if (type == PieceType.EMPTY) {
            imageToDraw = null;
        }
        if (type == PieceType.PAWN) {
            // Draw a pawn in this rectangle
            if (team == TeamEnum.WHITE) {
                imageToDraw = whitePawnImage;
            }
            if (team == TeamEnum.BLACK) {
                imageToDraw = blackPawnImage;
            }
        }
        if (type == PieceType.ROOK) {
            if (team == TeamEnum.WHITE) {
                imageToDraw = whiteRookImage;
            }
            if (team == TeamEnum.BLACK) {
                imageToDraw = blackRookImage;
            }
        }
        if (type == PieceType.BISHOP) {
            if (team == TeamEnum.WHITE) {
                imageToDraw = whiteBishopImage;
            }
            if (team == TeamEnum.BLACK) {
                imageToDraw = blackBishopImage;
            }
        }
        if (type == PieceType.KNIGHT) {
            if (team == TeamEnum.WHITE) {
                imageToDraw = whiteKnightImage;
            }
            if (team == TeamEnum.BLACK) {
                imageToDraw = blackKnightImage;
            }
        }
        if (type == PieceType.QUEEN) {
            if (team == TeamEnum.WHITE) {
                imageToDraw = whiteQueenImage;
            }
            if (team == TeamEnum.BLACK) {
                imageToDraw = blackQueenImage;
            }
        }
        if (type == PieceType.KING) {
            if (team == TeamEnum.WHITE) {
                imageToDraw = whiteKingImage;
            }
            if (team == TeamEnum.BLACK) {
                imageToDraw = blackKingImage;
            }
        }

        return imageToDraw;

    }

    public void highlightCoordinates(Set<Coordinate> coordSet) {
        for (Coordinate c : coordSet) {
            highlightCoordinate(c);
        }
    }

    private void drawImageIntoLayer(BufferedImage canvas, BufferedImage item, Rectangle r) {
        Graphics2D g2 = canvas.createGraphics();
        g2.drawImage(item, r.x, r.y, r.width, r.height, this);
        g2.dispose();
        repaint();
    }

    private void drawLineIntoLayer(Coordinate a, Coordinate b, BufferedImage canvas) {
        Graphics2D g2 = canvas.createGraphics();

    }

    private void clearLayer(BufferedImage img) {
        Graphics2D g = img.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, FRAME_LENGTH, FRAME_LENGTH);
        g.dispose();
    }

    public void mousePressed(MouseEvent me) {
        if (!isBoardLive) {
            return;
        }
        Coordinate scaledCoord = getGridCoordinateFromRawCoordinate(me.getPoint().x, me.getPoint().y);
        moveStart = scaledCoord;

        // Clear the selection drawings

        if (me.getButton() == MouseEvent.BUTTON1) {
            clearLayer(illustrationLayer);
        }
        // Draw the yellow square on the clicked spot

        Rectangle r = getRectAtCoordinate(scaledCoord.x, scaledCoord.y);
        drawImageIntoLayer(selectionLayer, yellowSquare, r);


        if (controller.isDrawPossibleMovesOption()) {
            // Draw the possible moves if it's the right team's turn
            TeamEnum teamClicked = controller.getBoard().getSlotObject(moveStart).getPiece().getTeam();
            if (controller.getWhoseTurnItIs() == teamClicked) {
                legals = controller.getBoard().getTrulyLegalMoves(scaledCoord);
                clearLayer(previousMoveLayer);
                for (Coordinate c : legals) {
                    fillRectWithColorInImage(getRectAtCoordinate(c), TRANS_GREEN, coachLayer);
                }
            }


        }

        if (controller.isDrawBoardControlOption()) {
            // Draw the board control
            // TODO implement this bad boy. Possibly use progressively deeper colors
            // for squares that are controlled by more than once piece.
        }

    }

    public void mouseReleased(MouseEvent me) {
        if (!isBoardLive) {
            return;
        }
        clearLayer(selectionLayer);
        legals.clear();
        if (getAMove) {

            Coordinate scaledCoord = getGridCoordinateFromRawCoordinate(me.getPoint().x, me.getPoint().y);

            Piece pieceToMove = controller.getBoard().getSlotObject(moveStart).getPiece();
            moveFinish = scaledCoord;
            TeamEnum turn = controller.getWhoseTurnItIs();
            if (turn == TeamEnum.WHITE
                    && pieceToMove.getTeam() == TeamEnum.WHITE) {
                moveFinish = scaledCoord;
                getAMove = false;
            }
            if (turn == TeamEnum.BLACK
                    && pieceToMove.getTeam() == TeamEnum.BLACK) {
                moveFinish = scaledCoord;
                getAMove = false;
            }


        }


        clearLayer(coachLayer);
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public Move getMove() {
        return new Move(moveStart, moveFinish);
    }

    public void prepareToMove() {
        getAMove = true;
    }

    public boolean isWaitingForMove() {
        return getAMove;
    }

    public void waitForMove() {
        getAMove = true;
    }

    public void mouseDragged(MouseEvent me) {

        if (me.isShiftDown()) {
            // Illustration
        } else {
            clearLayer(selectionLayer);

            Coordinate scaledCoord = getGridCoordinateFromRawCoordinate(me.getX(), me.getY());
            if (scaledCoord.x == moveStart.x
                    && scaledCoord.y == moveStart.y) {
                return;
            }
            TeamEnum teamClicked = controller.getBoard().getSlotObject(moveStart).getPiece().getTeam();
            if (controller.getWhoseTurnItIs() == teamClicked) {
                for (Coordinate c : legals) {
                    if (scaledCoord.x == c.x
                            && scaledCoord.y == c.y) {
                        fillRectWithColorInImage(getRectAtCoordinate(scaledCoord), BARELY_TRANS_GRAY, selectionLayer);

                    }
                }
            }

        }


    }

    public void mouseMoved(MouseEvent me) {
    }
}
