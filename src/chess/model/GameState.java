/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public enum GameState {
    BLACK_IS_MATED, WHITE_IS_MATED, BLACK_FORFEITS_TIME, WHITE_FORFEITS_TIME,
    WHITE_RESIGNS, BLACK_RESIGNS, UNFINISHED, STALEMATE, NOT_STARTED;


    @Override
    public String toString() {
       if (this == BLACK_IS_MATED) return "Black is Checkmated";
       if (this == WHITE_IS_MATED) return "White is Checkmated";
       if (this == UNFINISHED) return "In progress";
       if (this == STALEMATE) return "Stalemate";
       if (this == NOT_STARTED) return "Game not started";
       if (this == BLACK_FORFEITS_TIME) return "Black forfeits on Time";
       if (this == WHITE_FORFEITS_TIME) return "White forfeits on Time";
       if (this == WHITE_RESIGNS) return "White resigns";
       if (this == BLACK_RESIGNS) return "Black resigns";

       return "Dof";
    }


}
