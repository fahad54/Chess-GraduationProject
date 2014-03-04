/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import chess.model.Piece;
import chess.model.PieceType;
import chess.model.TeamEnum;
/**
 *
 * @author Fahad
 */
public class PrisonView extends javax.swing.JPanel {

    private Rectangle whitePawnRect, whiteKnightRect, whiteBishopRect, whiteRookRect,
            whiteQueenRect;
    private Rectangle blackPawnRect, blackKnightRect, blackBishopRect, blackRookRect,
            blackQueenRect;
    private Set<Piece> capturedPieces;
    BufferedImage figurineLayer, counterLayer;
    boolean firstPaint = true;

    /** Creates new form PrisonView */
    public PrisonView() {
        initComponents();

    }

    public void setCapturedPieces(Set<Piece> set) {
        capturedPieces = set;
        if (counterLayer != null) {
            clearBufferedImage(counterLayer);

        }
        if (figurineLayer != null) {
            clearBufferedImage(figurineLayer);
        }
        updateLayers();
        repaint();
    }

    private void initRects(int width, int height) {
        int gridWidth = width / 5;
        int gridHeight = height / 2;
        whitePawnRect = new Rectangle(0, 0, gridWidth, gridHeight);
        whiteKnightRect = new Rectangle(gridWidth, 0, gridWidth, gridHeight);
        whiteBishopRect = new Rectangle(gridWidth * 2, 0, gridWidth, gridHeight);
        whiteRookRect = new Rectangle(gridWidth * 3, 0, gridWidth, gridHeight);
        whiteQueenRect = new Rectangle(gridWidth * 4, 0, gridWidth, gridHeight);

        blackPawnRect = new Rectangle(0, gridHeight, gridWidth, gridHeight);
        blackKnightRect = new Rectangle(gridWidth, gridHeight, gridWidth, gridHeight);
        blackBishopRect = new Rectangle(gridWidth * 2, gridHeight, gridWidth, gridHeight);
        blackRookRect = new Rectangle(gridWidth * 3, gridHeight, gridWidth, gridHeight);
        blackQueenRect = new Rectangle(gridWidth * 4, gridHeight, gridWidth, gridHeight);

    }

    public void clearBufferedImage(BufferedImage img) {
        Graphics2D g = img.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.dispose();
    }

    /**
     * Private internal function. This function updates the two BufferedImages which represent the entire view,
     * based off the class's capturedPieces member. This should be called just prior to repaint(), which simply
     * redraws the two buffered images.
     */
    private void updateLayers() {
        int wPC = 0, wNC = 0, wBC = 0, wRC = 0, wQC = 0;
        int bPC = 0, bNC = 0, bBC = 0, bRC = 0, bQC = 0;

        if (firstPaint) {
            int width = this.getWidth();
            int height = this.getHeight();

            figurineLayer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            counterLayer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

            capturedPieces = new HashSet<Piece>();

            initRects(width, height);

            firstPaint = false;
        }

        int width = this.getWidth();
        int height = this.getHeight();
        int gridWidth = width / 5;

        for (Piece piece : capturedPieces) {
            if (piece.isWhite()) {
                if (piece.isPawn()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.PAWN, TeamEnum.WHITE,
                            gridWidth), whitePawnRect);
                    wPC++;

                }
                if (piece.isKnight()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.KNIGHT, TeamEnum.WHITE,
                            gridWidth), whiteKnightRect);
                    wNC++;
                }
                if (piece.isBishop()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.BISHOP, TeamEnum.WHITE,
                            gridWidth), whiteBishopRect);
                    wBC++;
                }
                if (piece.isRook()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.ROOK, TeamEnum.WHITE,
                            gridWidth), whiteRookRect);
                    wRC++;
                }
                if (piece.isQueen()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.QUEEN, TeamEnum.WHITE,
                            gridWidth), whiteQueenRect);
                    wQC++;
                }

            }
            if (piece.isBlack()) {
                if (piece.isPawn()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.PAWN, TeamEnum.BLACK,
                            gridWidth), blackPawnRect);
                    bPC++;
                }
                if (piece.isKnight()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.KNIGHT, TeamEnum.BLACK,
                            gridWidth), blackKnightRect);
                    bNC++;
                }
                if (piece.isBishop()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.BISHOP, TeamEnum.BLACK,
                            gridWidth), blackBishopRect);
                    bBC++;
                }
                if (piece.isRook()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.ROOK, TeamEnum.BLACK,
                            gridWidth), blackRookRect);
                    bRC++;
                }
                if (piece.isQueen()) {
                    drawImageIntoLayer(figurineLayer, ImageFactory.getFigurineImage(PieceType.QUEEN, TeamEnum.BLACK,
                            gridWidth), blackQueenRect);
                    bQC++;
                }

            }
        }

        if (wPC > 1) {
            drawCounterIntoLayer(counterLayer, wPC, whitePawnRect);
        }
        if (wNC > 1) {
            drawCounterIntoLayer(counterLayer, wNC, whiteKnightRect);
        }
        if (wBC > 1) {
            drawCounterIntoLayer(counterLayer, wBC, whiteBishopRect);
        }
        if (wRC > 1) {
            drawCounterIntoLayer(counterLayer, wRC, whiteRookRect);
        }
        if (wQC > 1) {
            drawCounterIntoLayer(counterLayer, wQC, whiteQueenRect);
        }

        if (bPC > 1) {
            drawCounterIntoLayer(counterLayer, bPC, blackPawnRect);
        }
        if (bNC > 1) {
            drawCounterIntoLayer(counterLayer, bNC, blackKnightRect);
        }
        if (bBC > 1) {
            drawCounterIntoLayer(counterLayer, bBC, blackBishopRect);
        }
        if (bRC > 1) {
            drawCounterIntoLayer(counterLayer, bRC, blackRookRect);
        }
        if (bQC > 1) {
            drawCounterIntoLayer(counterLayer, bQC, blackQueenRect);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(figurineLayer, null, this);
        g2.drawImage(counterLayer, null, this);
    }

    private void drawCounterIntoLayer(BufferedImage canvas, int count, Rectangle whereToDraw) {
        Graphics2D g2 = canvas.createGraphics();
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.black);
        g2.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        String countString = Integer.toString(count);
        FontMetrics textMetrics = g2.getFontMetrics();
        String countString = Integer.toString(count);
        int xCoord = whereToDraw.x + whereToDraw.width - textMetrics.stringWidth(countString);
        int yCoord = whereToDraw.y + whereToDraw.height;
        g2.drawString(countString, xCoord, yCoord);
        g2.dispose();
    }

    private void drawImageIntoLayer(BufferedImage canvas, BufferedImage item, Rectangle r) {
        Graphics2D g2 = canvas.createGraphics();
        g2.drawImage(item, r.x, r.y, r.width, r.height, this);
        g2.dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(150, 25));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 320, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 108, Short.MAX_VALUE)
        );
    }// </editor-fold>
    // Variables declaration - do not modify
    // End of variables declaration
}
