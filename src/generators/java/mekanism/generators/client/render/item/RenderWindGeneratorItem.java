package mekanism.generators.client.render.item;

import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nonnull;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.item.ItemLayerWrapper;
import mekanism.client.render.item.MekanismItemStackRenderer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.client.model.ModelWindGenerator;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWindGeneratorItem extends MekanismItemStackRenderer {

    private static ModelWindGenerator windGenerator = new ModelWindGenerator();
    private static int angle = 0;
    public static ItemLayerWrapper model;

    @Override
    public void renderBlockSpecific(@Nonnull ItemStack stack, TransformType transformType) {
        GlStateManager.pushMatrix();
        GlStateManager.rotatef(180, 0, 0, 1);
        if (transformType == TransformType.THIRD_PERSON_RIGHT_HAND || transformType == TransformType.THIRD_PERSON_LEFT_HAND) {
            GlStateManager.rotatef(180, 0, 1, 0);
            GlStateManager.translatef(0, 0.4F, 0);
            if (transformType == TransformType.THIRD_PERSON_LEFT_HAND) {
                GlStateManager.rotatef(-45, 0, 1, 0);
            } else {
                GlStateManager.rotatef(45, 0, 1, 0);
            }
            GlStateManager.rotatef(50, 1, 0, 0);
            GlStateManager.translatef(2.0F, 2.0F, 2.0F);
            GlStateManager.translatef(0, -0.4F, 0);
        } else {
            if (transformType == TransformType.GUI) {
                GlStateManager.rotatef(90, 0, 1, 0);
            } else if (transformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
                GlStateManager.rotatef(180, 0, 1, 0);
            }
            GlStateManager.translatef(0, 0.4F, 0);
        }

        MekanismRenderer.bindTexture(MekanismUtils.getResource(ResourceType.RENDER, "wind_generator.png"));
        //TODO: Only update angle if the player is not in a blacklisted dimension, one that has no "wind".
        //The best way to do this would be to add an event listener for dimension change.
        //The event is server side only so we would need to send a packet to clients to tell them if they are
        //in a blacklisted dimension or not.
        angle = (angle + 2) % 360;
        windGenerator.render(0.016F, angle);
        GlStateManager.popMatrix();
    }

    @Override
    protected void renderItemSpecific(@Nonnull ItemStack stack, TransformType transformType) {
    }

    @Nonnull
    @Override
    protected TransformType getTransform(@Nonnull ItemStack stack) {
        return model.getTransform();
    }
}