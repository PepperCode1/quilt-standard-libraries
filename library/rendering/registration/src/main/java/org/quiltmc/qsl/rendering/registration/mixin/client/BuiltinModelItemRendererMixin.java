/*
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qsl.rendering.registration.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import org.quiltmc.qsl.rendering.registration.api.client.DynamicItemRenderer;
import org.quiltmc.qsl.rendering.registration.impl.client.DynamicItemRendererRegistryImpl;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void onRender(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo info) {
		DynamicItemRenderer renderer = DynamicItemRendererRegistryImpl.getRenderer(stack.getItem());

		if (renderer != null) {
			renderer.render(stack, mode, matrices, vertexConsumers, light, overlay);
			info.cancel();
		}
	}
}