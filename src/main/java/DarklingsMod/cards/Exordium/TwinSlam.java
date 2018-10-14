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
import com.megacrit.cardcrawl.cards.DamageInfo;

import DarklingsMod.cards.AbstractDittoCard;
import DarklingsMod.monsters.*;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;

public abstract class TwinSlam extends AbstractDittoCard {
    public static final String           ID = "TwinSlam";
    public static final int            COST = 2;
    public static final CardType       TYPE = CardType.ATTACK;
    public static final CardTarget   TARGET = CardTarget.ENEMY;
    public static final String  MONSTERPOOL = "TheGuardian";

    public TwinSlam() {
        super(ID, COST, TYPE, TARGET, MONSTERPOOL);
        this.baseDamage = 8;

        this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i=0;i<2;i++) {
            act(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));        
        }
        if (this.upgraded) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
            if (p.hasPower(ThornsPower.POWER_ID)) {
                act(new ApplyPowerAction(p, p, new StrengthPower(p, p.getPower(ThornsPower.POWER_ID).amount), p.getPower("Thorns").amount));
            }
            if (player.getMinions().getMonster(Casey.ID).hasPower(ThornsPower.POWER_ID)) {
                actForDarkittyn(new ApplyPowerAction(p, p, new StrengthPower(p, p.getPower("Thorns").amount), p.getPower("Thorns").amount), "Casey");
            }
            if (player.getMinions().getMonster(Anthony.ID).hasPower(ThornsPower.POWER_ID)) {
                actForDarkittyn(new ApplyPowerAction(p, p, new StrengthPower(p, p.getPower("Thorns").amount), p.getPower("Thorns").amount), "Anthony");
            }
        }
    }
}