package DarklingsMod.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonMeshRenderer;

import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;

import java.lang.reflect.*;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

import basemod.abstracts.CustomCard;
import basemod.ReflectionHacks;

import DarklingsMod.enums.AbstractCardEnum;
import DarklingsMod.DarklingsModInitializer;

public abstract class AbstractDittoCard extends CustomCard {
    public String monsterID;
    public CardStrings cardStrings;

    public int damageUp;
    public int blockUp;
    public int magicNumberUp;
    public int costUp;

    protected TextureAtlas atlas = null;
    protected Skeleton skeleton;
    public AnimationState state;
    protected AnimationStateData stateData;

    public float skeleOffsetX = 0.0F;
    public float skeleOffsetY = 0.0F;
    public float skeleScale = 1.0F;
    public static SkeletonMeshRenderer sr;

    public Color renderTint = Color.WHITE.cpy();

    public AbstractDittoCard(String id, int cost, CardType type, CardTarget target, AbstractCard.CardRarity rarity, String monsterPool) {
        // super("Darklings:"+id, "", "DarklingsImgs/cards/" + id, cost, "", type, AbstractCardEnum.DARKLINGS_BLACK, rarity, target);
        super("Darklings:"+id, "", null, cost, "", type, AbstractCardEnum.DARKLINGS_BLACK, rarity, target);

        if (type == CardType.ATTACK) {
            this.loadCardImage("DarklingsImgs/cards/Attack.png");
        } else if (type == CardType.SKILL) {
            this.loadCardImage("DarklingsImgs/cards/Skill.png");
        } else if (type == CardType.POWER) {
            this.loadCardImage("DarklingsImgs/cards/Power.png");
        }

        this.cardStrings = CardCrawlGame.languagePack.getCardStrings("Darklings:"+id);
        this.originalName = cardStrings.NAME;
        this.name = cardStrings.NAME;
        this.rawDescription = cardStrings.DESCRIPTION;

        this.monsterID = monsterPool;

        initializeTitle();
        initializeDescription();
        UnlockTracker.markCardAsSeen(this.cardID);
    }

    public AbstractDittoCard(String id, int cost, CardType type, CardTarget target, String monsterPool) {
        this(id, cost, type, target, AbstractCard.CardRarity.SPECIAL, monsterPool);
    }

    public AbstractDittoCard(String id, int cost, CardType type, CardTarget target, AbstractCard.CardRarity rarity) {
        this(id, cost, type, target, rarity, "Darklings");
    }

    public void act(AbstractGameAction act) {
        AbstractDungeon.actionManager.addToBottom(act);
    }

    public void actForDarkittyns(AbstractGameAction act) {
        act.source = AbstractDungeon.player;

        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            AbstractGameAction a = (AbstractGameAction)clone(act);
            a.target = m;
            AbstractDungeon.actionManager.addToBottom(a);
        }
    }

    public void actForDarkittyn(AbstractGameAction act, String Darkittyn) {
        act.source = AbstractDungeon.player;

        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            if (Darkittyn == m.name) {
                AbstractGameAction a = (AbstractGameAction)clone(act);
                a.target = m;
                AbstractDungeon.actionManager.addToBottom(a);
            }
        }
    }

    public AbstractFriendlyMonster getDarkittyn(String Darkittyn) {
        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            if (Darkittyn == m.name) {
                return (AbstractFriendlyMonster)m;
            }
        }
        return null;
    }

    public void actForDarkittyn(AbstractGameAction act) {
        String[] d = {"Casey", "Anthony"};
        String target = d[new Random().nextInt(d.length)];

        this.actForDarkittyn(act, target);
    }

    public void actForDarklings(AbstractGameAction act) {
        act.source = AbstractDungeon.player;
        act.target = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(act);

        for (AbstractMonster m : ((AbstractPlayerWithMinions)AbstractDungeon.player).getMinions().monsters) {
            AbstractGameAction a = (AbstractGameAction)clone(act);
            a.target = m;
            AbstractDungeon.actionManager.addToBottom(a);
        }
    }

    private static Object clone(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(clone, field.get(obj));
            }
            return clone;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            
            if (this.cardStrings.UPGRADE_DESCRIPTION != "" && this.cardStrings.UPGRADE_DESCRIPTION != null) {
                this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;   
                initializeDescription();
            }

            if (damageUp != 0)      { upgradeDamage(damageUp); }
            if (blockUp  != 0)      { upgradeBlock(blockUp); }
            if (magicNumberUp != 0) { upgradeMagicNumber(magicNumberUp); }
            if (costUp != 0)        { upgradeBaseCost(costUp); }
        }
    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float useless) {
        this.loadAnimation(atlasUrl, skeletonUrl);
    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl)
    {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.scale / (2.0F*this.skeleScale));
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }

    public void render(SpriteBatch sb) {
        super.render(sb);
        if (this.skeleton != null) {
            renderSkeleton(sb);
        }
    }

    public void renderInLibrary(SpriteBatch sb) {
        super.renderInLibrary(sb);
        if ((SingleCardViewPopup.isViewingUpgrade) && (this.isSeen) && (!this.isLocked)) { return; }
        if (this.skeleton != null) {
            renderSkeleton(sb);
        }
    }

    public void renderSkeleton(SpriteBatch sb) {
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.getRootBone().setRotation(this.angle);
        this.skeleton.getRootBone().setScale((2.0F * this.skeleScale) * (Settings.scale * drawScale));
        this.skeleton.updateWorldTransform();

        this.skeleton.setPosition(
            this.current_x + skeleOffsetX,
            this.current_y + skeleOffsetY);

        sb.end();
        CardCrawlGame.psb.begin();
        AbstractMonster.sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb)
    {
        TextureAtlas.AtlasRegion tmpPortrait = (TextureAtlas.AtlasRegion)ReflectionHacks.getPrivate(this, AbstractCard.class, "portrait");

        float drawX = this.current_x - 125.0F;
        float drawY = this.current_y - 95.0F;
        
        Texture img = null;
        if (this.portraitImg != null) {
          img = this.portraitImg;
        }
        if (!this.isLocked)
        {
          if (tmpPortrait != null)
          {
            drawX = this.current_x - tmpPortrait.packedWidth / 2.0F;
            drawY = this.current_y - tmpPortrait.packedHeight / 2.0F;
            sb.setColor(this.renderTint);
            sb.draw(tmpPortrait, drawX, drawY + 72.0F, tmpPortrait.packedWidth / 2.0F, tmpPortrait.packedHeight / 2.0F - 72.0F, tmpPortrait.packedWidth, tmpPortrait.packedHeight, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);
          }
          else if (img != null)
          {
            sb.setColor(this.renderTint);
            sb.draw(img, drawX, drawY + 72.0F, 125.0F, 23.0F, 250.0F, 190.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 250, 190, false, false);
          }
        }
        else {
          sb.draw(this.portraitImg, drawX, drawY + 72.0F, 125.0F, 23.0F, 250.0F, 190.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 250, 190, false, false);
        }
    }
}