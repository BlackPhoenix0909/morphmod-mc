package com.morphmod.client.gui;

import com.morphmod.client.MorphModClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

/**
 * Morph selection screen rendered in Minecraft chest GUI style.
 * Shows all unlocked morphs as spawn eggs in a chest-like grid.
 */
public class MorphScreen extends Screen {

    // Chest GUI texture (vanilla)
    private static final Identifier CHEST_GUI_TEXTURE =
        new Identifier("textures/gui/container/generic_54.png");

    // Grid: 9 columns x 6 rows = 54 slots (like a large chest)
    private static final int COLS = 9;
    private static final int ROWS = 6;
    private static final int SLOT_SIZE = 18;
    private static final int GUI_WIDTH  = 176;
    private static final int GUI_HEIGHT = 222; // 6 rows + title area

    private int guiLeft, guiTop;
    private final List<String> morphList = new ArrayList<>();
    private int hoveredSlot = -1;

    public MorphScreen() {
        super(Text.translatable("gui.morphmod.title"));
    }

    @Override
    protected void init() {
        super.init();
        guiLeft = (width  - GUI_WIDTH)  / 2;
        guiTop  = (height - GUI_HEIGHT) / 2;

        // Build sorted list: "player" first, then all unlocked morphs alphabetically
        morphList.clear();
        morphList.add("player");
        List<String> sorted = new ArrayList<>(MorphModClient.unlockedMorphs);
        sorted.sort(Comparator.naturalOrder());
        morphList.addAll(sorted);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Dim background
        renderBackground(context, mouseX, mouseY, delta);

        // Draw chest background
        context.drawTexture(CHEST_GUI_TEXTURE, guiLeft, guiTop, 0, 0, GUI_WIDTH, GUI_HEIGHT);

        // Title
        context.drawTextWithShadow(textRenderer,
            Text.translatable("gui.morphmod.title"),
            guiLeft + 8, guiTop + 6, 0x404040);

        // Determine hovered slot
        hoveredSlot = getSlotAt(mouseX, mouseY);

        // Draw morph items in slots
        for (int i = 0; i < ROWS * COLS; i++) {
            int col = i % COLS;
            int row = i / COLS;
            int slotX = guiLeft + 8 + col * SLOT_SIZE;
            int slotY = guiTop + 18 + row * SLOT_SIZE;

            if (i < morphList.size()) {
                String morphId = morphList.get(i);

                // Highlight active morph
                boolean isActive = morphId.equals(MorphModClient.activeMorph != null ? MorphModClient.activeMorph : "player");
                if (isActive) {
                    context.fill(slotX, slotY, slotX + 16, slotY + 16, 0x8000FF00);
                }

                // Hover highlight
                if (i == hoveredSlot) {
                    context.fill(slotX, slotY, slotX + 16, slotY + 16, 0x80FFFFFF);
                }

                // Draw spawn egg or player head icon
                drawMorphIcon(context, morphId, slotX, slotY);
            }
        }

        // Tooltip for hovered slot
        if (hoveredSlot >= 0 && hoveredSlot < morphList.size()) {
            String morphId = morphList.get(hoveredSlot);
            String displayName = getMorphDisplayName(morphId);
            List<Text> tooltip = new ArrayList<>();
            tooltip.add(Text.literal(displayName).styled(s -> s.withBold(true)));
            if (morphId.equals("player")) {
                tooltip.add(Text.literal("Return to human form").styled(s -> s.withColor(0xAAAAAA)));
            } else {
                // Show stats
                EntityType<?> type = getEntityType(morphId);
                if (type != null) {
                    double hp = com.morphmod.ability.MobAbilityRegistry.getMaxHealth(type);
                    double dmg = com.morphmod.ability.MobAbilityRegistry.getAttackDamage(type);
                    tooltip.add(Text.literal("❤ Health: " + (int)hp).styled(s -> s.withColor(0xFF5555)));
                    tooltip.add(Text.literal("⚔ Damage: " + dmg).styled(s -> s.withColor(0xFF9944)));
                }
            }
            context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawMorphIcon(DrawContext context, String morphId, int x, int y) {
        if (morphId.equals("player")) {
            // Draw a player icon (steve head using GUI item rendering)
            // Use a barrier block as placeholder for player icon
            context.drawItem(new net.minecraft.item.ItemStack(net.minecraft.item.Items.PLAYER_HEAD), x, y);
            return;
        }

        // Get the spawn egg for this entity type
        EntityType<?> type = getEntityType(morphId);
        if (type != null) {
            net.minecraft.item.SpawnEggItem egg = net.minecraft.item.SpawnEggItem.forEntity(type);
            if (egg != null) {
                context.drawItem(new net.minecraft.item.ItemStack(egg), x, y);
            } else {
                // Fallback: barrier
                context.drawItem(new net.minecraft.item.ItemStack(net.minecraft.item.Items.BARRIER), x, y);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int slot = getSlotAt((int)mouseX, (int)mouseY);
        if (slot >= 0 && slot < morphList.size()) {
            String morphId = morphList.get(slot);
            MorphModClient.requestMorph(morphId.equals("player") ? null : morphId);
            this.close();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private int getSlotAt(int mouseX, int mouseY) {
        for (int i = 0; i < ROWS * COLS; i++) {
            int col = i % COLS;
            int row = i / COLS;
            int slotX = guiLeft + 8 + col * SLOT_SIZE;
            int slotY = guiTop + 18 + row * SLOT_SIZE;
            if (mouseX >= slotX && mouseX < slotX + SLOT_SIZE &&
                mouseY >= slotY && mouseY < slotY + SLOT_SIZE) {
                return i;
            }
        }
        return -1;
    }

    private EntityType<?> getEntityType(String morphId) {
        try {
            return Registries.ENTITY_TYPE.getOrEmpty(new Identifier(morphId)).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private String getMorphDisplayName(String morphId) {
        if (morphId.equals("player")) return "Human (You)";
        EntityType<?> type = getEntityType(morphId);
        if (type != null) return type.getName().getString();
        return morphId;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
