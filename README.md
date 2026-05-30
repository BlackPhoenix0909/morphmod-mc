# 🧬 MorphMod

A Minecraft Fabric mod for **1.20.4** that lets you transform into any mob you've killed — taking on their appearance, stats, and a unique ability you can trigger on demand.

---

## ✨ Features

- **Transform into any mob** by killing it first — unlocks permanently
- **Look like the mob** — other players see the mob model, not your skin
- **Mob stats** — health and attack damage match the mob you're morphed into
- **Manual abilities** — press `R` to trigger your current morph's ability (nothing happens automatically)
- **Morph menu** — press `M` to open a chest-style GUI showing all your unlocked morphs
- **Passive effects** — Spider lets you climb walls, Chicken prevents fall damage

---

## 🎮 Controls

| Key | Action |
|-----|--------|
| `M` | Open morph selection menu |
| `R` | Trigger current morph's ability |

Both keys can be rebound in **Options → Controls → Morph Mod**.

---

## 🔓 How to Unlock Morphs

Kill a mob → you permanently unlock that morph. A message confirms the unlock. Open the menu with `M`, click a morph to transform, click **Human** to return to normal.

---

## ⚡ Mob Abilities (press R)

### 🌿 Passive & Neutral
| Mob | Ability |
|-----|---------|
| 🐔 Chicken | Slow Falling for 5 seconds |
| 🐺 Wolf | Speed II + Strength II for 5 seconds |
| 🦊 Fox | Super Jump Boost (4 blocks high) |
| 🐻 Polar Bear | Swipe — 12 damage to all nearby |
| 🦙 Llama | Spit projectile |
| 🐬 Dolphin | Dolphin's Grace + Water Breathing for 10 seconds |
| 🦑 Squid | Ink Cloud — Invisibility + blinds nearby enemies |
| 🐐 Goat | Ram — knockback + 6 damage to nearby |
| 🐸 Frog | Extreme leap (Jump Boost V) |
| 🦎 Axolotl | Regeneration II + Water Breathing |
| 🐝 Bee | Poison sting — poisons all enemies within 3 blocks |
| 🧚 Allay | Luck III for 15 seconds + Speed |

### ⚔️ Hostile
| Mob | Ability |
|-----|---------|
| 🧟 Zombie | Rage — Strength II + Resistance for 5 seconds |
| 🏹 Skeleton | Arrow Volley — fires 5 arrows at once |
| 💥 Creeper | Explosion around you |
| 🕷️ Spider | Speed II + Jump Boost II dash |
| ☠️ Cave Spider | Poison Aura — poisons all enemies within 4 blocks |
| 👁️ Enderman | Teleport 16 blocks in your look direction |
| 🔥 Blaze | Shoot a Fireball |
| 💀 Ghast | Shoot a large Ghast Fireball |
| 🧙‍♀️ Witch | Brew — Fire Resistance + Water Breathing + Speed |
| 🟩 Slime | Launch — bounces all nearby enemies into the air |
| 🔥 Magma Cube | Magma Burst — ignites and damages nearby enemies |
| 🐛 Silverfish | Frenzy — Speed IV + Haste III |
| 🌊 Drowned | Water Breathing + Dolphin's Grace |
| 🏜️ Husk | Desert Hunger — inflicts Hunger II on nearby enemies |
| ❄️ Stray | Slowness Arrow — shoots a slowness-tipped arrow |
| 👻 Phantom | Dive — Levitation + Invisibility |
| 🐷 Piglin | Nether Fury — Fire Resistance + Strength |
| 🏹 Pillager | Piercing Arrow — arrow that goes through 3 enemies |
| 🦕 Ravager | Ravage — massive knockback + 15 damage in 6 blocks |
| 🧙 Evoker | Summon 3 Vexes that fight for you |
| 📦 Shulker | Shoot a Shulker Bullet |
| 🔱 Guardian | Laser — 8 magic damage to all in 10 blocks |
| ⚓ Elder Guardian | Curse — Mining Fatigue III + 12 damage in 15 blocks |
| 👿 Vex | Phase — Levitation + Invisibility + Strength III |
| ⚙️ Iron Golem | Iron Fist — 20 damage + launch all in 5 blocks |
| ⛄ Snow Golem | Snowball Barrage — fires 5 snowballs at once |

### 👑 Bosses
| Mob | Ability |
|-----|---------|
| 🔊 Warden | Sonic Boom — 10 damage to everything in 15 blocks (bypasses armor) |
| 💀 Wither | Shoot a Wither Skull |
| 🐉 Ender Dragon | Dragon's Wrath — 20 damage to all in 20 blocks + Resistance V + Strength V |

---

## 🛠️ Requirements

- Minecraft **1.20.4**
- [Fabric Loader](https://fabricmc.net/) 0.15+
- [Fabric API](https://modrinth.com/mod/fabric-api) 0.97.1+1.20.4

---

## 📦 Installation

1. Install Fabric Loader for 1.20.4
2. Download Fabric API and place it in your `mods` folder
3. Place `morphmod-1.0.0.jar` in your `mods` folder
4. Launch Minecraft 1.20.4 with Fabric

---

## 🔨 Building from Source

```bash
git clone https://github.com/YOUR_USERNAME/morphmod-mc
cd morphmod-mc
gradle build --no-daemon
```

The JAR will be in `build/libs/`.

---

## 📋 Planned Features

- [ ] First-person view matches morph model
- [ ] Sound effects when triggering abilities
- [ ] Ability cooldown display in HUD
- [ ] Config file for balancing stats
- [ ] More mobs (Breeze, Bogged)

---

*Built with Fabric API · Minecraft 1.20.4 · Java 17*
