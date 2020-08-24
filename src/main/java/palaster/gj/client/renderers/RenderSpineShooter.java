package palaster.gj.client.renderers;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.gj.client.models.ModelSpineShooter;
import palaster.gj.entities.EntitySpineShooter;
import palaster.gj.libs.LibResource;

@SideOnly(Side.CLIENT)
public class RenderSpineShooter extends RenderLiving<EntitySpineShooter> {

    public RenderSpineShooter(RenderManager manager) { super(manager, new ModelSpineShooter(), 0.5f); }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySpineShooter entity) { return LibResource.SPINE_SHOOTER; }
}
