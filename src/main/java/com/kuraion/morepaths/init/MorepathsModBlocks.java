package com.kuraion.morepaths.init;

import com.kuraion.morepaths.MorepathsMod;
import com.kuraion.morepaths.blocks.OtherPathBlock;
import com.kuraion.morepaths.config.ServerConfig;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MorepathsModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MorepathsMod.MODID);

    public static final RegistryObject<Block> SNOW_PATH = BLOCKS.register("snow_path", () -> createBlock(Blocks.SNOW_BLOCK));
    public static final RegistryObject<Block> GRAVEL_PATH = BLOCKS.register("gravel_path", () -> createBlock(Blocks.GRAVEL));
    public static final RegistryObject<Block> COARSE_DIRT_PATH = BLOCKS.register("coarse_dirt_path", () -> createBlock(Blocks.COARSE_DIRT));
    public static final RegistryObject<Block> SAND_PATH = BLOCKS.register("sand_path", () -> createBlock(Blocks.SAND));
    public static final RegistryObject<Block> RED_SAND_PATH = BLOCKS.register("red_sand_path", () -> createBlock(Blocks.RED_SAND));
    public static final RegistryObject<Block> PODZOL_PATH = BLOCKS.register("podzol_path", () -> createBlock(Blocks.PODZOL));
    public static final RegistryObject<Block> MYCELIUM_PATH = BLOCKS.register("mycelium_path", () -> createBlock(Blocks.MYCELIUM));
    public static final RegistryObject<Block> CRIMSON_GRASS_PATH = BLOCKS.register("crimson_nylium_path", () -> createBlock(Blocks.CRIMSON_NYLIUM));
    public static final RegistryObject<Block> WARPED_GRASS_PATH = BLOCKS.register("warped_nylium_path", () -> createBlock(Blocks.WARPED_NYLIUM));

    static HashMap<Block, DirtPathBlock> ALL_PATHS = new HashMap<Block, DirtPathBlock>();

    private static OtherPathBlock createBlock(Block fromBlock) {
        OtherPathBlock regBlock = new OtherPathBlock(fromBlock);
        ShovelItem.FLATTENABLES.put(fromBlock, regBlock.defaultBlockState());
        ALL_PATHS.put(fromBlock, regBlock);
        return regBlock;
    }

    public static Block getPathForBlock(Block fromBlock)
    {
        if(!ServerConfig.getPathTypeEnabled(fromBlock))
            return null;

        return ALL_PATHS.get(fromBlock);
    }

}
