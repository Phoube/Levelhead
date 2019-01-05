package club.sk1er.mods.levelhead.data;

public class MediaheadItem {

    private MediaheadMediaType type;
    private String value;

    public MediaheadItem(MediaheadMediaType type, String value) {
        this.type = type;
        this.value = value;
    }

    public MediaheadMediaType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
