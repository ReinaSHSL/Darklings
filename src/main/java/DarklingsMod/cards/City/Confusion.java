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
import DarklingsMod.powers.AnthonysConfusionPower;

public class Confusion extends AbstractDittoCard {
    public static final String           ID = "PerplexingGlare";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Snecko";

    public Confusion() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        loadAnimation("images/monsters/theCity/reptile/skeleton.atlas", "images/monsters/theCity/reptile/skeleton.json", 1.0F);
        
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Attack", true);
        this.skeleScale = 0.66F;
        this.renderTint = Color.GOLD;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(getDarkittyn("Anthony"), p, new AnthonysConfusionPower(this.upgraded), 1));
    }
}