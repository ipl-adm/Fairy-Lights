package com.pau101.fairylights.client.model;

import net.minecraft.util.MathHelper;

import com.pau101.fairylights.util.MathUtils;

public class ModelLightGhost extends ModelLight {
	private static final float FIN_ANGLE = -MathUtils.PI / 3;

	private static final float[] MAGIC_ANGLES = MathUtils.toEuler(-1, 0, 1, FIN_ANGLE);

	public ModelLightGhost() {
		AdvancedModelRenderer bodyTop = new AdvancedModelRenderer(this, 52, 48);
		bodyTop.setRotationPoint(0, 2, 0);
		bodyTop.addBox(-1.5F, 0, -1.5F, 3, 1, 3, 0);
		bodyTop.setRotationAngles(MathUtils.PI, 0, 0);
		colorableParts.addChild(bodyTop);
		AdvancedModelRenderer littleFace = new AdvancedModelRenderer(this, 40, 17);
		littleFace.setRotationPoint(0, -1, -2.25F);
		littleFace.addBox(-1.5F, -1.5F, 0, 3, 3, 0, 0);
		littleFace.setRotationAngles(MathUtils.PI, 0, 0);
		amutachromicParts.addChild(littleFace);
		AdvancedModelRenderer body = new AdvancedModelRenderer(this, 46, 40);
		body.setRotationPoint(0, 1, 0);
		body.addBox(-2, 0, -2, 4, 4, 4, 0);
		body.setRotationAngles(MathUtils.PI, 0, 0);
		colorableParts.addChild(body);
		final int finCount = 8;
		for (int i = 0; i < finCount; i++) {
			AdvancedModelRenderer fin = new AdvancedModelRenderer(this, 40, 21);
			float theta = i * MathUtils.TAU / finCount;
			fin.setRotationPoint(MathHelper.cos(-theta + MathUtils.PI / 4) * 1.1F, -2.75F, MathHelper.sin(-theta + MathUtils.PI / 4) * 1.1F);
			fin.addBox(0, 0, 0, 2, 1, 2, -0.1F);
			fin.setRotationOrder(RotationOrder.YZX);
			fin.setRotationAngles(MAGIC_ANGLES[0], MAGIC_ANGLES[1] + theta, MAGIC_ANGLES[2]);
			colorableParts.addChild(fin);
		}
	}

	@Override
	public boolean hasRandomRotatation() {
		return true;
	}
}
