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
import DarklingsMod.powers.HexPower;

public class Hex extends AbstractDittoCard {
    public static final String           ID = "Hex";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "Chosen";

    public Hex() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.costUp = 0;
        loadAnimation("images/monsters/theCity/chosen/skeleton.atlas", "images/monsters/theCity/chosen/skeleton.json", 1.0F);
        
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.skeleScale = 0.66F;
        this.renderTint = Color.SLATE;
        e.setTimeScale(3.0F);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1, true));
    }
}