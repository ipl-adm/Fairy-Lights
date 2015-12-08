package com.pau101.fairylights.client.model;

import java.awt.Color;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.pau101.fairylights.FairyLights;
import com.pau101.fairylights.util.MathUtils;
import com.pau101.fairylights.util.vectormath.Vector3f;

public abstract class ModelLight extends ModelBase {
	protected AdvancedModelRenderer colorableParts;

	// amutachromic [A - without] + [MUT - change] + [CHROM - color]
	protected AdvancedModelRenderer amutachromicParts;

	public ModelLight() {
		textureWidth = 128;
		textureHeight = 64;

		colorableParts = new AdvancedModelRenderer(this);
		colorableParts.setRotationOrder(RotationOrder.YXZ);
		amutachromicParts = new AdvancedModelRenderer(this);
		amutachromicParts.setRotationOrder(RotationOrder.YXZ);
	}

	public boolean shouldParallelCord() {
		return true;
	}

	public boolean hasRandomRotatation() {
		return false;
	}

	protected void resetModels() {
		if (colorableParts.cubeList != null) {
			colorableParts.cubeList.clear();
		}
		if (amutachromicParts.cubeList != null) {
			amutachromicParts.cubeList.clear();
		}
		if (colorableParts.childModels != null) {
			colorableParts.childModels.clear();
		}
		if (amutachromicParts.childModels != null) {
			amutachromicParts.childModels.clear();
		}
		colorableParts.compiled = false;
		amutachromicParts.compiled = false;
	}

	protected void devInitModels() {}

	public void setRotationAngles(float x, float y, float z) {
		colorableParts.rotateAngleX = x;
		colorableParts.rotateAngleY = y;
		colorableParts.rotateAngleZ = z;
		amutachromicParts.rotateAngleX = x;
		amutachromicParts.rotateAngleY = y;
		amutachromicParts.rotateAngleZ = z;
	}

	public void setOffsets(float x, float y, float z) {
		colorableParts.offsetX = x;
		colorableParts.offsetY = y;
		colorableParts.offsetZ = z;
		amutachromicParts.offsetX = x;
		amutachromicParts.offsetY = y;
		amutachromicParts.offsetZ = z;
	}

	public void setAfts(float x, float y, float z) {
		colorableParts.aftMoveX = x;
		colorableParts.aftMoveY = y;
		colorableParts.aftMoveZ = z;
		amutachromicParts.aftMoveX = x;
		amutachromicParts.aftMoveY = y;
		amutachromicParts.aftMoveZ = z;
	}

	public void setScale(float scale) {
		setScales(scale, scale, scale);
	}

	public void setScales(float x, float y, float z) {
		colorableParts.scaleX = x;
		colorableParts.scaleY = y;
		colorableParts.scaleZ = z;
		amutachromicParts.scaleX = x;
		amutachromicParts.scaleY = y;
		amutachromicParts.scaleZ = z;
	}

	public void render(World world, float scale, Vector3f color, int moonlight, int sunlight, float brightness, int index, float partialRenderTicks) {
		if (hasRandomRotatation()) {
			float randomOffset = MathUtils.modf(MathUtils.hash(index) * MathUtils.DEG_TO_RAD, MathUtils.TAU) + MathUtils.PI / 4;
			colorableParts.secondaryRotateAngleY = randomOffset;
			amutachromicParts.secondaryRotateAngleY = randomOffset;
		}
		float[] hsb = new float[3];
		Color.RGBtoHSB((int) (color.x * 255 + 0.5F), (int) (color.y * 255 + 0.5F), (int) (color.z * 255 + 0.5F), hsb);
		hsb[2] = brightness * 0.75F + 0.25F;
		int colorRGB = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
		float cr = (colorRGB >> 16 & 0xFF) / 255F, cg = (colorRGB >> 8 & 0xFF) / 255F, cb = (colorRGB & 0xFF) / 255F;
		GL11.glColor3f(cr, cg, cb);
		float b = Math.max(Math.max(brightness, world.getSunBrightness(1) * 0.95F + 0.05F) * 240, sunlight);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, b, moonlight);
		GL11.glEnable(GL11.GL_LIGHTING);
		colorableParts.render(scale);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, sunlight, moonlight);
		GL11.glColor3f(b / 256F, b / 256F, b / 256F);
		amutachromicParts.render(scale);
		GL11.glDisable(GL11.GL_LIGHTING);

		// Assume that the shaderpack that is installed adds a glow to light sources
		// so this 'glow' should be disabled
		if (!FairyLights.isShadersModInstalled) {
			int maxBrightness = 1;
			float expand = 1.3f;
			Color.RGBtoHSB((int) (color.x * 255 + 0.5F), (int) (color.y * 255 + 0.5F), (int) (color.z * 255 + 0.5F), hsb);
			if (hsb[1] > 0) {
				hsb[1] = brightness;
				hsb[2] = 1;
			}
			colorRGB = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
			cr = (colorRGB >> 16 & 0xFF) / 255F;
			cg = (colorRGB >> 8 & 0xFF) / 255F;
			cb = (colorRGB & 0xff) / 255F;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glAlphaFunc(GL11.GL_ALWAYS, 0);
			GL11.glColor4f(cr, cg, cb, brightness * 0.15F + 0.1F);
			colorableParts.isGlowing = true;
			colorableParts.render(scale);
			colorableParts.isGlowing = false;
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}
}
