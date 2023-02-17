package palaster.gj.api.jobs;

import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InfoComponentTooltip {
    public final Component component;
    public final int minX;
    public final int maxX;
    public final int minY;
    public final int maxY;

    public InfoComponentTooltip(Component component, int minX, int maxX, int minY, int maxY) {
        this.component = component;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
}