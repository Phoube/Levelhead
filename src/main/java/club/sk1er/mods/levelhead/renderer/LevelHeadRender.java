package club.sk1er.mods.levelhead.renderer;

import club.sk1er.mods.levelhead.Levelhead;
import club.sk1er.mods.levelhead.utils.Sk1erMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by mitchellkatz
 * <p>
 * Modified by boomboompower on 16/6/2017
 */
public class LevelHeadRender {

    private Levelhead levelHead;

    public LevelHeadRender(Levelhead levelHead) {
        this.levelHead = levelHead;
    }

    @SubscribeEvent
    public void render(RenderPlayerEvent.Pre event) {
        if ((event.entityPlayer.getUniqueID().equals(Levelhead.getInstance().userUuid) && !levelHead.getConfig().isShowSelf()) || !Sk1erMod.getInstance().isHypixel() && !levelHead.dev)
            return;

        EntityPlayer player = event.entityPlayer;
        // TODO - "entityPlayer" field is private for newer versions (PORTING REQUIRED) - Newer versions use a getter instead
        // TODO - https://github.com/MinecraftForge/MinecraftForge/blob/1.11.x/src/main/java/net/minecraftforge/event/entity/player/PlayerEvent.java#L51-L54

        if (levelHead.loadOrRender(player) && (Levelhead.getInstance().getLevelString(player.getUniqueID())) != null) {
            if (player.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer) < 64 * 64) {
                double offset = 0.3;
                Scoreboard scoreboard = player.getWorldScoreboard();
                ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);

                if (scoreObjective != null && event.entityPlayer.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer) < 10 * 10) {
                    offset *= 2;
                }
                if (event.entityPlayer.getUniqueID().equals(Levelhead.getInstance().userUuid))
                    offset = -.1;
                renderName(event, (Levelhead.getInstance().getLevelString(player.getUniqueID())), player, event.x, event.y + offset, event.z);
            }
        }
    }

    public void renderName(RenderPlayerEvent event, LevelheadTag tag, EntityPlayer entityIn, double x, double y, double z) {
        FontRenderer fontrenderer = event.renderer.getFontRendererFromRenderManager();
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.0F, (float) y + entityIn.height + 0.5F, (float) z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-event.renderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(event.renderer.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int i = 0;

        int j = fontrenderer.getStringWidth(tag.getString()) / 2;
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double) (-j - 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double) (-j - 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double) (j + 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double) (j + 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        renderString(fontrenderer, tag);

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void renderString(FontRenderer renderer, LevelheadTag tag) {

        int y = 0;

        int x = -renderer.getStringWidth(tag.getString()) / 2;
        //Render header
        LevelheadComponent header = tag.getHeader();
        render(renderer, header, x);
        x += renderer.getStringWidth(header.getValue());
        //render footer
        render(renderer, tag.getFooter(), x);

    }

    private void render(FontRenderer renderer, LevelheadComponent header, int x) {
        int y = 0;
        if (header.isRgb()) {
            renderer.drawString(header.getValue(), x, y, new Color(header.getRed(), header.getBlue(), header.getGreen(), header.getAlpha()).darker().getRGB());
        } else if (header.isChroma()) {
            renderer.drawString(header.getValue(), x, y, header.isChroma() ? Levelhead.getRGBColor() : 553648127);
        } else {
            renderer.drawString(header.getColor() + header.getValue(), x, y, 553648127);
        }
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        if (header.isRgb()) {
            renderer.drawString(header.getValue(), x, y, new Color(header.getRed(), header.getBlue(), header.getGreen(), header.getAlpha()).getRGB());
        } else if (header.isChroma()) {
            renderer.drawString(header.getValue(), x, y, header.isChroma() ? Levelhead.getRGBDarkColor() : Color.WHITE.getRGB());
        } else {
            renderer.drawString(header.getColor() + header.getValue(), x, y, Color.WHITE.getRGB());
        }

    }
}
