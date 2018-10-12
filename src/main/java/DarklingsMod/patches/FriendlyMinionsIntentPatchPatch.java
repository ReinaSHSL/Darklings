package DarklingsMod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.enums.MonsterIntentEnum;
import kobting.friendlyminions.helpers.BasePlayerMinionHelper;
import kobting.friendlyminions.helpers.MonsterHelper;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.patches.MonsterIntentPatch;
import kobting.friendlyminions.patches.MonsterSetMovePatch;

import java.lang.reflect.Field;


public class FriendlyMinionsIntentPatchPatch {

    @SpirePatch(clz = MonsterIntentPatch.CreateIntentPatch.class, method = "Insert")
    public static class CreateIntentsPatch {
        public static SpireReturn<Object> Prefix(AbstractMonster __instance) {
            AbstractMonster.Intent _intent = __instance.intent;
            if ((AbstractDungeon.player instanceof AbstractPlayerWithMinions
                    && ((AbstractPlayerWithMinions)AbstractDungeon.player).hasMinions()
                    || BasePlayerMinionHelper.hasMinions(AbstractDungeon.player))
                    && (_intent == MonsterIntentEnum.ATTACK_MINION
                    || _intent == MonsterIntentEnum.ATTACK_MINION_BUFF
                    || _intent == MonsterIntentEnum.ATTACK_MINION_DEBUFF
                    || _intent == MonsterIntentEnum.ATTACK_MINION_DEFEND)
                    && MonsterHelper.getTarget(__instance) == null) {
                AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
                AbstractFriendlyMonster target = (AbstractFriendlyMonster)player.minions.getRandomMonster(true);
                MonsterHelper.setTarget(__instance, target);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(clz = MonsterSetMovePatch.class, method= "maybeChangeIntent")
    public static class SetIntentPatch {
        public static void Postfix(AbstractMonster monster, AbstractMonster.Intent intent, byte nextMove, int baseDamage, int multiplier, boolean isMultiDamage) {
              try {
                  if (AbstractDungeon.player.currentHealth == 0) {
                      Field moveInfo = AbstractMonster.class.getDeclaredField("move");
                      moveInfo.setAccessible(true);
                      EnemyMoveInfo newInfo = new EnemyMoveInfo(nextMove, intent, baseDamage, multiplier, isMultiDamage);
                      moveInfo.set(monster, newInfo);
                  }
              } catch (NoSuchFieldException | IllegalAccessException e) {
                  e.printStackTrace();
              }
        }
    }
}
