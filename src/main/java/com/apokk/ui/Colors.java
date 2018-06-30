package com.apokk.ui;

public enum Colors {
    A_GREEN(0x6cff00),
    A_WHITE(0xffffff),
    A_BLUE(0x00c7ff),
    A_RED(0xff0000),
    A_ORANGE(0xffa900),
    A_MAGENTA(0xf613e7),
    A_YELLOW(0xf1f613),
    A_GREY(0x302931),
    A_LIGHT_GREY(0x525156),

    B_HORIZON_BLUE(0x0099cc),
    B_TURQUOISE(0x00d5d5),
    B_BLUE(0x00bde7),
    B_DARK_BLUE(0x5785dd),
    B_BROWN(0xb36600),
    B_GREY(0x33334c),
    B_LIGHT_GREY2(0x646464),
    B_LIGHT_GREY(0x4b4b4b),
    B_DARK_GREY(0x132525),
    B_DARK_MAGENTA(0xbd127c),
    B_MAGENTA(0xfb00fb),
    B_GREEN(0x00d500),
    B_WHITE(0xffffff),
    B_RED(0xfd0000),
    B_YELLOW(0xf7cc27),
    B_PURPLE(0x6a6bc5),
    B_PINK(0xdd6bdd);

    private Colors(int hex) {
        this.c = new Color(hex);
    }

    private final Color c;

    public Color color() {
        return c;
    }
}