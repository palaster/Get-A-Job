/* TODO: fix me
package palaster.gj.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import palaster.gj.entities.SpineShooterEntity;

@OnlyIn(Dist.CLIENT)
public class ModelSpineShooter extends EntityModel<SpineShooterEntity> {
    public final ModelRenderer mouthLeft;
    public final ModelRenderer mouthRight;
    public final ModelRenderer mouthBack;
    public final ModelRenderer mouthTop;
    public final ModelRenderer mouthBottom;
    public final ModelRenderer mouthLeftBack;
    public final ModelRenderer mouthRightBack;
    public final ModelRenderer mouthTopBack;
    public final ModelRenderer mouthBottomBack;

    public ModelSpineShooter() {
    	super(RenderType::entitySolid);
    	texWidth = 64;
    	texHeight = 64;
        mouthBottomBack = new ModelRenderer(this, 24, 0);
        mouthBottomBack.mirror = true;
        mouthBottomBack.setPos(1.0F, 24.0F, 2.0F);
        // TODO: mouthBottomBack.setRotationPoint(1.0F, 24.0F, 2.0F);
        mouthBottomBack.addBox(-7.0F, 0.0F, -3.0F, 13, 1, 7, 0.0F);
        setRotateAngle(mouthBottomBack, 3.141592653589793F, 0.0F, 0.0F);
        mouthLeftBack = new ModelRenderer(this, 32, 7);
        mouthLeftBack.mirror = true;
        mouthLeftBack.setPos(7.0F, 9.0F, -2.0F);
        // TODO: mouthLeftBack.setRotationPoint(7.0F, 9.0F, -2.0F);
        mouthLeftBack.addBox(0.0F, 0.0F, 0.0F, 1, 14, 7, 0.0F);
        mouthTop = new ModelRenderer(this, 0, 36);
        mouthTop.setPos(0.5F, 9.0F, -2.0F);
        // TODO: mouthTop.setRotationPoint(0.5F, 9.0F, -2.0F);
        mouthTop.addBox(-7.0F, 0.0F, -7.0F, 14, 1, 7, 0.0F);
        setRotateAngle(mouthTop, 1.1344640137963142F, 0.0F, 0.0F);
        mouthRightBack = new ModelRenderer(this, 32, 7);
        mouthRightBack.setPos(-7.0F, 9.0F, -2.0F);
        // TODO: mouthRightBack.setRotationPoint(-7.0F, 9.0F, -2.0F);
        mouthRightBack.addBox(0.0F, 0.0F, 0.0F, 1, 14, 7, 0.0F);
        mouthTopBack = new ModelRenderer(this, 24, 0);
        mouthTopBack.setPos(-6.0F, 9.0F, -2.0F);
        // TODO: mouthTopBack.setRotationPoint(-6.0F, 9.0F, -2.0F);
        mouthTopBack.addBox(0.0F, 0.0F, 0.0F, 13, 1, 7, 0.0F);
        mouthBottom = new ModelRenderer(this, 0, 36);
        mouthBottom.mirror = true;
        mouthBottom.setPos(0.5F, 23.0F, -2.0F);
        // TODO: mouthBottom.setRotationPoint(0.5F, 23.0F, -2.0F);
        mouthBottom.addBox(-7.0F, 0.0F, -7.0F, 14, 1, 7, 0.0F);
        setRotateAngle(mouthBottom, -2.007128639793479F, -3.141592653589793F, 0.0F);
        mouthRight = new ModelRenderer(this, 0, 0);
        mouthRight.mirror = true;
        mouthRight.setPos(-7.0F, 16.0F, -2.0F);
        // TODO: mouthRight.setRotationPoint(-7.0F, 16.0F, -2.0F);
        mouthRight.addBox(0.0F, -7.0F, -7.0F, 1, 14, 7, 0.0F);
        setRotateAngle(mouthRight, 0.0F, -1.1344640137963142F, 0.0F);
        mouthLeft = new ModelRenderer(this, 0, 0);
        mouthLeft.setPos(7.0F, 16.0F, -2.0F);
        // TODO: mouthLeft.setRotationPoint(7.0F, 16.0F, -2.0F);
        mouthLeft.addBox(-1.0F, -7.0F, -7.0F, 1, 14, 7, 0.0F);
        setRotateAngle(mouthLeft, 0.0F, 1.1344640137963142F, 0.0F);
        mouthBack = new ModelRenderer(this, 0, 22);
        mouthBack.setPos(-6.0F, 10.0F, 5.0F);
        // TODO: mouthBack.setRotationPoint(-6.0F, 10.0F, 5.0F);
        mouthBack.addBox(0.0F, 0.0F, 0.0F, 13, 13, 1, 0.0F);
    }
    
    @Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int light, int overlay, float r, float g, float b, float a) {
    	mouthBottomBack.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthLeftBack.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthTop.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthRightBack.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthTopBack.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthBottom.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthRight.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthLeft.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
        mouthBack.render(matrixStack, vertexBuilder, light, overlay, r, g, b, a);
	}

    /* TODO: Old Render
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        mouthBottomBack.render(f5);
        mouthLeftBack.render(f5);
        mouthTop.render(f5);
        mouthRightBack.render(f5);
        mouthTopBack.render(f5);
        mouthBottom.render(f5);
        mouthRight.render(f5);
        mouthLeft.render(f5);
        mouthBack.render(f5);
    }
    */
/*
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    	modelRenderer.xRot = x;
    	modelRenderer.yRot = y;
    	modelRenderer.zRot = z;
    	/* TODO: Old rotateAngle
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
        */
/*
    }

	@Override
	public void setupAnim(SpineShooterEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) { }
}
*/