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
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.powers.SlimePower;

public class Preparing extends AbstractDittoCard {
    public static final String           ID = "Preparing";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.SKILL;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "SlimeBoss";

    public Preparing() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 3;
        this.magicNumberUp = 2;

        loadAnimation("images/monsters/theBottom/boss/slime/skeleton.atlas", "images/monsters/theBottom/boss/slime/skeleton.json", 1.0F); 
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTimeScale(8.0F);
        this.skeleScale = 0.5F;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.LONG, ScreenShake.ShakeIntensity.LOW));
        actForDarklings(new ApplyPowerAction(p, p, new DoubleDamagePower(p, this.magicNumber, false), this.magicNumber));
    }

    @Override
    public void upgrade() {
    	if (!this.upgraded) {
    		this.exhaust = false;
    	}
    	super.upgrade();
    }
}