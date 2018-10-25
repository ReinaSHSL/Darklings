package DarklingsMod.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;

import java.lang.reflect.*;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

import basemod.abstracts.CustomCard;

import DarklingsMod.enums.AbstractCardEnum;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

public abstract class AbstractDittoCard extends CustomCard {
    public String monsterID;
    public CardStrings cardStrings;

    public int damageUp;
    public int blockUp;
    public int magicNumberUp;
    public int costUp;

    public AbstractDittoCard(String id, int cost, CardType type, CardTarget target, AbstractCard.CardRarity rarity, String monsterPool) {
        // super("Darklings:"+id, "", "DarklingsImgs/cards/" + id, cost, "", type, AbstractCardEnum.DARKLINGS_BLACK, rarity, target);
        super("Darklings:"+id, "", null, cost, "", type, AbstractCardEnum.DARKLINGS_BLACK, rarity, target);

        this.cardStrings = CardCrawlGame.languagePack.getCardStrings("Darklings:"+id);
        this.originalName = cardStrings.NAME;
        this.name = cardStrings.NAME;
        this.rawDescription = cardStrings.DESCRIPTION;

        this.monsterID = monsterPool;

        initializeTitle();
        initializeDescription();
    }

    public AbstractDittoCard(String id, int cost, CardType type, CardTarget target, String monsterPool) {
        this(id, cost, type, target, AbstractCard.CardRarity.SPECIAL, monsterPool);
    }

    public AbstractDittoCard(String id, int cost, CardType type, CardTarget target, AbstractCard.CardRarity rarity) {
        this(id, cost, type, target, rarity, "Darklings");
    }



    public void act(AbstractGameAction act) {
        AbstractDungeon.actionManager.addToBottom(act);
    }

    public void actForDarkittyns(AbstractGameAction act) {
        act.source = AbstractDungeon.player;

        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            AbstractGameAction a = (AbstractGameAction)clone(act);
            a.target = m;
            AbstractDungeon.actionManager.addToBottom(a);
        }
    }

    public void actForDarkittyn(AbstractGameAction act, String Darkittyn) {
        act.source = AbstractDungeon.player;

        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            if (Darkittyn == m.name) {
                AbstractGameAction a = (AbstractGameAction)clone(act);
                a.target = m;
                AbstractDungeon.actionManager.addToBottom(a);
            }
        }
    }

    public AbstractFriendlyMonster getDarkittyn(String Darkittyn) {
        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            if (Darkittyn == m.name) {
                return (AbstractFriendlyMonster)m;
            }
        }
        return null;
    }

    public void actForDarkittyn(AbstractGameAction act) {
        String[] d = {"Casey", "Anthony"};
        String target = d[new Random().nextInt(d.length)];

        this.actForDarkittyn(act, target);
    }

    public void actForDarklings(AbstractGameAction act) {
        act.source = AbstractDungeon.player;
        act.target = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(act);

        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            AbstractGameAction a = (AbstractGameAction)clone(act);
            a.target = m;
            AbstractDungeon.actionManager.addToBottom(a);
        }
    }

    private static Object clone(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(clone, field.get(obj));
            }
            return clone;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            
            if (this.cardStrings.UPGRADE_DESCRIPTION != "") {
                this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;   
                initializeDescription();
            }

            if (damageUp != 0)      { upgradeDamage(damageUp); }
            if (blockUp  != 0)      { upgradeBlock(blockUp); }
            if (magicNumberUp != 0) { upgradeMagicNumber(magicNumberUp); }
            if (costUp != 0)        { upgradeBaseCost(costUp); }
        }
    }

    // @Override
    // public AbstractCard makeCopy() {
    //     AbstractCard c = null;
    //     try {
    //         c = (AbstractDittoCard)this.getClass().newInstance(); 
    //     } catch (Throwable e) {
    //         // ChronoMod.log(e.toString());
    //     }
    //     return c;
    // }
}