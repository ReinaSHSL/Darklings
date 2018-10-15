package DarklingsMod.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import java.util.ArrayList;

public class TimeWarpPower extends AbstractPower
{
  public static final String POWER_ID = "Time Warp";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Time Warp");
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESC = powerStrings.DESCRIPTIONS;
  public int strGain = 0;
  
  public TimeWarpPower(AbstractCreature owner, int strGain)
  {
    this.name = NAME;
    this.ID = "Time Warp";
    this.owner = owner;
    this.amount = 0;

    updateDescription();
    loadRegion("time");
    this.type = AbstractPower.PowerType.BUFF;
  }
  
  public void playApplyPowerSfx()
  {
    CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
  }
  
  public void updateDescription()
  {
    this.description = (DESC[0] + 12 + DESC[1] + 2 + DESC[2]);
  }
  
  public void onAfterUseCard(AbstractCard card, UseCardAction action)
  {
    flashWithoutSound();
    this.amount += 1;
    if (this.amount == 12)
    {
      this.amount = 0;
      if (AbstractDungeon.overlayMenu.endTurnButton.enabled) {
          playApplyPowerSfx();
          AbstractDungeon.actionManager.cardQueue.clear();
          for (AbstractCard c : AbstractDungeon.player.limbo.group) {
            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
          }
          AbstractDungeon.player.limbo.group.clear();
          AbstractDungeon.player.releaseCard();
          AbstractDungeon.overlayMenu.endTurnButton.disable(true);
          
          CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
          AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
          AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
      }
      if (strGain > 0) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 2), 2));
      }
    }
    updateDescription();
  }
}
