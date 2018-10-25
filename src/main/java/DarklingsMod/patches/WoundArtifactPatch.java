package DarklingsMod.patches;

import DarklingsMod.powers.WoundPower;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")
public class WoundArtifactPatch {

    public static void Prefix(ApplyPowerAction self) {
        float d = (float)ReflectionHacks.getPrivate(self, AbstractGameAction.class, "duration");
	    if (d == (float)ReflectionHacks.getPrivate(self, ApplyPowerAction.class, "startingDuration")) { 

            if ((self.target.hasPower("Darklings:WoundPower")) && 
                (((AbstractPower)ReflectionHacks.getPrivate(self, ApplyPowerAction.class, "powerToApply")).type == AbstractPower.PowerType.BUFF))
            {
                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(self.target, "Blocked"));
                CardCrawlGame.sound.play("NULLIFY_SFX");
                self.target.getPower("Darklings:WoundPower").flashWithoutSound();
                self.target.getPower("Darklings:WoundPower").onSpecificTrigger();
                return;
            }

        }
    }
}
