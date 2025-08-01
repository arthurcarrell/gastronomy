package com.asteristired.gastronomy.Blocks;

import com.asteristired.gastronomy.BlockEntities.CookingPotBlockEntity;
import com.asteristired.gastronomy.BlockEntities.ModBlockEntities;
import com.asteristired.gastronomy.Tags.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CookingPot extends BlockWithEntity {

    public static final BooleanProperty WATER = BooleanProperty.of("water");

    private static final VoxelShape SHAPE = Block.createCuboidShape(
            2.0, 0.0, 2.0,
            14.0, 8.0, 14.0
    );

    protected CookingPot(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(WATER, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        // fill with water
        if (!state.get(WATER) && player.getStackInHand(hand).getItem() == Items.WATER_BUCKET) {
            world.setBlockState(pos, state.with(WATER, true));
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                player.setStackInHand(hand, Items.BUCKET.getDefaultStack());
            }
            return ActionResult.success(world.isClient);

        } // remove water
        else if (state.get(WATER) && player.getStackInHand(hand).getItem() == Items.BUCKET) {
            world.setBlockState(pos, state.with(WATER, false));
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                player.setStackInHand(hand, Items.WATER_BUCKET.getDefaultStack());
            }
            return ActionResult.success(world.isClient);
        } // add item to cooking pot
        else if (state.get(WATER) && player.getStackInHand(hand).isIn(ModTags.CAN_BE_PLACED_IN_POT)) {
            // get the block entity of the cooking pot
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CookingPotBlockEntity cookingPotBlockEntity) {
                boolean isSuccess = cookingPotBlockEntity.addItem(player.getStackInHand(hand));
                world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                if (isSuccess) {
                    if (!player.isCreative()) {
                        ItemStack handStack = player.getStackInHand(hand);
                        handStack.decrement(1);
                        player.setStackInHand(hand, handStack);
                    }
                    return ActionResult.CONSUME;
                } else { // cooking pot is full, either cook or spit items out
                    if (world.getBlockState(pos.down(1)).isIn(ModTags.COOKING_POT_WARMERS)) {
                        cookingPotBlockEntity.CreateFoodItem();
                        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.PLAYERS);
                        return ActionResult.SUCCESS;
                    } else {
                        // spit out all four items.
                        cookingPotBlockEntity.spitItems();
                        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.PLAYERS);
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATER);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.COPPER;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public float getHardness() {
        return 1;
    }



    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CookingPotBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.COOKING_POT,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
