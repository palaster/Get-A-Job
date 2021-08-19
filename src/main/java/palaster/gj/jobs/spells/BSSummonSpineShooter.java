package palaster.gj.jobs.spells;

import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import palaster.libpal.core.helpers.PlayerHelper;

public class BSSummonSpineShooter implements IBotanySpell {

    @Override
    public int getCost() { return 250; }
    
    @Override
    public ActionResultType useOn(ItemUseContext itemUseContext) {
    	//BlockPos spawnPos = itemUseContext.getClickedPos().relative(itemUseContext.getClickedFace());
    	PlayerHelper.sendChatMessageToPlayer(itemUseContext.getPlayer(), "Hasn't been implemented yet");
    	/*
        SpineShooterEntity ss = new SpineShooterEntity(itemUseContext.getLevel());
        ss.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        return itemUseContext.getLevel().addFreshEntity(ss) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        */
    	return ActionResultType.PASS;
    }
}
