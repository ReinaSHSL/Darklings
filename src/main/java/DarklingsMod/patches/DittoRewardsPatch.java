package DarklingsMod.patches;

import DarklingsMod.character.Darklings;
import DarklingsMod.cards.AbstractDittoCard;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "getRewardCards")
public class DittoRewardsPatch {
    public static SpireReturn<ArrayList<AbstractCard>> Prefix() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player instanceof Darklings) {
            ArrayList<AbstractCard> retVal = new ArrayList();
            
            // Adjust reward size
            int numCards = 3;
            if (player.hasRelic("Question Card")) {
              numCards++;
            }
            if (player.hasRelic("Busted Crown")) {
              numCards -= 2;
            }
            if (player.hasRelic("Honey Jar")) {
              numCards++;
            }
            if (ModHelper.isModEnabled("Binary")) {
              numCards--;
            }

            // Grab all the special cards, and find the relevant monster cards
            ArrayList<AbstractCard> tmpPool = new ArrayList();
            CardGroup dittoGroup = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            AbstractDungeon.player.getCardPool(tmpPool);

            for (AbstractCard c : tmpPool) {
                if (c.rarity == AbstractCard.CardRarity.SPECIAL) { 
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (((AbstractDittoCard)c).monsterID == mo.id) { 
                            dittoGroup.addToTop(c);
                        } 
                    }
                } 
            } 

            // Choose the relevant number of monster cards. Should populate with numCards - 1 (-1 again if Prismatic)
            int dittoSize = numCards-1-((player.hasRelic("PrismaticShard")) ? 0 : 1);
            // AbstractCard c;

            if (dittoSize >= dittoGroup.size()) {
                retVal.addAll(dittoGroup.group);
            } else {
                for (int i = 0; i < dittoSize; i++) {
                    AbstractCard c = dittoGroup.getRandomCard(true);
                    dittoGroup.removeCard(c);
                    retVal.add(c);
                }
            }

            // Choose other cards
            AbstractCard.CardRarity rarity;
            for (int i = 0; i < numCards-dittoGroup.size(); i++)
            {
                rarity = AbstractDungeon.rollRarity();
                AbstractCard card;
                if (player.hasRelic("PrismaticShard")) {
                  card = CardLibrary.getAnyColorCard(rarity);
                } else {
                  card = AbstractDungeon.getCard(rarity);
                }

                // Never reward with dupes. Original game has some fallback code that will never execute, ignored that.
                while (retVal.contains(card))
                {
                    if (player.hasRelic("PrismaticShard")) {
                        card = CardLibrary.getAnyColorCard(rarity);
                    } else {
                        card = AbstractDungeon.getCard(rarity);
                    }
                }
                if (card != null) {
                  retVal.add(card);
                }
            }

            // Making a copy of the cards so we aren't stealing card pool cards
            ArrayList<AbstractCard> retVal2 = new ArrayList();
            for (AbstractCard c : retVal) {
              retVal2.add(c.makeCopy());
            }

            // Upgrade cards that need the upgradez
            float cardUpgradedChance = (float)ReflectionHacks.getPrivate(CardCrawlGame.dungeon, AbstractDungeon.class, "cardUpgradedChance");
            for (AbstractCard c : retVal2) {
              if ((c.rarity != AbstractCard.CardRarity.RARE) && (AbstractDungeon.cardRng.randomBoolean(cardUpgradedChance)) && (c.canUpgrade())) {
                c.upgrade();
              } else if ((c.type == AbstractCard.CardType.ATTACK) && (player.hasRelic("Molten Egg 2"))) {
                c.upgrade();
              } else if ((c.type == AbstractCard.CardType.SKILL) && (player.hasRelic("Toxic Egg 2"))) {
                c.upgrade();
              } else if ((c.type == AbstractCard.CardType.POWER) && (player.hasRelic("Frozen Egg 2"))) {
                c.upgrade();
              }
            }
            return SpireReturn.Return(retVal2);
        }
        return SpireReturn.Continue();
    }
}
