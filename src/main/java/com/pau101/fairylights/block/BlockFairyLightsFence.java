package com.pau101.fairylights.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.pau101.fairylights.util.vectormath.Point3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFairyLightsFence extends BlockFairyLightsFastener {
	public BlockFairyLightsFence() {
		super(Material.wood);
		setHardness(2);
		setResistance(5);
		setStepSound(soundTypeWood);
		setBlockBounds(0.375f, 0, 0.375f, 0.625f, 1, 0.625f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return Blocks.fence.getIcon(0, 0);
	}

	@Override
	public Item getItemDropped(int data, Random random, int fortune) {
		return Item.getItemFromBlock(Blocks.fence);
	}

	@Override
	public Point3f getOffsetForData(int data, float offset) {
		return new Point3f(offset + 0.375f, offset + 0.375f, offset + 0.375f);
	}

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0.375f, 0, 0.375f, 0.625f, 1, 0.625f);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.UP || side == ForgeDirection.DOWN;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}
}
