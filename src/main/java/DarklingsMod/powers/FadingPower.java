package DarklingsMod.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;

public class FadingPower
  extends AbstractPower
{
  public static final String POWER_ID = "Darklings:FadingPower";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  public int oldMaxHp;
  public int oldCurrentHp;
  
  public FadingPower(AbstractCreature owner, int turns)
  {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.amount = turns;
    updateDescription();
    loadRegion("fading");

    this.oldMaxHp = this.owner.maxHealth;
    this.oldCurrentHp = this.owner.currentHealth;

    this.owner.maxHealth = 999;
    this.owner.currentHealth = 999;
  }
  
  @Override
  public void onInitialApplication() {
    AbstractDungeon.effectsQueue.add(new HealEffect(this.owner.hb.cX - this.owner.animX, this.owner.hb.cY, 999));
  }

  @Override
  public void updateDescription()
  {
    if (this.amount == 1) {
      this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
    } else {
      this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }
  }
  
  @Override
  public void duringTurn()
  {
    if ((this.amount == 1) && (!this.owner.isDying))
    {
      AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
      AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)this.owner));
    }
    else
    {
      AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
      updateDescription();
    }
  }

  @Override
  public void onVictory() {
    if (!this.owner.halfDead || !this.owner.isDying) {
      this.owner.maxHealth = this.oldMaxHp;
      this.owner.currentHealth = this.oldCurrentHp;

      AbstractDungeon.effectsQueue.add(new HealEffect(this.owner.hb.cX - this.owner.animX, this.owner.hb.cY, this.oldCurrentHp));
    }
  }  
}
