/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import sun.awt.image.BufferedImageGraphicsConfig;
import chess.model.Piece;
import chess.model.PieceType;
import chess.model.TeamEnum;
/**
 *
 * @author Fahad
 */
public class ImageFactory {

    private static final String BOARD_LINK = "resources/boards/white_blue.jpg";
    private static BufferedImage whiteBlueBoardImage;
    private static BufferedImage whitePawnImage, whiteRookImage, whiteBishopImage, whiteKnightImage,
            whiteQueenImage, whiteKingImage;
    private static BufferedImage blackPawnImage, blackRookImage, blackBishopImage, blackKnightImage,
            blackQueenImage, blackKingImage;
    static {
        try {
            whiteBlueBoardImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/boards/white_blue.jpg"));
            whitePawnImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/white_pawn.png"));
            whiteRookImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/white_rook.png"));
            whiteBishopImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/white_bishop.png"));
            whiteKnightImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/white_knight.png"));
            whiteQueenImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/white_queen.png"));
            whiteKingImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/white_king.png"));

            blackPawnImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/black_pawn.png"));
            blackRookImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/black_rook.png"));
            blackBishopImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/black_bishop.png"));
            blackKnightImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/black_knight.png"));
            blackQueenImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/black_queen.png"));
            blackKingImage = ImageIO.read(
                    ImageFactory.class.getResourceAsStream("resources/black_king.png"));


        } catch (IOException ex) {
            System.out.println("File Read Error");
        }
    }

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
    private static BufferedImage resizeFigurineTrick(BufferedImage image, int width, int height) {
        image = createCompatibleImage(image);
        image = resize(image, 100, 100);
//        image = blurImage(image);
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

    private static BufferedImage blurImage(BufferedImage image) {
        float ninth = 1.0f / 9.0f;
        float fourth = 1.0f / 4.0f;
        float[] blurKernel = {
            ninth, ninth, ninth,
            ninth, ninth, ninth,
            ninth, ninth, ninth
        };
        float[] blurKernel2 = {
            fourth, fourth, fourth,
            fourth};
        Map map = new HashMap();

        map.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RenderingHints hints = new RenderingHints(map);
        BufferedImageOp op = new ConvolveOp(new Kernel(2, 2, blurKernel2), ConvolveOp.EDGE_NO_OP, hints);
        return op.filter(image, null);
    }
    public static BufferedImage getFigurineImage(PieceType type, TeamEnum team, int sideLength) {
        Piece p = new Piece(type, team);
        return getFigurineImage(p, sideLength);
    }
    public static BufferedImage getFigurineImage(Piece piece, int sideLength) {
        PieceType type = piece.getType();
        TeamEnum team = piece.getTeam();
        BufferedImage imageToDraw = null;

        if (type == PieceType.EMPTY) {
            imageToDraw = null;
        }
        if (type == PieceType.PAWN) {
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

        return resizeFigurineTrick(imageToDraw, sideLength, sideLength);
    }
    public static BufferedImage loadBoardImage(String file, int sideLength) {

        // Iterate through my pre-loaded board PNGs. If not found, return null.
        if (file.contains("resources/boards/white_blue.jpg")) {
            return whiteBlueBoardImage;
        }
        System.err.println("Unable to load Image resource from given string. Returning null.");
        return null;
    }
    public static BufferedImage createBoardImage(Color lightColor, Color darkColor,
            int sideLength) {

        BufferedImage b = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_4BYTE_ABGR);
        int gridLength = sideLength / 8;
        Graphics2D g = b.createGraphics();
        Rectangle gridRect;
        boolean darkLightToggle = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = j * gridLength;
                int y = i * gridLength;
                gridRect = new Rectangle(x, y, gridLength, gridLength);
                if (darkLightToggle) {
                    g.setColor(lightColor);
                    g.fill(gridRect);
                } else {
                    g.setColor(darkColor);
                    g.fill(gridRect);
                }
                darkLightToggle = !darkLightToggle;
            }
            darkLightToggle = !darkLightToggle;
        }

        return b;
    }
}
