package club.sk1er.mods.levelhead.data;

public class MediaheadItem {

    private MediaheadMediaType type;
    private String value;
    private double centerX;
    private double centerY;
    private double centerZ;

    public MediaheadItem(MediaheadMediaType type, String value) {
        this.type = type;
        this.value = value;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getCenterZ() {
        return centerZ;
    }

    public void setCenterZ(double centerZ) {
        this.centerZ = centerZ;
    }

    @Override
    public String toString() {
        return "MediaheadItem{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

    public MediaheadMediaType getType() {
        return type;
    }

    public void setType(MediaheadMediaType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
