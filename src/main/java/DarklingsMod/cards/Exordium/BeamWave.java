package DarklingsMod.cards;

import DarklingsMod.powers.DazePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.animations.*;
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
import com.megacrit.cardcrawl.cards.DamageInfo;

import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import DarklingsMod.cards.AbstractDittoCard;

public class BeamWave extends AbstractDittoCard {
    public static final String           ID = "BeamWave";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "Sentry";

    public BeamWave() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseMagicNumber = 2;
        this.magicNumberUp = 1;

        loadAnimation("images/monsters/theBottom/sentry/skeleton.atlas", "images/monsters/theBottom/sentry/skeleton.json", 1.0F);
        
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        this.skeleScale = 0.6F;
        this.renderTint = Color.TAN;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new SFXAction("THUNDERCLAP"));
        act(new VFXAction(p, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
        act(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.2F));
        act(new ApplyPowerAction(m, p, new DazePower(m, this.magicNumber), this.magicNumber));
    }
}