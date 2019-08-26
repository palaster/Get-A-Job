package palaster.gj.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSpineShooter extends ModelBase {
    public ModelRenderer MouthLeft;
    public ModelRenderer MouthRight;
    public ModelRenderer MouthBack;
    public ModelRenderer MouthTop;
    public ModelRenderer MouthBottom;
    public ModelRenderer MouthLeftBack;
    public ModelRenderer MouthRightBack;
    public ModelRenderer MouthTopBack;
    public ModelRenderer MouthBottomBack;

    public ModelSpineShooter() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.MouthBottomBack = new ModelRenderer(this, 24, 0);
        this.MouthBottomBack.mirror = true;
        this.MouthBottomBack.setRotationPoint(1.0F, 24.0F, 2.0F);
        this.MouthBottomBack.addBox(-7.0F, 0.0F, -3.0F, 13, 1, 7, 0.0F);
        this.setRotateAngle(MouthBottomBack, 3.141592653589793F, 0.0F, 0.0F);
        this.MouthLeftBack = new ModelRenderer(this, 32, 7);
        this.MouthLeftBack.mirror = true;
        this.MouthLeftBack.setRotationPoint(7.0F, 9.0F, -2.0F);
        this.MouthLeftBack.addBox(0.0F, 0.0F, 0.0F, 1, 14, 7, 0.0F);
        this.MouthTop = new ModelRenderer(this, 0, 36);
        this.MouthTop.setRotationPoint(0.5F, 9.0F, -2.0F);
        this.MouthTop.addBox(-7.0F, 0.0F, -7.0F, 14, 1, 7, 0.0F);
        this.setRotateAngle(MouthTop, 1.1344640137963142F, 0.0F, 0.0F);
        this.MouthRightBack = new ModelRenderer(this, 32, 7);
        this.MouthRightBack.setRotationPoint(-7.0F, 9.0F, -2.0F);
        this.MouthRightBack.addBox(0.0F, 0.0F, 0.0F, 1, 14, 7, 0.0F);
        this.MouthTopBack = new ModelRenderer(this, 24, 0);
        this.MouthTopBack.setRotationPoint(-6.0F, 9.0F, -2.0F);
        this.MouthTopBack.addBox(0.0F, 0.0F, 0.0F, 13, 1, 7, 0.0F);
        this.MouthBottom = new ModelRenderer(this, 0, 36);
        this.MouthBottom.mirror = true;
        this.MouthBottom.setRotationPoint(0.5F, 23.0F, -2.0F);
        this.MouthBottom.addBox(-7.0F, 0.0F, -7.0F, 14, 1, 7, 0.0F);
        this.setRotateAngle(MouthBottom, -2.007128639793479F, -3.141592653589793F, 0.0F);
        this.MouthRight = new ModelRenderer(this, 0, 0);
        this.MouthRight.mirror = true;
        this.MouthRight.setRotationPoint(-7.0F, 16.0F, -2.0F);
        this.MouthRight.addBox(0.0F, -7.0F, -7.0F, 1, 14, 7, 0.0F);
        this.setRotateAngle(MouthRight, 0.0F, -1.1344640137963142F, 0.0F);
        this.MouthLeft = new ModelRenderer(this, 0, 0);
        this.MouthLeft.setRotationPoint(7.0F, 16.0F, -2.0F);
        this.MouthLeft.addBox(-1.0F, -7.0F, -7.0F, 1, 14, 7, 0.0F);
        this.setRotateAngle(MouthLeft, 0.0F, 1.1344640137963142F, 0.0F);
        this.MouthBack = new ModelRenderer(this, 0, 22);
        this.MouthBack.setRotationPoint(-6.0F, 10.0F, 5.0F);
        this.MouthBack.addBox(0.0F, 0.0F, 0.0F, 13, 13, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.MouthBottomBack.render(f5);
        this.MouthLeftBack.render(f5);
        this.MouthTop.render(f5);
        this.MouthRightBack.render(f5);
        this.MouthTopBack.render(f5);
        this.MouthBottom.render(f5);
        this.MouthRight.render(f5);
        this.MouthLeft.render(f5);
        this.MouthBack.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}