package DarklingsMod.patches;

import DarklingsMod.character.Darklings;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.helpers.MonsterHelper;
import kobting.friendlyminions.patches.MonsterIntentPatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(clz = AbstractRoom.class, method = "update")
public class AntiSoftlockPatch {

    @SpireInsertPatch(rloc=73)
    public static void Insert(AbstractRoom __instance) {
        if (AbstractDungeon.player instanceof Darklings
                && !__instance.monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.player.updateInput();
        }
    }
}
