package palaster.gj.libs;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LibResource {
	
	private static final String GUI = "textures/gui/";
	//private static final String MODELS = "textures/models/";
	
	public static final ResourceLocation THIRD_HAND = new ResourceLocation(LibMod.MODID, GUI + "thirdHand.png");
}
