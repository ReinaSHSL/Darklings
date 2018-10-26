package DarklingsMod.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import DarklingsMod.cards.AbstractDittoCard;

public class DefensiveMode extends AbstractDittoCard {
    public static final String           ID = "DefensiveMode";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "TheGuardian";

    public DefensiveMode() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 3;
        this.magicNumberUp = 1;


    loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0F);
    
    this.state.setAnimation(0, "defensive", true);
        this.skeleScale = 0.33F;
        this.skeleOffsetY = -32.0F;
        this.renderTint = Color.GOLDENROD;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        actForDarklings(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
    }
}