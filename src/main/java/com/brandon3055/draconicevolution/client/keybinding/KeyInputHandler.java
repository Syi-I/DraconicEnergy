package com.brandon3055.draconicevolution.client.keybinding;

import com.brandon3055.draconicevolution.client.gui.GuiDislocator;
import com.brandon3055.draconicevolution.client.gui.modular.itemconfig.GuiConfigurableItem;
import com.brandon3055.draconicevolution.items.tools.DislocatorAdvanced;
import com.brandon3055.draconicevolution.network.DraconicNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Created by Brandon on 14/08/2014.
 */
public class KeyInputHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        onInput(player);
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        onInput(player);

//        int change = event.getDwheel();
//        if (change == 0 || !player.isShiftKeyDown()) return;
//
//        ItemStack item = player.inventory.getStackInSlot(player.inventory.currentItem);
//        if (item.getItem() == DEFeatures.dislocatorAdvanced) {
//            event.setCanceled(true);
//            DraconicEvolution.network.sendToServer(new PacketDislocator(PacketDislocator.SCROLL, change < 0 ? -1 : 1, false));
//        }
    }

    private void onInput(PlayerEntity player) {

        if (KeyBindings.toolConfig.consumeClick()) {
            DraconicNetwork.sendOpenItemConfig(false);
        }
//        else if (KeyBindings.hudConfig.isPressed()) {
////            Minecraft.getInstance().displayGuiScreen(new GuiHudConfig());
//
//        }
        else if (KeyBindings.toolModules.consumeClick()) {
            DraconicNetwork.sendOpenItemConfig(true);
        }
//        else if (KeyBindings.toolProfileChange.isPressed() && HandHelper.getMainFirst(player) != null) {
////            PacketDispatcher.dispatchToolProfileChange(false);
//        }
        /*else if (KeyBindings.toggleFlight.consumeClick()) {
            if (player.abilities.mayfly) {
                if (player.abilities.flying) {
                    player.abilities.flying = false;
                    player.onUpdateAbilities();
                } else {
                    player.abilities.flying = true;
                    if (player.isOnGround()) {
                        player.setPos(player.getX(), player.getY() + 0.05D, player.getZ());
                        player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
                    }
                    player.onUpdateAbilities();
                }
            }
        }*/ else if (KeyBindings.toggleMagnet.consumeClick()) {
            DraconicNetwork.sendToggleMagnets();
        } else if (KeyBindings.dislocatorTeleport.consumeClick()) {
            DraconicNetwork.sendDislocatorMessage(11, output -> {});
        } else if (KeyBindings.dislocatorBlink.consumeClick()) {
            DraconicNetwork.sendDislocatorMessage(12, output -> {});
        } else if (KeyBindings.dislocatorUp.consumeClick()) {
            DraconicNetwork.sendDislocatorMessage(13, output -> output.writeBoolean(false));
        } else if (KeyBindings.dislocatorDown.consumeClick()) {
            DraconicNetwork.sendDislocatorMessage(13, output -> output.writeBoolean(true));
        } else if (KeyBindings.dislocatorGui.consumeClick()) {
            ItemStack stack = DislocatorAdvanced.findDislocator(player);
            if (!stack.isEmpty()) {
                Minecraft.getInstance().setScreen(new GuiDislocator(stack.getHoverName(), player));
            }
        } else if (KeyBindings.placeItem.consumeClick()) {
            RayTraceResult result = Minecraft.getInstance().hitResult;
            if (result != null && result.getType() == RayTraceResult.Type.BLOCK) {
                DraconicNetwork.sendPlaceItem();
            }
        }
//        else if (KeyBindings.armorProfileChange.isPressed()) {
////            PacketDispatcher.dispatchToolProfileChange(true);
//        }
//        else if (KeyBindings.cycleDigAOE.isPressed()) {
////            PacketDispatcher.dispatchCycleDigAOE(player.isShiftKeyDown());
//        }
//        else if (KeyBindings.cycleAttackAOE.isPressed()) {
////            PacketDispatcher.dispatchCycleAttackAOE(player.isShiftKeyDown());
//        }
    }

    private int previouseSlot(int i, int c) {
        if (c > 0 && c < 8) return c + i;
        if (c == 0 && i < 0) return 8;
        if (c == 8 && i > 0) return 0;
        return c + i;
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void priorityKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && event.getAction() == 1) {
            GuiConfigurableItem.checkKeybinding(event.getKey(), event.getScanCode());
        }
    }
}
