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
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.graphics.Texture;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import java.util.ArrayList;

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.actions.TeachAction;

public class Explode extends AbstractDittoCard {
    public static final String           ID = "Explode";
    public static final int            COST = 1;
    public static final CardType       TYPE = CardType.POWER;
    public static final CardTarget   TARGET = CardTarget.SELF;
    public static final String  MONSTERPOOL = "Exploder";

    public Explode() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.costUp = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new TeachAction(
            getDarkittyn("Casey"),
            "Explode",
            "Self-destruct, dealing 30 damage to ALL monsters.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/Explode.png"),
            () -> {
                ArrayList<AbstractMonster> mo = AbstractDungeon.getCurrRoom().monsters.monsters;
                int[] tmp = new int[mo.size()];
                for (int i = 0; i < tmp.length; i++) {
                  AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(mo.get(i).hb.cX, mo.get(i).hb.cY), 0.1F));
                  tmp[i] = 30;
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(getDarkittyn("Casey"), tmp, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)getDarkittyn("Casey")));
            }
            ));

        act(new TeachAction(
            getDarkittyn("Anthony"),
            "Explode",
            "Self-destruct, dealing 30 damage to ALL monsters.",
            ImageMaster.loadImage("DarklingImgs/buddy/actions/Explode.png"),
            () -> {
                ArrayList<AbstractMonster> mon = AbstractDungeon.getCurrRoom().monsters.monsters;
                int[] tmpb = new int[mon.size()];
                for (int i = 0; i < tmpb.length; i++) {
                  AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(mon.get(i).hb.cX, mon.get(i).hb.cY), 0.1F));
                  tmpb[i] = 30;
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(getDarkittyn("Anthony"), tmpb, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)getDarkittyn("Anthony")));
            }
            ));
    }
}