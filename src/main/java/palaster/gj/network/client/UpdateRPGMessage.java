package palaster.gj.network.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import palaster.gj.api.capabilities.IRPG;
import palaster.gj.api.capabilities.RPGCapability.RPGCapabilityProvider;
import palaster.libpal.network.AbstractMessage;

public class UpdateRPGMessage extends AbstractMessage.AbstractClientMessage<UpdateRPGMessage> {

	private NBTTagCompound tag = null;
	
	public UpdateRPGMessage() {}

    public UpdateRPGMessage(NBTTagCompound tag) { this.tag = tag; }

	@Override
	protected void read(PacketBuffer buffer) throws IOException { tag = ByteBufUtils.readTag(buffer); }

	@Override
	protected void write(PacketBuffer buffer) throws IOException { ByteBufUtils.writeTag(buffer, tag); }

	@Override
	protected void process(EntityPlayer player, Side side) {
		if(player != null && tag != null) {
			final IRPG rpg = RPGCapabilityProvider.get(player);
			if(rpg != null)
				rpg.loadNBT(tag);
		}
	}
}