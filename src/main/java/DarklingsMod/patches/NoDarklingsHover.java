package DarklingsMod.patches;

import DarklingsMod.monsters.Anthony;
import DarklingsMod.monsters.Casey;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NoDarklingsHover {

    @SpirePatch(clz= AbstractMonster.class, method="renderName")
    public static class NoDarklingsName {
        public static SpireReturn<Object> Prefix(AbstractMonster __instance, SpriteBatch sb) {
            if (__instance instanceof Anthony || __instance instanceof Casey) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
