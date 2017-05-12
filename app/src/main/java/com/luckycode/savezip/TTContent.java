package com.luckycode.savezip;

/**
 * Created by Marce Cuevas on 10/05/17.
 */

public class TTContent {
    private String contentURL;
    private TTPath path;

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public void setPath(TTPath path) {
        this.path = path;
    }

    public String getContentURL() {
        return contentURL;
    }

    public TTPath getPath() {
        return path;
    }
}
