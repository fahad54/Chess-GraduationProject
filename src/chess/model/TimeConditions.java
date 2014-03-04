/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class TimeConditions {
    public final int whiteTime;
    public final int blackTime;
    public final boolean isTimed;
    public final int bonus;
    public final boolean isMinutes;

    public TimeConditions(int whiteTime, int blackTime, int bonus, boolean isMinutes) {
        if (whiteTime == 0 && blackTime == 0) {
            isTimed = false;
            this.whiteTime = 0;
            this.blackTime = 0;
        } else {
            this.whiteTime = whiteTime;
            this.blackTime = blackTime;
            isTimed = true;
        }
        this.isMinutes = isMinutes;
        this.bonus = bonus;
    }

}
