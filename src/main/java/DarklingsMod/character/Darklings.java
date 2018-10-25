package DarklingsMod.character;

import DarklingsMod.cards.*;
import DarklingsMod.enums.AbstractCardEnum;
import DarklingsMod.enums.DarklingsEnum;
import DarklingsMod.monsters.Anthony;
import DarklingsMod.monsters.Casey;
import DarklingsMod.powers.ReincarnationPower;
import basemod.BaseMod;
import basemod.interfaces.OnStartBattleSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.relics.Shuriken;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.characters.CustomCharSelectInfo;
import kobting.friendlyminions.enums.MonsterIntentEnum;
import kobting.friendlyminions.helpers.MonsterHelper;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import java.util.ArrayList;

public class Darklings extends AbstractPlayerWithMinions {
    public static AbstractFriendlyMonster Casey = new Casey();
    public static AbstractFriendlyMonster Anthony = new Anthony();
    public CustomCharSelectInfo getInfo() {
        return (CustomCharSelectInfo) getLoadout ();
    }

    public Darklings (String name, PlayerClass setClass) {
        super(name, setClass,null, null, (String)null, null);
        initializeClass(null, "DarklingsImgs/charassets/shoulder2.png",
                "DarklingsImgs/charassets/shoulder.png", "DarklingsImgs/charassets/corpse.png",
                getLoadout(), 20.0f, -10.0f, 220.0f, 290.0f, new EnergyManager(3));
        this.loadAnimation("DarklingsImgs/charassets/skeleton.atlas", "DarklingsImgs/charassets/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        // retVal.add(Strike_Red.ID);
        // retVal.add(Strike_Red.ID);
        // retVal.add(Strike_Red.ID);
        retVal.add("Darklings:Strike");
        retVal.add("Darklings:Strike");
        // retVal.add(Strike.ID);
        // retVal.add(Strike.ID);
        // retVal.add(Defend.ID);
        // retVal.add(Defend.ID);
        // retVal.add(Defend.ID);
        // retVal.add(Chomp.ID);
        // retVal.add(Nip.ID);
        // retVal.add(Harden.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Shuriken.ID);
        return retVal;
    }

    public CharSelectInfo getLoadout() {

        CharSelectInfo info = new CustomCharSelectInfo (
                "Darklings",
                "TODO",
                20, //currentHP
                20, //maxHP
                0,  //maxOrbs
                2,  //maxMinions
                99, //gold
                5,  //cardDraw
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
        return info;
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "the Blobs";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.DARKLINGS_BLACK;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.BLACK;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.BLACK;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 3;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_FIRE", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.sound.playA("ATTACK_FAST", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_FIRE";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "the Blobs";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Darklings(this.name, DarklingsEnum.DARKLING);
    }

    @Override
    public String getSpireHeartText() {
        return "NL You blob it up...";
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.BLACK;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};

    }

    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us~ ~Darkling,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";

    }

    @Override
    public void damage(final DamageInfo info) {
        System.out.println("Damage");
        AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
        AbstractMonster owner;
        boolean attackingMonster = false;
        if (info.owner instanceof AbstractMonster) {
            owner = (AbstractMonster) info.owner;
            attackingMonster = checkAttackMonsterIntent(owner.intent);
        }
        if (attackingMonster) {
            //damageFriendlyMonster(info);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(MonsterHelper.getTarget((AbstractMonster)info.owner), info, AbstractGameAction.AttackEffect.NONE));
            return;
        }
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

    public static boolean checkAttackMonsterIntent(AbstractMonster.Intent intent) {
        if(intent == MonsterIntentEnum.ATTACK_MINION
                || intent == MonsterIntentEnum.ATTACK_MINION_BUFF
                || intent == MonsterIntentEnum.ATTACK_MINION_DEBUFF
                || intent == MonsterIntentEnum.ATTACK_MINION_DEFEND) {
            return true;
        }
        return false;
    }
}
