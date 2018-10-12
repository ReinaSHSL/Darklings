package DarklingsMod.patches;

import DarklingsMod.character.Darklings;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.helpers.MonsterHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(clz = DamageAction.class, method = "update")
public class AntiSoftlockPatch {

    public static SpireReturn<Object> Prefix(DamageAction __instance) {
        System.out.println("go heck urself");
        if (AbstractDungeon.player instanceof Darklings && AbstractDungeon.player.halfDead) {
            System.out.println("go fuck yourself");
            DamageInfo i = (DamageInfo) ReflectionHacks.getPrivate(__instance, DamageAction.class, "info");
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
            AbstractMonster owner;
            boolean attackingMonster = false;
            if (i.owner instanceof AbstractMonster) {
                owner = (AbstractMonster) i.owner;
                attackingMonster = Darklings.checkAttackMonsterIntent(owner.intent);
            }
            if (attackingMonster) {
                //damageFriendlyMonster(info);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(MonsterHelper.getTarget((AbstractMonster)i.owner), i, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.monsterQueue.remove(0);
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }
}
