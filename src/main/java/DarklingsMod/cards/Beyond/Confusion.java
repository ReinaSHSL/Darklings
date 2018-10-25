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
import DarklingsMod.powers.AnthonysConfusionPower;

public class Confusion extends AbstractDittoCard {
    public static final String           ID = "PerplexingGlare";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Snecko";

    public Confusion() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(getDarkittyn("Anthony"), p, new AnthonysConfusionPower(this.upgraded), 1));
    }
}