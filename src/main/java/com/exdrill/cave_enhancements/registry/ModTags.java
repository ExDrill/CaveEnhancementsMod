package com.exdrill.cave_enhancements.registry;

import com.exdrill.cave_enhancements.CaveEnhancements;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {

    public static final TagKey<BannerPattern> GOOP_PATTERN_ITEM = TagKey.of(Registry.BANNER_PATTERN_KEY, new Identifier(CaveEnhancements.NAMESPACE, "goop_pattern_item"));
}