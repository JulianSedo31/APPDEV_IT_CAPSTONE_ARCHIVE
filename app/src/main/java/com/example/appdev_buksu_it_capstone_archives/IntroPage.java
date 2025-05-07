package com.example.appdev_buksu_it_capstone_archives;

public class IntroPage {
    private final int imageResId;
    private final String title;
    private final String description;
    private static final int DEFAULT_IMAGE = R.drawable.ic_launcher_foreground; // Default app icon

    public IntroPage(int imageResId, String title, String description) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public static int getDefaultImage() {
        return DEFAULT_IMAGE;
    }
} 