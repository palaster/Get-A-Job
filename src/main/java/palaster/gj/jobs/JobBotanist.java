package palaster.gj.jobs;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
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

public class JobBotanist implements IRPGJob {
    
    private static final int FLOURISH_RADIUS = 6;

    int flourishTimer = 1200;

    @Override
    public String getJobName() { return "gj.job.botanist"; }

    @Override
    public void drawExtraInformation(PoseStack ps, Font font, int mouseX, int mouseY, @Nullable Player player, int suggestedX, int suggestedY) {
        font.draw(ps, Component.translatable("gj.job.abilities"), suggestedX, suggestedY, 4210752);
        font.draw(ps, Component.translatable("gj.job.botanist.abilities.flourish"), suggestedX, suggestedY + 12, 4210752);
        font.draw(ps, Component.translatable("gj.job.botanist.abilities.bountiful_harvest"), suggestedX, suggestedY + 24, 4210752);
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
                flourishTimer = 1200;
            } else
                flourishTimer--;
        }
    }
}
