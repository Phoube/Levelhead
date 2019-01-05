package club.sk1er.mods.levelhead.renderer;

import club.sk1er.mods.levelhead.Levelhead;
import club.sk1er.mods.levelhead.data.MediaheadItem;
import club.sk1er.mods.levelhead.data.MediaheadMediaType;
import club.sk1er.mods.levelhead.utils.JsonHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MediaheadTag extends LevelheadTag {


    private List<MediaheadItem> items = new ArrayList<>();

    public MediaheadTag(UUID owner) {
        super(owner);
    }

    public void apply(Levelhead levelhead, JsonHolder data) {
        for (MediaheadMediaType mediaheadMediaType : levelhead.getMediaheadMediaTypes()) {
            if (data.has(mediaheadMediaType.getBackend())) {
                this.items.add(new MediaheadItem(mediaheadMediaType, data.optString(mediaheadMediaType.getBackend())));
            }
        }
    }


    public List<MediaheadItem> getItems() {
        return items;
    }
}
