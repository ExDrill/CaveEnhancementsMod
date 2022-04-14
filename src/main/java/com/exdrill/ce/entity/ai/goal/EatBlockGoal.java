package com.exdrill.ce.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;

import com.exdrill.ce.entity.CruncherEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Control;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EatBlockGoal extends Goal {
    private final CruncherEntity mob;
    private final World world;
    private int timer;

    public EatBlockGoal(CruncherEntity mob) {
        this.mob = mob;
        this.world = mob.world;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    public boolean canStart() {
        if (this.mob.eatingTicks > 0 && this.mob.world.getTime() - this.mob.lastEatTick > 120L) {
            BlockPos blockPos = this.mob.getBlockPos().down();

                return this.world.getBlockState(blockPos.down()).isIn(BlockTags.BASE_STONE_OVERWORLD);
        }
        return false;
    }

    public void start() {
        this.timer = this.getTickCount(40);
        this.world.sendEntityStatus(this.mob, (byte)10);
        this.mob.getNavigation().stop();
    }

    public void stop() {
        this.mob.lastEatTick = this.mob.world.getTime();
        this.timer = 0;
    }

    public boolean shouldContinue() {
        return this.timer > 0;
    }

    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer == this.getTickCount(4)) {
            BlockPos blockPos = this.mob.getBlockPos().down();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                this.world.breakBlock(blockPos, false);
                this.mob.isEatingBlock = true;
            }

            this.mob.onEatingGrass();
            this.mob.emitGameEvent(GameEvent.EAT, this.mob.getCameraBlockPos());
        }
    }
}
