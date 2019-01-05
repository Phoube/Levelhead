package club.sk1er.mods.levelhead.display;

import org.lwjgl.input.Keyboard;

public class MediaheadConfig extends DisplayConfig {

    private int key = Keyboard.KEY_P;
    private boolean alwaysShow = false;
    private boolean useImage = true;
    private boolean openOnkey = true;
    private boolean lobbyOnly = true;
    private boolean showSelf;
    public boolean isShowSelf() {
        return showSelf;
    }

    public void setShowSelf(boolean showSelf) {
        this.showSelf = showSelf;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public boolean isUseImage() {
        return useImage;
    }

    public void setUseImage(boolean useImage) {
        this.useImage = useImage;
    }

    public boolean isOpenOnkey() {
        return openOnkey;
    }

    public void setOpenOnkey(boolean openOnkey) {
        this.openOnkey = openOnkey;
    }

    public boolean isLobbyOnly() {
        return lobbyOnly;
    }

    public void setLobbyOnly(boolean lobbyOnly) {
        this.lobbyOnly = lobbyOnly;
    }


}
