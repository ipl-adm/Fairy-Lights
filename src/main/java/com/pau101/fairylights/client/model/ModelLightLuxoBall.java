package com.pau101.fairylights.client.model;

public class ModelLightLuxoBall extends ModelLight {
	public ModelLightLuxoBall() {
		colorableParts.setTextureOffset(12, 6);
		colorableParts.addBox(-3, -4, -3, 6, 6, 6);
	}

	@Override
	public boolean hasRandomRotatation() {
		return true;
	}
}
