package md2html;

import markup.*;

import java.util.*;

public class TextParser {
    private StringBuilder frame;
    private int current = 0;
    private int begin;

    public TextParser(StringBuilder frame) {
        this.frame = frame;
    }

    private boolean checkMarker() {
        char k = frame.charAt(current);
        if (!((current == 0 || Character.isWhitespace(frame.charAt(current - 1))) &&
                (current + 1 == frame.length() || Character.isWhitespace(frame.charAt(current + 1)))) &&
                (current == 0 || frame.charAt(current - 1) != '\\')) {
            if (k == '`' || k == '*' || k == '_' || k == '~') {
                return true;
            } else if (k == '-' && (current < frame.length() - 1) && frame.charAt(current + 1) == '-') {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void checkSpecial() {
        if (frame.charAt(current) == '&') {
            frame.replace(current, current + 1, "&amp;");
            current += 4;
        } else if (frame.charAt(current) == '>') {
            frame.replace(current, current + 1, "&gt;");
            current += 3;
        } else if (frame.charAt(current) == '<') {
            frame.replace(current, current + 1, "&lt;");
            current += 3;
        }
        if (current > 0 && frame.charAt(current - 1) == '\\') {
            frame.deleteCharAt(current - 1);
        }
    }

    public TextTool parseText() {
        int level = 0;

        if (Character.isWhitespace(frame.charAt(frame.length() - 1))) {
            frame.deleteCharAt(frame.length() - 1);
        }
        while (frame.charAt(current) == '#') {
            level++;
            current++;
        }
        if (!Character.isWhitespace(frame.charAt(current))) {
            level = 0;
            current = 0;
        }
        if (level > 0) current++;
        begin = current;

        List list = new LinkedList<TextTool>();

        while (current < frame.length()) {
            while (current < frame.length() && !checkMarker()) {
                checkSpecial();
                current++;
            }
            if (begin < current) {
                list.add(new Text(frame.substring(begin, current)));
            }
            if (current < frame.length() - 1) {
                list.add(parseChoose());
            }
        }

        if (level > 0) {
            return new Head(list, level);
        } else {
            return new Paragraph(list);
        }
    }

    private TextTool parseChoose() {
        if (frame.charAt(current) == frame.charAt(current + 1)) {
            current += 2;
            begin = current;
            return parseUnit(frame.charAt(current - 1), 2);
        } else {
            begin = ++current;
            return parseUnit(frame.charAt(current - 1), 1);
        }
    }

    private TextTool parseUnit(char sym, int num) {
        List list = new LinkedList<TextTool>();
        boolean closed = false;

        while (!closed) {
            while (!checkMarker()) {
                checkSpecial();
                current++;
            }

            if (begin < current) {
                list.add(new Text(frame.substring(begin, current)));
            }

            if (num == 1) {
                if (frame.charAt(current) == sym &&
                        (sym == '`' || !Character.isWhitespace(frame.charAt(current - 1)))) {
                    begin = ++current;
                    closed = true;
                } else {
                    list.add(parseChoose());
                }
            } else {
                if (frame.charAt(current) == sym && frame.charAt(current + 1) == sym &&
                        !Character.isWhitespace(frame.charAt(current - 1))) {
                    current += 2;
                    begin = current;
                    closed = true;
                } else {
                    list.add(parseChoose());
                }
            }
        }

        if (num == 1) {
            if (sym == '`') {
                return new Code(list);
            } else if (sym == '~') {
                return new Mark(list);
            } else {
                return new Emphasis(list);
            }
        } else {
            if (sym == '-') {
                return new Strikeout(list);
            } else {
                return new Strong(list);
            }
        }
    }
}