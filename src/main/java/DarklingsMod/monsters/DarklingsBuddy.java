package DarklingsMod.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.AbstractPower;
import kobting.friendlyminions.actions.ChooseAction;
import kobting.friendlyminions.actions.ChooseActionInfo;
import kobting.friendlyminions.cards.MonsterCard;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import org.apache.logging.log4j.LogManager;


import java.util.ArrayList;

public class DarklingsBuddy extends AbstractFriendlyMonster {
    public static String NAME = "";
    public static String ID = "DarklingsBuddy";
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean green;
    private float offsetY;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private static final float BLOCK_OFFSET_DIST;
    private static final float HEALTH_BAR_HEIGHT;
    private static final float HEALTH_BAR_OFFSET_Y;
    private static final float HEALTH_TEXT_OFFSET_Y;
    private static final float POWER_ICON_PADDING_X;
    private static final float HEALTH_BG_OFFSET_X;
    private static final float HB_Y_OFFSET_DIST;
    private float blockScale = 1.0f;
    private float blockOffset = 0.0f;
    private float healthBarWidth;
    private float healthHideTimer = 0.0f;
    private Color hbBgColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    private Color hbShadowColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    private Color blockColor = new Color(0.6f, 0.93f, 0.98f, 0.0f);
    private Color blockOutlineColor = new Color(0.6f, 0.93f, 0.98f, 0.0f);
    private Color blockTextColor = new Color(0.9f, 0.9f, 0.9f, 0.0f);
    private Color redHbBarColor = new Color(0.8f, 0.05f, 0.05f, 0.0f);
    private Color greenHbBarColor = Color.valueOf("78c13c00");
    private Color blueHbBarColor = Color.valueOf("31568c00");
    private Color orangeHbBarColor = new Color(1.0f, 0.5f, 0.0f, 0.0f);
    private float targetHealthBarWidth;
    private Color hbTextColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
    private float hbYOffset = HB_Y_OFFSET_DIST * 5.0f;

    public DarklingsBuddy(float offsetY, boolean green) {
        super(NAME, ID, 20,
                null, -2.0F, 10.0F, 220.0F, 240.0F,
                "DarklingsImgs/buddy/DarklingBuddy.png", -935, offsetY);
        this.green = green;
        this.offsetY = offsetY;

    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
    }

    @Override
    public void takeTurn() {
        if(!hasAttacked){
            moveInfo = makeMoves();
            ChooseAction pickAction = new ChooseAction(new MonsterCard(), target, "Choose your attack");
            this.moveInfo.forEach( move -> {
                pickAction.add(move.getName(), move.getDescription(), move.getAction());
            });
            AbstractDungeon.actionManager.addToBottom(pickAction);
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    //Create possible moves for the monster
    private ArrayList<ChooseActionInfo> makeMoves(){
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        target = AbstractDungeon.getRandomMonster();
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.id.equals(AwakenedOne.ID) || mo.id.equals(Darkling.ID)) {
                target = mo;
            }
        }
        if ((target != null)) {
            tempInfo.add(new ChooseActionInfo("Attack", "owo", () -> {
                DamageInfo info = new DamageInfo(this, 1, DamageInfo.DamageType.NORMAL);
                info.applyPowers(this, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }));
        }

        return tempInfo;
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }

    @Override
    public void renderHealth(final SpriteBatch sb) {
        if (Settings.hideCombatElements) {
            return;
        }
        final float x = this.hb.cX - this.hb.width / 2.0f;
        final float y = this.hb.cY - this.hb.height / 2.0f + this.hbYOffset;
        renderHealthBg(sb, x, y);
        targetHealthBarWidth = this.hb.width * this.currentHealth / this.maxHealth;
        if (this.targetHealthBarWidth != 0.0f) {
            renderOrangeHealthBar(sb, x, y);
            if (this.hasPower("Poison")) {
                renderGreenHealthBar(sb, x, y);
            }
            renderRedHealthBar(sb, x, y);
        }
        if (this.currentBlock != 0 && this.hbAlpha != 0.0f) {
            renderBlockOutline(sb, x, y);
        }
        this.renderHealthText(sb, y);
        if (this.currentBlock != 0 && this.hbAlpha != 0.0f) {
            renderBlockIconAndValue(sb, x, y);
        }
        renderPowerIcons(sb, x, y);
    }

    private void renderHealthText(final SpriteBatch sb, final float y) {
        if (this.targetHealthBarWidth != 0.0f) {
            final float tmp = this.hbTextColor.a;
            final Color hbTextColor = this.hbTextColor;
            hbTextColor.a *= this.healthHideTimer;
            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, Integer.toString(this.currentHealth) + "/" + Integer.toString(this.maxHealth), this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0f * Settings.scale, this.hbTextColor);
            this.hbTextColor.a = tmp;
        }
        else {
            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, TEXT[0], this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y - 1.0f * Settings.scale, this.hbTextColor);
        }
    }

    private void renderPowerIcons(final SpriteBatch sb, final float x, final float y) {
        float offset = 10.0f * Settings.scale;
        for (final AbstractPower p : this.powers) {
            p.renderIcons(sb, x + offset, y - 48.0f * Settings.scale, this.hbTextColor);
            offset += POWER_ICON_PADDING_X;
        }
        offset = 0.0f * Settings.scale;
        for (final AbstractPower p : this.powers) {
            p.renderAmount(sb, x + offset + 32.0f * Settings.scale, y - 66.0f * Settings.scale, this.hbTextColor);
            offset += POWER_ICON_PADDING_X;
        }
    }

    private void renderBlockOutline(final SpriteBatch sb, final float x, final float y) {
        sb.setColor(this.blockOutlineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.BLOCK_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.BLOCK_BAR_R, x + this.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    private void renderBlockIconAndValue(final SpriteBatch sb, final float x, final float y) {
        sb.setColor(this.blockColor);
        sb.draw(ImageMaster.BLOCK_ICON, x + BLOCK_ICON_X - 32.0f, y + BLOCK_ICON_Y - 32.0f + this.blockOffset, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(this.currentBlock), x + BLOCK_ICON_X, y - 16.0f * Settings.scale, this.blockTextColor, this.blockScale);
    }

    private void renderOrangeHealthBar(final SpriteBatch sb, final float x, final float y) {
        sb.setColor(this.orangeHbBarColor);
        if (this.maxHealth == this.currentHealth) {
            this.healthBarWidth = this.targetHealthBarWidth;
        }
        else if (this.currentHealth == 0) {
            this.healthBarWidth = 0.0f;
            this.targetHealthBarWidth = 0.0f;
        }
        if (this.targetHealthBarWidth > this.healthBarWidth) {
            this.healthBarWidth = this.targetHealthBarWidth;
        }
        sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.healthBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.healthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderGreenHealthBar(final SpriteBatch sb, final float x, final float y) {
        sb.setColor(this.greenHbBarColor);
        if (this.currentHealth > 0) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.targetHealthBarWidth, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetHealthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
    }

    private void renderRedHealthBar(final SpriteBatch sb, final float x, final float y) {
        if (this.currentBlock > 0) {
            sb.setColor(this.blueHbBarColor);
        }
        else {
            sb.setColor(this.redHbBarColor);
        }
        if (!this.hasPower("Poison")) {
            if (this.currentHealth > 0) {
                sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            }
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.targetHealthBarWidth, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetHealthBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }
        else {
            int poisonAmt = this.getPower("Poison").amount;
            if (poisonAmt > 0 && this.hasPower("Intangible")) {
                poisonAmt = 1;
            }
            if (this.currentHealth > poisonAmt) {
                float w = 1.0f - (this.currentHealth - poisonAmt) / this.currentHealth;
                w *= this.targetHealthBarWidth;
                if (this.currentHealth > 0) {
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                }
                sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.targetHealthBarWidth - w, HEALTH_BAR_HEIGHT);
                sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetHealthBarWidth - w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            }
        }
    }

    private void renderHealthBg(final SpriteBatch sb, final float x, final float y) {
        sb.setColor(this.hbShadowColor);
        sb.draw(ImageMaster.HB_SHADOW_L, x - HEALTH_BAR_HEIGHT, y - HEALTH_BG_OFFSET_X + 3.0f * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, y - HEALTH_BG_OFFSET_X + 3.0f * Settings.scale, this.hb.width, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + this.hb.width, y - HEALTH_BG_OFFSET_X + 3.0f * Settings.scale, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setColor(this.hbBgColor);
        if (this.currentHealth != this.maxHealth) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.hb.width, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + this.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }
    }

    static {

        BLOCK_OFFSET_DIST = 12.0f * Settings.scale;
        HB_Y_OFFSET_DIST = 12.0f * Settings.scale;
        HEALTH_BAR_HEIGHT = 20.0f * Settings.scale;
        HEALTH_BAR_OFFSET_Y = -28.0f * Settings.scale;
        HEALTH_TEXT_OFFSET_Y = 6.0f * Settings.scale;
        POWER_ICON_PADDING_X = 48.0f * Settings.scale;
        HEALTH_BG_OFFSET_X = 31.0f * Settings.scale;
    }
}
