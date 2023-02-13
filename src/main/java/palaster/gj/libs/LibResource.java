package palaster.gj.libs;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LibResource {
	
	private static final String GUI = "textures/gui/";
	private static final String MODELS = "textures/models/";
	
	public static final ResourceLocation BLANK = new ResourceLocation(LibMod.MODID, GUI + "blank.png");
}
