/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class CheckNotice implements Notice {
    private final Piece attacker;
    private final TeamEnum defendingTeam;
    private final Coordinate attackerCoord;

    public CheckNotice(Piece attacker, Coordinate ac) {
        this.attacker = attacker;
        this.attackerCoord = ac;
        if (attacker.getTeam() == TeamEnum.WHITE) {
            defendingTeam = TeamEnum.BLACK;
        } else if (attacker.getTeam() == TeamEnum.BLACK) {
            defendingTeam = TeamEnum.WHITE;
        } else {
            defendingTeam = TeamEnum.EMPTY;
        }
    }
    public String getBody() {
        StringBuilder sb = new StringBuilder();
        sb.append("The ");
        sb.append(defendingTeam.toString());
        sb.append(" King is now being checked by the ");
        sb.append(attacker.getType());
        sb.append(" on ");
        sb.append(attackerCoord.toString());

        return sb.toString();
    }

    public String getTitle() {
        return "Check!";
    }

}
