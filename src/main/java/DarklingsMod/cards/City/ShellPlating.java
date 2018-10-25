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

public class ShellPlating extends AbstractDittoCard {
    public static final String           ID = "ShellPlating";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Shelled Parasite";

    public ShellPlating() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 14;

        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            actForDarkittyn(new ApplyPowerAction(m, p, new PlatedArmorPower(m, this.magicNumber), this.magicNumber));
        } 
        act(new ApplyPowerAction(m, p, new PlatedArmorPower(m, this.magicNumber), this.magicNumber));
    }
}