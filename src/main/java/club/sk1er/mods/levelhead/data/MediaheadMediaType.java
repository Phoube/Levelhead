package club.sk1er.mods.levelhead.data;

import club.sk1er.mods.levelhead.Levelhead;
import club.sk1er.mods.levelhead.utils.Multithreading;
import net.minecraft.client.renderer.texture.DynamicTexture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MediaheadMediaType {

    private String name;
    private String backend;
    private String imageUrl;
    private boolean ready = false;
    private int textureID;

    public MediaheadMediaType(String name, String backend, String imageUrl) {
        this.name = name;
        this.backend = backend;
        this.imageUrl = imageUrl;
        Multithreading.runAsync(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setUseCaches(true);
                connection.addRequestProperty("User-Agent", "Mozilla/4.76 Levelhead V" + Levelhead.getInstance().getVersion());
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setDoOutput(true);
                InputStream is = connection.getInputStream();
                BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(is));
                Levelhead.getInstance().schedule(() -> {
                    MediaheadMediaType.this.textureID = new DynamicTexture(img).getGlTextureId();
                    MediaheadMediaType.this.ready = true;
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String toString() {
        return "MediaheadMediaType{" +
                "name='" + name + '\'' +
                ", backend='" + backend + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ready=" + ready +
                ", textureID=" + textureID +
                '}';
    }

    public int getTextureID() {
        return textureID;
    }

    public boolean isReady() {
        return ready;
    }

    public String getName() {
        return name;
    }

    public String getBackend() {
        return backend;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
