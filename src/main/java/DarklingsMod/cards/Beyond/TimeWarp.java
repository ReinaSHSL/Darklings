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

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.powers.TimeWarpPower;

public class TimeWarp extends AbstractDittoCard {
    public static final String           ID = "TimeWarp";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "TimeEater";

    public TimeWarp() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 1;
        this.magicNumberUp = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        actForDarklings(new ApplyPowerAction(p, p, new TimeWarpPower(p, this.magicNumber), this.magicNumber));
    }
}