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
import DarklingsMod.powers.IntangibleDarklingPower;

public class Intrigible extends AbstractDittoCard {
    public static final String           ID = "Intrigible";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Nemesis";

    public Intrigible() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.costUp = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(p, p, new IntangibleDarklingPower(p, 0), 0));
        act(new ApplyPowerAction(getDarkittyn("Casey"), p, new IntangibleDarklingPower(getDarkittyn("Casey"), -1), -1));
        act(new ApplyPowerAction(getDarkittyn("Anthony"), p, new IntangibleDarklingPower(getDarkittyn("Anthony"), -2), -2));
    }
}