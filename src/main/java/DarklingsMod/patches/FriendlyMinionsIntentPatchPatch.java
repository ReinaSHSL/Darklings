package DarklingsMod.patches;


import DarklingsMod.tools.TextureLoader;
import basemod.ReflectionHacks;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


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
                try {
                    final Field isMultiDmg = AbstractMonster.class.getDeclaredField("isMultiDmg");
                    final Field intentDmg = AbstractMonster.class.getDeclaredField("intentDmg");
                    final Field intentMultiAmt = AbstractMonster.class.getDeclaredField("intentMultiAmt");
                    isMultiDmg.setAccessible(true);
                    intentDmg.setAccessible(true);
                    intentMultiAmt.setAccessible(true);
                    int tmp;
                    if (isMultiDmg.getBoolean(__instance)) {
                        tmp = intentDmg.getInt(__instance) * intentMultiAmt.getInt(__instance);
                    }
                    else {
                        tmp = intentDmg.getInt(__instance);
                    }
                    switch (target.id) {
                        case "Darklings:Anthony":
                            if (tmp < 5) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/anthony/attack_intent_1.png"));
                            } else if (tmp < 10) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/anthony/attack_intent_2.png"));
                            } else if (tmp < 15) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/anthony/attack_intent_3.png"));
                            } else if (tmp < 20) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/anthony/attack_intent_4.png"));
                            } else if (tmp < 25) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/anthony/attack_intent_5.png"));
                            } else if (tmp < 30){
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/anthony/attack_intent_6.png"));
                            } else {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/anthony/attack_intent_7.png"));
                            }
                        case "Darklings:Casey":
                            if (tmp < 5) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/casey/attack_intent_1.png"));
                            } else if (tmp < 10) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/casey/attack_intent_2.png"));
                            } else if (tmp < 15) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/casey/attack_intent_3.png"));
                            } else if (tmp < 20) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/casey/attack_intent_4.png"));
                            } else if (tmp < 25) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/casey/attack_intent_5.png"));
                            } else if (tmp < 30){
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/casey/attack_intent_6.png"));
                            } else {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/casey/attack_intent_7.png"));
                            }
                        default:
                            if (tmp < 5) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/kio/attack_intent_1.png"));
                            } else if (tmp < 10) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/kio/attack_intent_2.png"));
                            } else if (tmp < 15) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/kio/attack_intent_3.png"));
                            } else if (tmp < 20) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/kio/attack_intent_4.png"));
                            } else if (tmp < 25) {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/kio/attack_intent_5.png"));
                            } else if (tmp < 30){
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/kio/attack_intent_6.png"));
                            } else {
                                ReflectionHacks.setPrivate(__instance, AbstractMonster.class, "intentImg", TextureLoader.getTexture("DarklingsImgs/intents/kio/attack_intent_7.png"));
                            }
                    }
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
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
