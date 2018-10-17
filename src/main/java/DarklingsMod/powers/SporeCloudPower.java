package DarklingsMod.powers;

import DarklingsMod.tools.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class SporeCloudPower extends AbstractPower {
  public static final String POWER_ID = "Darklings:Spore Cloud";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Spore Cloud");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  public SporeCloudPower(AbstractCreature owner, int vulnAmt)
  {
    this.name = NAME;
    this.ID = "Spore Cloud";
    this.owner = owner;
    this.amount = vulnAmt;
    updateDescription();
    loadRegion("sporeCloud");
  }
  
  public void updateDescription()
  {
    this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
  }
  
  public void onDeath()
  {
    if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
      return;
    }
    CardCrawlGame.sound.play("SPORE_CLOUD_RELEASE");
    flashWithoutSound();
    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, null, new VulnerablePower(mo, this.amount, true), this.amount));
    }
  }
}
