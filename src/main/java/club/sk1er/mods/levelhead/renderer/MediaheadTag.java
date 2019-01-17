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
        JsonHolder links = data.optJsonObject("links");
        for (MediaheadMediaType mediaheadMediaType : levelhead.getMediaheadMediaTypes()) {
            if (links.has(mediaheadMediaType.getBackend())) {
                MediaheadItem e = new MediaheadItem(mediaheadMediaType, links.optString(mediaheadMediaType.getBackend()));
                this.items.add(e);
                for (int i = 0; i < 100; i++) {
                    System.out.println("ADD: " + e);
                }
            }
        }
    }


    public List<MediaheadItem> getItems() {
        return items;
    }
}
