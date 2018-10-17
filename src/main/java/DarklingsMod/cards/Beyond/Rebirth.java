package DarklingsMod.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import DarklingsMod.cards.AbstractDittoCard;

public abstract class Rebirth extends AbstractDittoCard {
    public static final String           ID = "Rebirth";
    public static final int            COST = 3;
    public static final CardType       TYPE = CardType.SKILL;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "AwakenedOne";

    public Rebirth() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.costUp = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getDarkittyn("Casey").halfDead) {
            act(new HealAction(getDarkittyn("Casey"), getDarkittyn("Casey"), getDarkittyn("Casey").maxHealth));
            getDarkittyn("Casey").halfDead = false;
            act(new RemoveSpecificPowerAction(getDarkittyn("Casey"), getDarkittyn("Casey"), "Darklings:ReincarnationPower"));
        }

        if (getDarkittyn("Anthony").halfDead) {
            act(new HealAction(getDarkittyn("Anthony"), getDarkittyn("Anthony"), getDarkittyn("Anthony").maxHealth));
            getDarkittyn("Anthony").halfDead = false;
            act(new RemoveSpecificPowerAction(getDarkittyn("Anthony"), getDarkittyn("Anthony"), "Darklings:ReincarnationPower"));
        }
    }
}

