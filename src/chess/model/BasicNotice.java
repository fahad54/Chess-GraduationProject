/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.model;

/**
 *
 * @author Fahad
 */
public class BasicNotice implements Notice {
    public final String body;
    public final String title;
    public BasicNotice(String title, String body) {
        this.body = body;
        this.title = title;
    }
    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

}
