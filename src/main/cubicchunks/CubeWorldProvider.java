/*******************************************************************************
 * This file is part of Cubic Chunks, licensed under the MIT License (MIT).
 * 
 * Copyright (c) Tall Worlds
 * Copyright (c) contributors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package cubicchunks;

import cubicchunks.generator.GeneratorPipeline;
import cubicchunks.generator.biome.WorldColumnManager;
import cubicchunks.generator.biome.WorldColumnManagerFlat;
import cubicchunks.generator.biome.biomegen.CubeBiomeGenBase;
import cubicchunks.server.CubeWorldServer;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.BiomeManagerSingle;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class CubeWorldProvider extends Dimension {
	
	@Override
	protected void registerBiomeManager() {
		// NOTE: this is the place we plug in different WorldColumnManagers for different dimensions or world types
		
		if (world.getGenerator() == DimensionType.FLAT) {
			FlatGeneratorInfo info = FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
			biomeManager = new BiomeManagerSingle(CubeBiomeGenBase.getBiome(info.getBiomeID()), 0.5F);
		} else {
			biomeManager = new BiomeManager(world);
		}
	}
	
	public WorldColumnManager getWorldColumnMananger() {
		return (WorldColumnManager)biomeManager;
	}
	
	public abstract GeneratorPipeline createGeneratorPipeline(CubeWorldServer worldServer);
	
	public abstract int getSeaLevel();
}