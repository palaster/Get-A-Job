package palaster.gj.jobs.spells;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public class BSSummonSpineShooter implements IBotanySpell {

    @Override
    public int getCost() { return 250; }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        //BlockPos spawnPos = itemUseContext.getClickedPos().relative(itemUseContext.getClickedFace());
        useOnContext.getPlayer().sendSystemMessage(Component.literal("Hasn't been implemented yet"));
        /*
        SpineShooterEntity ss = new SpineShooterEntity(itemUseContext.getLevel());
        ss.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        return itemUseContext.getLevel().addFreshEntity(ss) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        */
        return IBotanySpell.super.useOn(useOnContext);
    }
}
