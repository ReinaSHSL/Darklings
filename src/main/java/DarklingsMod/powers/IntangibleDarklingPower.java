package DarklingsMod.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class IntangibleDarklingPower
  extends AbstractPower
{
  public static final String POWER_ID = "Darklings:Intangible";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  public IntangibleDarklingPower(AbstractCreature owner, int turn)
  {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.amount = turn;
    updateDescription();
    loadRegion("intangible");
    this.priority = 99;
  }
  
  public void playApplyPowerSfx()
  {
    CardCrawlGame.sound.play("POWER_INTANGIBLE", 0.05F);
  }
  
  public float atDamageReceive(float damage, DamageInfo.DamageType type)
  {
    if (this.amount == 0) {
      if (damage > 1.0F) {
        damage = 1.0F;
      }
    }
    return damage;
  }
  
  public void updateDescription()
  {
    if (this.amount == 0) {
      this.description = DESCRIPTIONS[3];
    } else {
      if (this.amount == -1) {
        this.description = DESCRIPTIONS[0] + Math.abs(this.amount) + DESCRIPTIONS[2];
      } else {
        this.description = DESCRIPTIONS[0] + Math.abs(this.amount) + DESCRIPTIONS[1];
      }
    }
  }
  
  public void atEndOfRound()
  {
    flash();
    if (this.amount == 0) {
      this.amount = -2;
      AbstractDungeon.onModifyPower();
    } else {
      this.amount += 1;
      if (this.amount == 0) {
        playApplyPowerSfx();
      }
    }
    updateDescription();
  }
}
