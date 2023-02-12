package palaster.gj.core.helpers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class NBTHelper {
	public static ItemStack giveCompoundNBT(ItemStack stack) {
		stack.setTag(new CompoundTag());
		return stack;
	}

	public static ItemStack setIntegerToItemStack(ItemStack stack, String key, int value) {
		if(!stack.hasTag())
			stack = giveCompoundNBT(stack);
		stack.getTag().putInt(key, value);
		return stack;
	}
	
	public static ItemStack setBooleanToItemStack(ItemStack stack, String key, boolean value) {
		if (!stack.hasTag())
			stack = giveCompoundNBT(stack);
		stack.getTag().putBoolean(key, value);
		return stack;
	}
	
	public static ItemStack setStringToItemStack(ItemStack stack, String key, String value) {
		if(!stack.hasTag())
			stack = giveCompoundNBT(stack);
		stack.getTag().putString(key, value);
		return stack;
	}
	
	public static ItemStack setUUIDToItemStack(ItemStack stack, String key, @Nullable UUID value) {
		if(!stack.hasTag())
			stack = giveCompoundNBT(stack);
		if(value != null)
			stack.getTag().putUUID(key, value);
		return stack;
	}
	
	public static ItemStack setTagToItemStack(ItemStack stack, String key, @Nullable Tag value) {
		if(!stack.hasTag())
			stack = giveCompoundNBT(stack);
		if(value != null)
			stack.getTag().put(key, value);
		return stack;
	}
	
	public static int getIntegerFromItemStack(ItemStack stack, String key) {
		if(stack.hasTag())
			if(stack.getTag().contains(key))
				return stack.getTag().getInt(key);
		return -1;
	}
	
	public static boolean getBooleanFromItemStack(ItemStack stack, String key) {
		if(stack.hasTag())
			if(stack.getTag().contains(key))
				return stack.getTag().getBoolean(key);
		return false;
	}
	
	public static String getStringFromItemStack(ItemStack stack, String key) {
		if(stack.hasTag())
			if(stack.getTag().contains(key))
				return stack.getTag().getString(key);
		return "";
	}
	
	@Nullable
	public static UUID getUUIDFromItemStack(ItemStack stack, String key) {
		if(stack.hasTag())
			if(stack.getTag().hasUUID(key))
				return stack.getTag().getUUID(key);
		return null;
	}
	
	@Nullable
	public static Tag getTagFromItemStack(ItemStack stack, String key) {
		if(stack.hasTag())
			if(stack.getTag().contains(key))
				return stack.getTag().get(key);
		return null;
	}
}