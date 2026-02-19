package com.enzo.mod.block.entity;

import com.enzo.mod.screen.AlchemicalCrucibleScreenHandler;

import com.enzo.mod.item.ModItems;
import com.enzo.mod.util.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlchemicalCrucibleBlockEntity extends BlockEntity
        implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public AlchemicalCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMICAL_CRUCIBLE_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AlchemicalCrucibleBlockEntity.this.progress;
                    case 1 -> AlchemicalCrucibleBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlchemicalCrucibleBlockEntity.this.progress = value;
                    case 1 -> AlchemicalCrucibleBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.my_fabric_mod.alchemical_crucible");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlchemicalCrucibleScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        // 如果有需要传给客户端的数据可以在这里写
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("alchemical_crucible.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("alchemical_crucible.progress");
    }

    public static void tick(World world, BlockPos pos, BlockState state, AlchemicalCrucibleBlockEntity entity) {
        if (world.isClient())
            return;

        if (hasRecipe(entity) && hasFuel(entity)) {
            entity.progress++;
            markDirty(world, pos, state);

            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(AlchemicalCrucibleBlockEntity entity) {
        entity.removeStack(0, 1);
        entity.removeStack(1, 1);
        entity.setStack(2, new ItemStack(ModItems.MERCURY, entity.getStack(2).getCount() + 1));

        entity.resetProgress();
    }

    private static boolean hasRecipe(AlchemicalCrucibleBlockEntity entity) {
        boolean hasInput = entity.getStack(0).getItem() == Items.REDSTONE;
        ItemStack result = new ItemStack(ModItems.MERCURY);

        return hasInput && canInsertAmountIntoOutputSlot(entity, result.getCount())
                && canInsertItemIntoOutputSlot(entity, result.getItem());
    }

    private static boolean hasFuel(AlchemicalCrucibleBlockEntity entity) {
        // 简单处理：槽位1有煤炭就算有燃料
        return entity.getStack(1).getItem() == Items.COAL || entity.getStack(1).getItem() == Items.CHARCOAL;
    }

    private static boolean canInsertItemIntoOutputSlot(AlchemicalCrucibleBlockEntity entity, Item item) {
        return entity.getStack(2).getItem() == item || entity.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(AlchemicalCrucibleBlockEntity entity, int count) {
        return entity.getStack(2).getMaxCount() >= entity.getStack(2).getCount() + count;
    }
}
