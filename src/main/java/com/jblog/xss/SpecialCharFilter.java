package com.jblog.xss;

import java.util.logging.Logger;

public final class SpecialCharFilter{

    private boolean vDebug = false;

    public SpecialCharFilter() {
    }

    public SpecialCharFilter(boolean vDebug) {
        this.vDebug = vDebug;
    }

    private String htmlSpecialChars(final String s) {
        String result = s;
        result = result.replaceAll("&", "&amp;");
        result = result.replaceAll("<", "&lt;");
        result = result.replaceAll(">", "&gt;");
        result = result.replaceAll("\"", "&quot;");
        result = result.replaceAll("'","&#39;");
        result = result.replaceAll("/","&#47;");
        result = result.replaceAll("\\(", "&#40;");
        result = result.replaceAll("\\)", "&#41;");
        result = result.replaceAll("eval", "&#101;&#118;&#97;&#108;");
        result = result.replaceAll("src", "&#115;&#114;&#99;");
        result = result.replaceAll("script","&#115;&#99;&#114;&#105;&#112;&#116;");
        return result;
    }

    private void debug(final String msg) {
        if (vDebug) {
            Logger.getAnonymousLogger().info(msg);
        }
    }

    public String filter(final String input) {
        String s = input;

        debug("************************************************");
        debug("              INPUT: " + input);

        s = htmlSpecialChars(s);
        debug("     htmlSpecialChars: " + s);

        debug("************************************************\n\n");
        return s;
    }
}
