package club.sk1er.mods.levelhead.renderer;

import club.sk1er.mods.levelhead.Levelhead;
import club.sk1er.mods.levelhead.data.MediaheadItem;
import club.sk1er.mods.levelhead.data.MediaheadMediaType;
import club.sk1er.mods.levelhead.display.MediaheadConfig;
import club.sk1er.mods.levelhead.display.MediaheadDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class MediaheadRenderer extends LevelheadAboveHeadRender {


    public MediaheadRenderer(Levelhead levelhead) {
        super(levelhead);
    }

    @SubscribeEvent
    public void render(RenderPlayerEvent.Pre event) {
        MediaheadDisplay display = levelhead.getDisplayManager().getMediaheadDisplay();
//        if (!levelhead.getDisplayManager().getMasterConfig().isEnabled()) {
//            return;
//        }
//        if ((event.entityPlayer.getUniqueID().equals(Levelhead.getInstance().userUuid) && !((MediaheadConfig) levelhead.getDisplayManager().getMediaheadDisplay().getConfig()).isShowSelf()) || !Sk1erMod.getInstance().isHypixel()) {
//            return;
//        }
//        if (!display.getConfig().isEnabled()) {
//            return;
//        }
        MediaheadConfig config = (MediaheadConfig) display.getConfig();
        if (config.isAlwaysShow() || Levelhead.getInstance().isLobby()) {

            EntityPlayer player = event.entityPlayer;
            LevelheadTag levelheadTag = display.getCache().get(player.getUniqueID());
            boolean b = display.loadOrRender(player);
            boolean b1 = levelheadTag != null;
            boolean b2 = !(levelheadTag instanceof NullLevelheadTag);
            if (b && b1 && b2) {
                MediaheadTag mediaheadTag = (MediaheadTag) levelheadTag;
                int i = 0;
                for (MediaheadItem item : mediaheadTag.getItems()) {
                    render(event, item, player, config, i++);
                }

            }
        }

    }

    private void render(RenderPlayerEvent event, MediaheadItem item, EntityPlayer entityIn, MediaheadConfig config, int sel) {
        double x = event.x;
        double y = event.y;
        double z = event.z;
        FontRenderer fontrenderer = event.renderer.getFontRendererFromRenderManager();
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.0F, (float) y + entityIn.height + 0.5F, (float) z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-event.renderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(.7 * (sel % 2 == 0 ? 1 : -1), -.25 - ((sel) / 2 * .5), 0);
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
        MediaheadMediaType mediaType = item.getType();


        int j = fontrenderer.getStringWidth("hello world") / 2;
//        GlStateManager.disableTexture2D();
//        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
//        worldrenderer.pos((double) (-j - 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        worldrenderer.pos((double) (-j - 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        worldrenderer.pos((double) (j + 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        worldrenderer.pos((double) (j + 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        tessellator.draw();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        boolean hovered = false;

        item.setCenterX(entityIn.posX + Math.cos(entityIn.rotationYaw / 360F * Math.PI * 2) / 1.45D);
        item.setCenterY(entityIn.posY + entityIn.getEyeHeight() - ((sel) / 2 * .5) + .25);
        item.setCenterZ(entityIn.posZ + Math.sin(entityIn.rotationYaw / 360F * Math.PI * 2) / 1.45D);

        if (mediaType.isReady()) {
            GlStateManager.bindTexture(mediaType.getTextureID());
            int size = 16;
            if (hovered)
                size *= 2;
            Gui.drawScaledCustomSizeModalRect((-8 - (hovered ? 16 * (sel % 2 == 0 ? 1 : 0) : 0)), 0, 0, 0, 128, 128, size, size, 128, 128);
        }
//        render(fontrenderer, new LevelheadComponent("Hello"), 0);

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);

        GlStateManager.popMatrix();
        renderDebugBoundingBox(item, item.getCenterX() - Minecraft.getMinecraft().thePlayer.posX,
                item.getCenterY() - Minecraft.getMinecraft().thePlayer.posY,
                item.getCenterZ() - Minecraft.getMinecraft().thePlayer.posZ);


    }

    private void renderDebugBoundingBox(MediaheadItem item, double p_85094_2_, double p_85094_4_, double p_85094_6_) {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        float f = .5F;
        AxisAlignedBB axisalignedbb = new AxisAlignedBB(item.getCenterX() - .2, item.getCenterY() - .2, item.getCenterZ() - .2, item.getCenterX() + .2, item.getCenterY() + .2, item.getCenterZ() + .2);
        AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX - item.getCenterX() + p_85094_2_, axisalignedbb.minY - item.getCenterY() + p_85094_4_, axisalignedbb.minZ - item.getCenterZ() + p_85094_6_, axisalignedbb.maxX - item.getCenterX() + p_85094_2_, axisalignedbb.maxY - item.getCenterY() + p_85094_4_, axisalignedbb.maxZ - item.getCenterZ() + p_85094_6_);
        RenderGlobal.drawOutlinedBoundingBox(axisalignedbb1, 255, 255, 255, 255);

//        if (entityIn instanceof EntityLivingBase) {
//            float f1 = 0.01F;
//            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(p_85094_2_ - (double) f, p_85094_4_ + (double) entityIn.getEyeHeight() - 0.009999999776482582D, p_85094_6_ - (double) f, p_85094_2_ + (double) f, p_85094_4_ + (double) entityIn.getEyeHeight() + 0.009999999776482582D, p_85094_6_ + (double) f), 255, 0, 0, 255);
//        }

//        Tessellator tessellator = Tessellator.getInstance();
//        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
//        Vec3 vec3 = entityIn.getLook(p_85094_9_);
//        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
//        worldrenderer.pos(p_85094_2_, p_85094_4_ + (double) entityIn.getEyeHeight(), p_85094_6_).color(0, 0, 255, 255).endVertex();
//        worldrenderer.pos(p_85094_2_ + vec3.xCoord * 2.0D, p_85094_4_ + (double) entityIn.getEyeHeight() + vec3.yCoord * 2.0D, p_85094_6_ + vec3.zCoord * 2.0D).color(0, 0, 255, 255).endVertex();
//        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }


}
