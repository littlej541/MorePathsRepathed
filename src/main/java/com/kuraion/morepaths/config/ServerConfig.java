package com.kuraion.morepaths.config;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_ENABLE_PATHS = "Allowed paths";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue ENABLE_DIRT_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_SAND_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_GRAVEL_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_SNOW_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_RED_SAND_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_COARSE_DIRT_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_MYCELIUM_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_PODZOL_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_CRIMSON_NYLIUM_PATH;
    public static ForgeConfigSpec.BooleanValue ENABLE_WARPED_NYLIUM_PATH;

    public static Boolean getPathTypeEnabled(Block pathType)
    {
        if(pathType == Blocks.DIRT)
            return ENABLE_DIRT_PATH.get();
        if(pathType == Blocks.SAND)
            return ENABLE_SAND_PATH.get();
        if(pathType == Blocks.GRAVEL)
            return ENABLE_GRAVEL_PATH.get();
        if(pathType == Blocks.SNOW_BLOCK)
            return ENABLE_SNOW_PATH.get();
        if(pathType == Blocks.RED_SAND)
            return ENABLE_RED_SAND_PATH.get();
        if(pathType == Blocks.COARSE_DIRT)
            return ENABLE_COARSE_DIRT_PATH.get();
        if(pathType == Blocks.MYCELIUM)
            return ENABLE_MYCELIUM_PATH.get();
        if(pathType == Blocks.PODZOL)
            return ENABLE_PODZOL_PATH.get();
        if(pathType == Blocks.CRIMSON_NYLIUM)
            return ENABLE_CRIMSON_NYLIUM_PATH.get();
        if(pathType == Blocks.WARPED_NYLIUM)
            return ENABLE_WARPED_NYLIUM_PATH.get();

        return false;
    }


    static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("Settings").push(CATEGORY_GENERAL);

        setupPathTypes(SERVER_BUILDER);

        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();


    }

    private static void setupPathTypes(ForgeConfigSpec.Builder SERVER_BUILDER) {

        SERVER_BUILDER.comment("Path Types").push(CATEGORY_ENABLE_PATHS);

        ENABLE_DIRT_PATH = SERVER_BUILDER.comment("Allow turning dirt directly into grass path")
                .define("enableDirtToPath", true);
        ENABLE_SAND_PATH = SERVER_BUILDER.comment("Enable sand path")
                .define("enableSandPath", true);
        ENABLE_GRAVEL_PATH = SERVER_BUILDER.comment("Enable gravel path")
                .define("enableGravelPath", true);
        ENABLE_SNOW_PATH = SERVER_BUILDER.comment("Enable snow path")
                .define("enableSnowPath", true);
        ENABLE_RED_SAND_PATH = SERVER_BUILDER.comment("Enable red sand path")
                .define("enableRedSandPath", true);
        ENABLE_COARSE_DIRT_PATH = SERVER_BUILDER.comment("Enable coarse dirt path")
                .define("enableCoarseDirtPath", true);
        ENABLE_MYCELIUM_PATH = SERVER_BUILDER.comment("Enable mycelium path")
                .define("enableMyceliumPath", true);
        ENABLE_PODZOL_PATH = SERVER_BUILDER.comment("Enable podzol path")
                .define("enablePodzolPath", true);
        ENABLE_CRIMSON_NYLIUM_PATH = SERVER_BUILDER.comment("Enable crimson nylium path")
                .define("enableCrimsonNyliumPath", true);
        ENABLE_WARPED_NYLIUM_PATH = SERVER_BUILDER.comment("Enable warped nylium path")
                .define("enableWarpedNyliumPath", true);

        SERVER_BUILDER.pop();
    }
}
