package DarklingsMod.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

public class ShiftingPower
  extends AbstractPower
{
  public static final String POWER_ID = "Darklings:Shifting";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  public ShiftingPower(AbstractCreature owner, int amount)
  {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.amount = amount;
    updateDescription();
    this.isPostActionPower = true;
    loadRegion("shift");
  }
  
  public int onAttacked(DamageInfo info, int damageAmount)
  {
    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -damageAmount), -damageAmount));
    if (!this.owner.hasPower("Artifact")) {
      AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new GainStrengthPower(this.owner, damageAmount), damageAmount));
    }
    flash();
    
    return damageAmount;
  }

  public void atStartOfTurnPostDraw()
  {
    flash();
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
  }

  public void updateDescription()
  {
    this.description = DESCRIPTIONS[1];
  }
}
