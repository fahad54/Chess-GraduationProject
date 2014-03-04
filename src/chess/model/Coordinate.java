/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static int convertCharToInt(char c) {
        int i = 0;
        if (c == 'a') i = 1;
        if (c == 'b') i = 2;
        if (c == 'c') i = 3;
        if (c == 'd') i = 4;
        if (c == 'e') i = 5;
        if (c == 'f') i = 6;
        if (c == 'g') i = 7;
        if (c == 'h') i = 8;
        return i;
    }
    public String toStringX() {
        if (x == 1) {
            return("a");
        }
        if (x == 2) {
            return("b");
        }
        if (x == 3) {
            return("c");
        }
        if (x == 4) {
            return("d");
        }
        if (x == 5) {
            return("e");
        }
        if (x == 6) {
            return("f");
        }
        if (x == 7) {
            return("g");
        }
        if (x == 8) {
            return("h");
        }
        return "";
    }
    public String toStringY() {
        if (y == 1) {
            return("8");
        }
        if (y == 2) {
            return("7");
        }
        if (y == 3) {
            return("6");
        }
        if (y == 4) {
            return("5");
        }
        if (y == 5) {
            return("4");
        }
        if (y == 6) {
            return("3");
        }
        if (y == 7) {
            return("2");
        }
        if (y == 8) {
            return("1");
        }
        return "";
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (x == 1) {
            sb.append("a");
        }
        if (x == 2) {
            sb.append("b");
        }
        if (x == 3) {
            sb.append("c");
        }
        if (x == 4) {
            sb.append("d");
        }
        if (x == 5) {
            sb.append("e");
        }
        if (x == 6) {
            sb.append("f");
        }
        if (x == 7) {
            sb.append("g");
        }
        if (x == 8) {
            sb.append("h");
        }

        if (y == 1) {
            sb.append("8");
        }
        if (y == 2) {
            sb.append("7");
        }
        if (y == 3) {
            sb.append("6");
        }
        if (y == 4) {
            sb.append("5");
        }
        if (y == 5) {
            sb.append("4");
        }
        if (y == 6) {
            sb.append("3");
        }
        if (y == 7) {
            sb.append("2");
        }
        if (y == 8) {
            sb.append("1");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        return hash;
    }

}
