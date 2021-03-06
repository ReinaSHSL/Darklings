package DarklingsMod.monsters;

import DarklingsMod.powers.ReincarnationPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import java.util.ArrayList;

public class Casey extends AbstractFriendlyMonster {
    public static String NAME = "Casey";
    public static final String ID = "Darklings:Casey";
    public boolean green;
    private float offsetY;
    private boolean hasAttacked = false;
    private AbstractMonster target;

    public Casey() {
        super(NAME, ID, 20,
                -2.0F, 10.0F, 220.0F, 240.0F,
                "DarklingsImgs/buddy/DarklingBuddy.png", -935, -75);

    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }

    @Override
    public void damage(final DamageInfo info) {
        AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
        int damageAmount = info.output;
        boolean hadBlock = true;
        if (this.currentBlock == 0) {
            hadBlock = false;
        }
        if (damageAmount < 0) {
            damageAmount = 0;
        }
        if (damageAmount > 1 && this.hasPower("IntangiblePlayer")) {
            damageAmount = 1;
        }
        damageAmount = this.decrementBlock(info, damageAmount);
        if (info.owner == this) {
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }
        if (info.owner != null) {
            for (final AbstractPower p : info.owner.powers) {
                p.onAttack(info, damageAmount, this);
            }
            for (final AbstractPower p : this.powers) {
                damageAmount = p.onAttacked(info, damageAmount);
            }
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                damageAmount = r.onAttacked(info, damageAmount);
            }
        }
        if (damageAmount > 0) {
            for (final AbstractPower p : this.powers) {
                damageAmount = p.onLoseHp(damageAmount);
            }
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onLoseHp(damageAmount);
            }
            if (info.owner != this) {
                this.useStaggerAnimation();
            }
            if (info.type == DamageInfo.DamageType.HP_LOSS) {
                GameActionManager.hpLossThisCombat += damageAmount;
            }
            GameActionManager.damageReceivedThisTurn += damageAmount;
            GameActionManager.damageReceivedThisCombat += damageAmount;
            this.currentHealth -= damageAmount;
            AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
            if (this.currentHealth < 0) {
                this.currentHealth = 0;
            }
            else if (this.currentHealth < this.maxHealth / 4) {
                AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(new Color(1.0f, 0.1f, 0.05f, 0.0f)));
            }
            this.healthBarUpdatedEvent();
            if (this.currentHealth <= this.maxHealth / 2.0f && !this.isBloodied) {
                this.isBloodied = true;
            }
            if (this.currentHealth < 1) {
                this.halfDead = true;
                boolean allDead = true;
                for (AbstractMonster m : player.getMinions().monsters) {
                    if (!m.halfDead || !(AbstractDungeon.player.currentHealth == 0)) {
                        allDead = false;
                    }
                }
                if (allDead) {
                    this.isDead = true;
                    AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
                    this.currentHealth = 0;
                    if (this.currentBlock > 0) {
                        this.loseBlock();
                        AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractPlayer.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractPlayer.BLOCK_ICON_Y));
                    }
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction
                            (this, this, new ReincarnationPower(this, 3)));
                }
            }
        }
        else if (this.currentBlock > 0) {
            AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractPlayer.BLOCKED_STRING));
        }
        else if (!hadBlock) {
            AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
        }
    }
}
