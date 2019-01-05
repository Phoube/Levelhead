package club.sk1er.mods.levelhead.display;

public enum DisplayPosition {

    ABOVE_HEAD,
    TAB,
    CHAT,
    MEDIAHEAD("mediahead");

    private String url = "levelheadv5";

    DisplayPosition() {

    }

    DisplayPosition(String base) {
        this.url = base;
    }

    public String getUrl() {
        return url;
    }

}
