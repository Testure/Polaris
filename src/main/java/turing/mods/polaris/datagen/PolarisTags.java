package turing.mods.polaris.datagen;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import turing.mods.polaris.Polaris;

public class PolarisTags {
    public static final class Blocks {
        public static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.makeWrapperTag(Polaris.forgeLoc(path).toString());
        }

        public static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.makeWrapperTag(Polaris.modLoc(path).toString());
        }

        public static ITag.INamedTag<Block> mc(String path) {
            return BlockTags.makeWrapperTag(Polaris.mcLoc(path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> CIRCUIT_LOGIC = mod("circuit_logic");
        public static final ITag.INamedTag<Item> CIRCUIT_BASIC = mod("circuit_basic");
        public static final ITag.INamedTag<Item> CIRCUIT_GOOD = mod("circuit_good");
        public static final ITag.INamedTag<Item> CIRCUIT_ADVANCED = mod("circuit_advanced");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS = mod("crafting_tools");

        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_HAMMER = mod("crafting_tools/hammer");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_SOFT_HAMMER = mod("crafting_tools/soft_hammer");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_FILE = mod("crafting_tools/file");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_SAW = mod("crafting_tools/saw");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_WRENCH = mod("crafting_tools/wrench");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_SCREWDRIVER = mod("crafting_tools/screwdriver");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_CROWBAR = mod("crafting_tools/crowbar");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS_MORTAR = mod("crafting_tools/mortar");

        public static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.makeWrapperTag(Polaris.forgeLoc(path).toString());
        }

        public static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.makeWrapperTag(Polaris.modLoc(path).toString());
        }

        public static ITag.INamedTag<Item> mc(String path) {
            return ItemTags.makeWrapperTag(Polaris.mcLoc(path).toString());
        }
    }

    public static final class Fluids {
        public static ITag.INamedTag<Fluid> forge(String path) {
            return FluidTags.makeWrapperTag(Polaris.forgeLoc(path).toString());
        }

        public static ITag.INamedTag<Fluid> mod(String path) {
            return FluidTags.makeWrapperTag(Polaris.modLoc(path).toString());
        }

        public static ITag.INamedTag<Fluid> mc(String path) {
            return FluidTags.makeWrapperTag(Polaris.mcLoc(path).toString());
        }
    }
}
