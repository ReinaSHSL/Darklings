package DarklingsMod.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import DarklingsMod.cards.AbstractDittoCard;

public class Bellow extends AbstractDittoCard {
    public static final String           ID = "Bellow";
    public static final int            COST = 3;
    public static final CardType       TYPE = CardType.SKILL;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "JawWorm";

    public Bellow() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 4;
        this.magicNumberUp = 5;

        this.baseBlock = 9;

        loadAnimation("images/monsters/theBottom/jawWorm/skeleton.atlas", "images/monsters/theBottom/jawWorm/skeleton.json", 1.0F);
            
        AnimationState.TrackEntry e = this.state.setAnimation(0, "tailslam", true);
        this.skeleScale = 0.75F;
        this.skeleOffsetY = 20.0F;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        actForDarkittyns(new GainBlockAction(p, p, this.block));
        if (this.upgraded) {
            actForDarkittyns(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }
}