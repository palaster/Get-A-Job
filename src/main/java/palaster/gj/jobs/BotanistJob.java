package palaster.gj.jobs;

import java.util.ArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.IPlantable;
import palaster.gj.api.capabilities.rpg.IRPG;
import palaster.gj.api.jobs.IRPGJob;
import palaster.gj.jobs.abilities.Abilities;

public class BotanistJob implements IRPGJob {
    
    private static final int FLOURISH_RADIUS = 6;
    private static final int FLOURISH_TIMER = 1200;

    int flourishTimer = FLOURISH_TIMER;

    @Override
    public String getJobName() { return "gj.job.botanist"; }

    @Override
    public void addExtraInformation(final ArrayList<Component> components) {
        components.add(Component.translatable("gj.job.botanist.abilities.flourish"));
        components.add(Component.translatable("gj.job.botanist.abilities.bountiful_harvest"));
    }
    
    @Override
    public boolean doUpdate() { return true; }

    @Override
    public void update(IRPG rpg, Player player) {
        if(rpg == null || player == null)
            return;
        if(Abilities.FLOURISH.isAvailable(rpg)) {
            if(flourishTimer <= 0) {
                for(double x = player.getX() - FLOURISH_RADIUS; x < player.getX() + FLOURISH_RADIUS; x++)
                    for(double y = player.getY() - FLOURISH_RADIUS; y < player.getY() + FLOURISH_RADIUS; y++)
                        for(double z = player.getZ() - FLOURISH_RADIUS; z < player.getZ() + FLOURISH_RADIUS; z++) {
                            BlockState state = player.level.getBlockState(new BlockPos(x, y, z));
                            if(state != null && state.getMaterial() != Material.AIR)
                                if(state.getBlock() instanceof BonemealableBlock && state.getBlock() instanceof IPlantable) {
                                	BonemealableBlock bonemealableBlock = (BonemealableBlock) state.getBlock();
                                    if(bonemealableBlock != null && player.level instanceof ServerLevel)
                                        if(bonemealableBlock.isValidBonemealTarget(player.level, new BlockPos(x, y, z), state, player.level.isClientSide))
                                            bonemealableBlock.performBonemeal((ServerLevel) player.level, player.level.random, new BlockPos(x, y, z), state);
                                }
                        }
                flourishTimer = FLOURISH_TIMER;
            } else
                flourishTimer--;
        }
    }
}
