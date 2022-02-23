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
            return BlockTags.bind(Polaris.forgeLoc(path).toString());
        }

        public static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(Polaris.modLoc(path).toString());
        }

        public static ITag.INamedTag<Block> mc(String path) {
            return BlockTags.bind(Polaris.mcLoc(path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> CIRCUIT_LOGIC = mod("circuit_logic");
        public static final ITag.INamedTag<Item> CIRCUIT_BASIC = mod("circuit_basic");
        public static final ITag.INamedTag<Item> CIRCUIT_GOOD = mod("circuit_good");
        public static final ITag.INamedTag<Item> CIRCUIT_ADVANCED = mod("circuit_advanced");
        public static final ITag.INamedTag<Item> CRAFTING_TOOLS = mod("crafting_tools");

        public static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(Polaris.forgeLoc(path).toString());
        }

        public static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(Polaris.modLoc(path).toString());
        }

        public static ITag.INamedTag<Item> mc(String path) {
            return ItemTags.bind(Polaris.mcLoc(path).toString());
        }
    }

    public static final class Fluids {
        public static ITag.INamedTag<Fluid> forge(String path) {
            return FluidTags.bind(Polaris.forgeLoc(path).toString());
        }

        public static ITag.INamedTag<Fluid> mod(String path) {
            return FluidTags.bind(Polaris.modLoc(path).toString());
        }

        public static ITag.INamedTag<Fluid> mc(String path) {
            return FluidTags.bind(Polaris.mcLoc(path).toString());
        }
    }
}
