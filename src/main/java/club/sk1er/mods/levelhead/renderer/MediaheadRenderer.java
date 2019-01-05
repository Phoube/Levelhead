package club.sk1er.mods.levelhead.renderer;

import club.sk1er.mods.levelhead.Levelhead;
import club.sk1er.mods.levelhead.data.MediaheadItem;
import club.sk1er.mods.levelhead.data.MediaheadMediaType;
import club.sk1er.mods.levelhead.display.MediaheadConfig;
import club.sk1er.mods.levelhead.display.MediaheadDisplay;
import club.sk1er.mods.levelhead.utils.Sk1erMod;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class MediaheadRenderer extends LevelheadAboveHeadRender {


    public MediaheadRenderer(Levelhead levelhead) {
        super(levelhead);
    }

    @SubscribeEvent
    public void render(RenderPlayerEvent.Pre event) {
        if (!levelhead.getDisplayManager().getMasterConfig().isEnabled()) {
            return;
        }
        if ((event.entityPlayer.getUniqueID().equals(Levelhead.getInstance().userUuid) && !((MediaheadConfig) levelhead.getDisplayManager().getMediaheadDisplay().getConfig()).isShowSelf()) || !Sk1erMod.getInstance().isHypixel()) {
            return;
        }
        MediaheadDisplay display = levelhead.getDisplayManager().getMediaheadDisplay();
        if (!display.getConfig().isEnabled()) {
            return;
        }

        MediaheadConfig config = (MediaheadConfig) display.getConfig();
        if (!config.isAlwaysShow() && !Levelhead.getInstance().isLobby()) {
            return;
        }
        EntityPlayer player = event.entityPlayer;
        LevelheadTag levelheadTag = display.getCache().get(player.getUniqueID());
        if (display.loadOrRender(player) && levelheadTag != null && !(levelheadTag instanceof NullLevelheadTag)) {
            MediaheadTag mediaheadTag = (MediaheadTag) levelheadTag;
            int i = 0;
            for (MediaheadItem item : mediaheadTag.getItems()) {
                render(event, item, player, config, i++);

            }

        }

    }

    private void render(RenderPlayerEvent event, MediaheadItem item, EntityPlayer entityIn, MediaheadConfig config, int i) {
        double x = entityIn.posX;
        double y = entityIn.posY;
        double z = entityIn.posZ;
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
        MediaheadMediaType mediaType = item.getType();
        GlStateManager.translate((64 + 15) * (i % 2 == 0 ? 1 : -1), ((i - 1) >> 1) * -64, 0);
        if (config.isUseImage()) {
            GlStateManager.enableTexture2D();
            if (mediaType.isReady()) {
                GlStateManager.bindTexture(mediaType.getTextureID());
                Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 64, 64, 16, 16, 64, 64);
            }
        } else {
            String message = mediaType.getName() + ": " + item.getValue();
            FontRenderer fontrenderer = event.renderer.getFontRendererFromRenderManager();
            int j = fontrenderer.getStringWidth(message) / 2;
            GlStateManager.disableTexture2D();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double) (-j - 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos((double) (-j - 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos((double) (j + 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos((double) (j + 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();

            render(fontrenderer,new LevelheadComponent(message),0);


        }


        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }


}
