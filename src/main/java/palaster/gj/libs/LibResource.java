package palaster.gj.libs;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LibResource {
	
	private static final String GUI = "textures/gui/";
	private static final String MODELS = "textures/models/";
	
	public static final ResourceLocation BLANK = new ResourceLocation(LibMod.MODID, GUI + "blank.png");

	public static final ResourceLocation SPINE_SHOOTER = new ResourceLocation(LibMod.MODID, MODELS + "spine_shooter.png");
}
