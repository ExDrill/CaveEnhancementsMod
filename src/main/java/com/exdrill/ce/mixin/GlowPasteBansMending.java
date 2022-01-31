package com.exdrill.ce.mixin;

import com.exdrill.ce.registry.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Enchantment.class)
public class GlowPasteBansMending {
    @Inject(method="isAcceptableItem", at = @At("HEAD"), cancellable=true)
    private void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        if(stack.getItem() == ModItems.GLOW_PASTE){
            ci.setReturnValue(false);
        }
    }
}
