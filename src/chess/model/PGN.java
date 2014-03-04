/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Fahad
 */
public class PGN {
//    private final String site;
//    private final String date;
//    private final int round;
//    private final String whitePlayer;
//    private final String blackPlayer;
//    private final String result;
    private final Set<PGNMove> moveText;

    public PGN() {
        moveText = new HashSet<PGNMove>();

    }
}
