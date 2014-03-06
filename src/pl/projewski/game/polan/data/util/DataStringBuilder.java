/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.util;

import java.util.Map;
import java.util.Set;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Role;
import pl.projewski.game.polan.data.RoleExperience;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class DataStringBuilder {

    private final StringBuilder sb = new StringBuilder();

    public DataStringBuilder() {
    }

    public DataStringBuilder appendNewLine() {
        sb.append("\r"); // TODO: System depended
        return this;
    }

    public DataStringBuilder append(final Creature human) {
        return this.append(human, false);
    }

    public DataStringBuilder append(final Creature human, boolean longInfo) {
        sb.append('[').append(human.getId()).append("] ");
        sb.append(human.getName()).append(" (").append(human.getActualRole().name().toLowerCase()).append(")");
        sb.append(" - ").append(human.getUserName());
        if (longInfo) {
            // TODO: Add information about known roles
        }
        return this;
    }

    public DataStringBuilder append(final Location location) {
        sb.append('[').append(location.getId());
        sb.append("] ");
        String username = location.getUsername();
        if (username == null) {
            sb.append("UNKNOWN");
        } else {
            sb.append(location.getType());
            sb.append(" (");
            sb.append(location.getSize());
            sb.append(") - ");
            sb.append(username);
            Set<Integer> connections = location.getConnection();
            if (connections != null && connections.size() > 0) {
                sb.append(" { ");
                for (Integer conn : connections) {
                    sb.append(conn);
                    sb.append(" ");
                }
                sb.append("}");
            }
        }
        return this;
    }

    public DataStringBuilder append(Object obj) {
        sb.append(obj);
        return this;
    }

    public DataStringBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public DataStringBuilder append(StringBuffer sb) {
        this.sb.append(sb);
        return this;
    }

    public DataStringBuilder append(CharSequence s) {
        sb.append(s);
        return this;
    }

    public DataStringBuilder append(CharSequence s, int start, int end) {
        sb.append(s, start, end);
        return this;
    }

    public DataStringBuilder append(char[] str) {
        sb.append(str);
        return this;
    }

    public DataStringBuilder append(char[] str, int offset, int len) {
        sb.append(str, offset, len);
        return this;
    }

    public DataStringBuilder append(boolean b) {
        sb.append(b);
        return this;
    }

    public DataStringBuilder append(char c) {
        sb.append(c);
        return this;
    }

    public DataStringBuilder append(int i) {
        sb.append(i);
        return this;
    }

    public DataStringBuilder append(long lng) {
        sb.append(lng);
        return this;
    }

    public DataStringBuilder append(float f) {
        sb.append(f);
        return this;
    }

    public DataStringBuilder append(double d) {
        sb.append(d);
        return this;
    }

    public DataStringBuilder appendCodePoint(int codePoint) {
        sb.appendCodePoint(codePoint);
        return this;
    }

    public DataStringBuilder delete(int start, int end) {
        sb.delete(start, end);
        return this;
    }

    public DataStringBuilder deleteCharAt(int index) {
        sb.deleteCharAt(index);
        return this;
    }

    public DataStringBuilder replace(int start, int end, String str) {
        sb.replace(start, end, str);
        return this;
    }

    public DataStringBuilder insert(int index, char[] str, int offset, int len) {
        sb.insert(index, str, offset, len);
        return this;
    }

    public DataStringBuilder insert(int offset, Object obj) {
        sb.insert(offset, obj);
        return this;
    }

    public DataStringBuilder insert(int offset, String str) {
        sb.insert(offset, str);
        return this;
    }

    public DataStringBuilder insert(int offset, char[] str) {
        sb.insert(offset, str);
        return this;
    }

    public DataStringBuilder insert(int dstOffset, CharSequence s) {
        sb.insert(dstOffset, s);
        return this;
    }

    public DataStringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        sb.insert(dstOffset, s, start, end);
        return this;
    }

    public DataStringBuilder insert(int offset, boolean b) {
        sb.insert(offset, b);
        return this;
    }

    public DataStringBuilder insert(int offset, char c) {
        sb.insert(offset, c);
        return this;
    }

    public DataStringBuilder insert(int offset, int i) {
        sb.insert(offset, i);
        return this;
    }

    public DataStringBuilder insert(int offset, long l) {
        sb.insert(offset, l);
        return this;
    }

    public DataStringBuilder insert(int offset, float f) {
        sb.insert(offset, f);
        return this;
    }

    public DataStringBuilder insert(int offset, double d) {
        sb.insert(offset, d);
        return this;
    }

    public int indexOf(String str) {
        return sb.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return sb.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return sb.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return sb.lastIndexOf(str, fromIndex);
    }

    public DataStringBuilder reverse() {
        sb.reverse();
        return this;
    }

    public String toString() {
        return sb.toString();
    }

}
