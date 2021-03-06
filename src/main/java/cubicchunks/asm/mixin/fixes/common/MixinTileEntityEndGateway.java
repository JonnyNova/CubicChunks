/*
 *  This file is part of Cubic Chunks Mod, licensed under the MIT License (MIT).
 *
 *  Copyright (c) 2015 contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package cubicchunks.asm.mixin.fixes.common;

import static cubicchunks.asm.JvmNames.CHUNK_GET_TOP_FILLED_SEGMENT;

import cubicchunks.asm.JvmNames;
import cubicchunks.world.column.IColumn;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(TileEntityEndGateway.class)
public class MixinTileEntityEndGateway {

    @Redirect(method = "findExitPortal", at = @At(value = "INVOKE", target = CHUNK_GET_TOP_FILLED_SEGMENT))
    private int getChunkTopFilledSegmentExitFromPortal(Chunk chunk) {
        int top = chunk.getTopFilledSegment();
        if (top < 0) {
            return 0;
        }
        return top;
    }

    @Redirect(method = "findSpawnpointInChunk", at = @At(value = "INVOKE", target = CHUNK_GET_TOP_FILLED_SEGMENT))
    private static int getChunkTopFilledSegmentFindSpawnpoint(Chunk chunk) {
        int top = chunk.getTopFilledSegment();
        if (top < 0) {
            return 0;
        }
        return top;
    }

    /**
     * @author Barteks2x
     * @reason Make it generate cubes with cubic chunks so that it's filled with blocks
     */
    @Overwrite
    private static Chunk getChunk(World world, Vec3d pos) {
        Chunk chunk = world.getChunkFromChunkCoords(MathHelper.floor(pos.xCoord / 16.0D), MathHelper.floor(pos.zCoord / 16.0D));
        if (((IColumn) chunk).getCubicWorld().isCubicWorld()) {
            for (int cubeY = 0; cubeY < 16; cubeY++) {
                ((IColumn) chunk).getCube(cubeY);// load the cube
            }
        }
        return chunk;
    }
}
