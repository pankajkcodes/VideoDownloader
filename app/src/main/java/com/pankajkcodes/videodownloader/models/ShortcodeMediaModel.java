package com.pankajkcodes.videodownloader.models;

public class ShortcodeMediaModel {

    private MainUrlModel shortcode_media;

    public ShortcodeMediaModel(MainUrlModel shortcode_media) {
        this.shortcode_media = shortcode_media;
    }

    public MainUrlModel getShortcode_media() {
        return shortcode_media;
    }

    public void setShortcode_media(MainUrlModel shortcode_media) {
        this.shortcode_media = shortcode_media;
    }
}
