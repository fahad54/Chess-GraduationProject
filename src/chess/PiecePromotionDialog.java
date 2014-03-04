/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import sun.awt.image.BufferedImageGraphicsConfig;
import chess.model.PieceType;
import chess.model.TeamEnum;
/**
 *
 * @author Fahad
 */
public class PiecePromotionDialog extends javax.swing.JDialog {

    private static final int THUMB_SIZE = 75;
    private final TeamEnum team;
    private static BufferedImage whiteRookImage, whiteBishopImage, whiteKnightImage,
            whiteQueenImage;
    private static BufferedImage blackRookImage, blackBishopImage, blackKnightImage,
            blackQueenImage;
    private PieceType pt;

    static {
        try {
            whiteRookImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/white_rook.png")), THUMB_SIZE, THUMB_SIZE);
            whiteBishopImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/white_bishop.png")), THUMB_SIZE, THUMB_SIZE);
            whiteKnightImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/white_knight.png")), THUMB_SIZE, THUMB_SIZE);
            whiteQueenImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/white_queen.png")), THUMB_SIZE, THUMB_SIZE);

            blackRookImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/black_rook.png")), THUMB_SIZE, THUMB_SIZE);
            blackBishopImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/black_bishop.png")), THUMB_SIZE, THUMB_SIZE);
            blackKnightImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/black_knight.png")), THUMB_SIZE, THUMB_SIZE);
            blackQueenImage = resizeTrick(ImageIO.read(
                    BoardView.class.getResourceAsStream("resources/black_queen.png")), THUMB_SIZE, THUMB_SIZE);
        } catch (IOException ex) {
            System.err.println("File Read Error");
        }
    }
    ImageIcon whiteRookIcon = new ImageIcon(whiteRookImage);
    ImageIcon whiteQueenIcon = new ImageIcon(whiteQueenImage);
    ImageIcon whiteBishopIcon = new ImageIcon(whiteBishopImage);
    ImageIcon whiteKnightIcon = new ImageIcon(whiteKnightImage);
    ImageIcon blackRookIcon = new ImageIcon(blackRookImage);
    ImageIcon blackKnightIcon = new ImageIcon(blackKnightImage);
    ImageIcon blackQueenIcon = new ImageIcon(blackQueenImage);
    ImageIcon blackBishopIcon = new ImageIcon(blackBishopImage);

    private static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g2 = result.createGraphics();
        g2.drawRenderedImage(image, null);
        g2.dispose();
        return result;
    }

    private static BufferedImage resizeTrick(BufferedImage image, int width, int height) {
        image = createCompatibleImage(image);
        image = resize(image, 100, 100);
        image = resize(image, width, height);
        return image;
    }

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    /** Creates new form PiecePromotionDialog */
    public PiecePromotionDialog(java.awt.Frame parent, boolean modal, TeamEnum team) {
        super(parent, modal);
        initComponents();
        this.team = team;
        initComponentsMyWay();
    }

    public void initComponentsMyWay() {

        if (team == TeamEnum.WHITE) {
            queenButton.setIcon(whiteQueenIcon);
            bishopButton.setIcon(whiteBishopIcon);
            rookButton.setIcon(whiteRookIcon);
            knightButton.setIcon(whiteKnightIcon);
        }
        if (team == TeamEnum.BLACK) {
            queenButton.setIcon(blackQueenIcon);
            bishopButton.setIcon(blackBishopIcon);
            rookButton.setIcon(blackRookIcon);
            knightButton.setIcon(blackKnightIcon);
        }

    }

    public PieceType getPieceType() {
        return pt;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        queenButton = new javax.swing.JButton();
        rookButton = new javax.swing.JButton();
        knightButton = new javax.swing.JButton();
        bishopButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(chess.ChessApp.class).getContext().getResourceMap(PiecePromotionDialog.class);
        queenButton.setText(resourceMap.getString("queenButton.text")); // NOI18N
        queenButton.setMaximumSize(new java.awt.Dimension(100, 100));
        queenButton.setMinimumSize(new java.awt.Dimension(100, 100));
        queenButton.setName("queenButton"); // NOI18N
        queenButton.setPreferredSize(new java.awt.Dimension(100, 100));
        queenButton.setSize(new java.awt.Dimension(100, 100));
        queenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queenButtonActionPerformed(evt);
            }
        });

        rookButton.setMaximumSize(new java.awt.Dimension(100, 100));
        rookButton.setMinimumSize(new java.awt.Dimension(100, 100));
        rookButton.setName("rookButton"); // NOI18N
        rookButton.setPreferredSize(new java.awt.Dimension(100, 100));
        rookButton.setSize(new java.awt.Dimension(100, 100));
        rookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rookButtonActionPerformed(evt);
            }
        });

        knightButton.setMaximumSize(new java.awt.Dimension(100, 100));
        knightButton.setMinimumSize(new java.awt.Dimension(100, 100));
        knightButton.setName("knightButton"); // NOI18N
        knightButton.setPreferredSize(new java.awt.Dimension(100, 100));
        knightButton.setSize(new java.awt.Dimension(100, 100));
        knightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                knightButtonActionPerformed(evt);
            }
        });

        bishopButton.setMaximumSize(new java.awt.Dimension(100, 100));
        bishopButton.setMinimumSize(new java.awt.Dimension(100, 100));
        bishopButton.setName("bishopButton"); // NOI18N
        bishopButton.setPreferredSize(new java.awt.Dimension(100, 100));
        bishopButton.setSize(new java.awt.Dimension(100, 100));
        bishopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bishopButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(queenButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(rookButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(bishopButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(6, 6, 6)
                        .add(knightButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(rookButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(queenButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(bishopButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(knightButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void queenButtonActionPerformed(java.awt.event.ActionEvent evt) {
        pt = PieceType.QUEEN;
        this.setVisible(false);
    }

    private void rookButtonActionPerformed(java.awt.event.ActionEvent evt) {
        pt = PieceType.ROOK;
        this.setVisible(false);
    }

    private void bishopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        pt = PieceType.BISHOP;
        this.setVisible(false);
    }

    private void knightButtonActionPerformed(java.awt.event.ActionEvent evt) {
        pt = PieceType.KNIGHT;
        this.setVisible(false);
    }
    // Variables declaration - do not modify
    private javax.swing.JButton bishopButton;
    private javax.swing.JButton knightButton;
    private javax.swing.JButton queenButton;
    private javax.swing.JButton rookButton;
    // End of variables declaration
}
