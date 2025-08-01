package com.asteristired.gastronomy.BlockEntities;

import com.asteristired.gastronomy.Items.ModItems;
import com.asteristired.gastronomy.Tags.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CookingPotBlockEntity extends BlockEntity {
    public static final BooleanProperty WATER = BooleanProperty.of("water");
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);


    public CookingPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COOKING_POT, pos, state);
    }


    public boolean addItem(ItemStack itemStackOriginal) {
        // get the next free slot
        int currentFreeSlot = -1;
        int currentSlot = 0;

        ItemStack itemStack = itemStackOriginal.copy();

        for (ItemStack item : items) {
            if (item.isEmpty()) {
                currentFreeSlot = currentSlot;
            }

            currentSlot++;
        }

        // if the currentFreeSlot is set to -1, then the inventory is full, and we should return fail.
        if (currentFreeSlot == -1) return false;

        // set the count to one
        itemStack.setCount(1);

        // replace slot "currentFreeSlot" with one of our item.
        items.set(currentFreeSlot, itemStack);

        // inventory edited, so this needs to be marked as dirty
        this.markDirty();

        // return success
        return true;
    }

    public void spitItems() {
        int currentSlot = 0;
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                assert world != null;
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), item);
                items.set(currentSlot, ItemStack.EMPTY);
            }
            currentSlot++;
        }

        // inventory edited, so this needs to be marked as dirty
        this.markDirty();
    }

    public void CreateFoodItem() {
        int setHunger = 0;
        int setSaturation = 0;

        int categoryFruitVeg = 0; // worst category, but has most effects
        int categoryFish = 0; // same as meat
        int categoryGolden = 0; // gives bonus_hearts to the meal
        int categoryMeat = 0; // gives the most saturation and hunger
        int categorySugar = 0; // gives a LOT of saturation, but removes hunger

        int creativity = 0; // goes up when different food items are used.

        int bonus_hearts = 0;
        boolean stuffed = false;

        int currentSlot = 0;
        List<Item> cookedItems = new ArrayList<>();
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                // add that items category to categories
                if (item.isIn(ModTags.FISH)) categoryFish++;
                if (item.isIn(ModTags.FRUIT_AND_VEGETABLES)) categoryFruitVeg++;
                if (item.isIn(ModTags.GOLDEN_FOOD)) categoryGolden++;
                if (item.isIn(ModTags.MEAT)) categoryMeat++;
                if (item.isIn(ModTags.SUGAR)) categorySugar++;

                // add to creativity?
                if (!cookedItems.contains(item.getItem())) {
                    creativity++;
                } else {
                    creativity--;
                }

                cookedItems.add(item.getItem());
                items.set(currentSlot, ItemStack.EMPTY);
                currentSlot++;
            }
        }

        // inventory edited, so this needs to be marked as dirty
        this.markDirty();

        // calculate effects
        setHunger += (int) (1.5 * categoryFruitVeg);
        setHunger += (3 * (categoryFish + categoryMeat));
        setHunger += (2 * categoryGolden);
        setHunger -= (2 * categorySugar);

        setSaturation += (2 * categoryFruitVeg);
        setSaturation += (3 * categoryGolden);
        setSaturation += (3 * (categoryFish + categoryMeat));
        setSaturation += (5 * categorySugar);

        // calculate appearance, this is based on majority.
        int currentCustomModelData = 1;

        // majority textures
        if (categoryGolden >= 3) currentCustomModelData = 4;
        else if (categoryFish >= 3) currentCustomModelData = 2;
        else if (categoryMeat >= 3) currentCustomModelData = 6;
        else if (categoryFruitVeg >= 3) currentCustomModelData = 8;

        // minority textures
        else if (categoryMeat > 0) currentCustomModelData = 7;
        else if (categoryFish > 0) currentCustomModelData = 3;
        else if (categoryGolden > 0) currentCustomModelData = 5;

        // do final adjustments, add creativity score
        float creativityMultiplier = 1.0f + (float) creativity /10;

        // calculate absorption
        if (categoryGolden > 2) bonus_hearts = (int) (categoryGolden*0.5);

        // calculate stuffed buffs
        if (creativity >= 4) stuffed = true;

        // create the item
        ItemStack foodItem = new ItemStack(ModItems.COOKED_FOOD);
        NbtCompound nbt = foodItem.getOrCreateNbt();
        nbt.putInt("hunger", (int) (setHunger*creativityMultiplier));
        nbt.putInt("saturation", (int) (setSaturation*creativityMultiplier));
        nbt.putInt("bonus_hearts", bonus_hearts);
        nbt.putBoolean("stuffed", stuffed);
        nbt.putInt("CustomModelData", currentCustomModelData);


        assert world != null;
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), foodItem);

    }

    public void tick(World world, BlockPos pos, BlockState state) {
        // pass
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        System.out.println("Saving pot items: " + items);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,items);
        System.out.println("Loading pot items: " + items);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
        return nbt;
    }

    public DefaultedList<ItemStack> getItems() {
        return items;
    }


}
