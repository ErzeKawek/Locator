package com.Apothic0n.Locator.mixin;

import com.Apothic0n.Locator.core.events.CommonForgeEvents;
import com.google.common.base.Stopwatch;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static com.Apothic0n.Locator.config.CommonConfig.scanForTerrain;
import static net.minecraft.server.commands.LocateCommand.showLocateResult;

@Mixin(value = LocateCommand.class, priority = 69420)
public class LocateBiomeMixin {
    @Shadow @Final private static DynamicCommandExceptionType ERROR_BIOME_NOT_FOUND;

    /**
     * @author Apothicon
     * @reason Gives the command a million block range.
     */
    @Overwrite
    private static int locateBiome(CommandSourceStack cmdSource, ResourceOrTagArgument.Result<Biome> biome) throws CommandSyntaxException {
        BlockPos blockpos = BlockPos.containing(cmdSource.getPosition());
        ServerLevel level = cmdSource.getLevel();
        Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
        Pair<BlockPos, Holder<Biome>> pair = cmdSource.getLevel().findClosestBiome3d(biome, blockpos, 6400, 32, 64);
        stopwatch.stop();
        if (pair != null) {
            if (scanForTerrain == true) {
                BlockPos blockPos = pair.getFirst();
                int seaLevel = level.getSeaLevel();
                if (blockPos != null) {
                    ChunkPos chunkPos = level.getChunkAt(blockPos).getPos();
                    level.setChunkForced(chunkPos.x, chunkPos.z, true);
                    if ((blockPos.getY() >= seaLevel && level.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) > blockpos.getY() - 8) || blockPos.getY() < seaLevel) {
                        return showLocateResult(cmdSource, biome, blockpos, pair, "commands.locate.biome.success", true, stopwatch.elapsed());
                    }
                    level.setChunkForced(chunkPos.x, chunkPos.z, false);
                }
            } else {
                return showLocateResult(cmdSource, biome, blockpos, pair, "commands.locate.biome.success", true, stopwatch.elapsed());
            }
        }
        if (CommonForgeEvents.isScanningBiome == false) {
            CommonForgeEvents.isScanningBiome = true;
            CommonForgeEvents.biomeScanBlockpos = blockpos.east(10000);
            CommonForgeEvents.cmdSource = cmdSource;
            CommonForgeEvents.biome = biome;
        }
        throw ERROR_BIOME_NOT_FOUND.create(biome.asPrintable());
    }
}
