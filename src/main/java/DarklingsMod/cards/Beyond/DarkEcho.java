package DarklingsMod.cards;

import com.badlogic.gdx.graphics.Color;
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
import com.megacrit.cardcrawl.cards.DamageInfo;

import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.powers.VoidPower;

public class DarkEcho extends AbstractDittoCard {
    public static final String           ID = "DarkEcho";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "AwakenedOne";

    public DarkEcho() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 40;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        act(new ApplyPowerAction(m, p, new VoidPower(m, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        act(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        act(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.3F, 0.2F, 0.4F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 1.0F));
    }
}