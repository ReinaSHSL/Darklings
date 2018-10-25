package DarklingsMod;

import DarklingsMod.character.Darklings;
import DarklingsMod.enums.AbstractCardEnum;
import DarklingsMod.enums.DarklingsEnum;
import DarklingsMod.monsters.Anthony;
import DarklingsMod.monsters.Casey;
import DarklingsMod.cards.*;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.RegrowPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;


import java.util.Properties;


@SpireInitializer
public class DarklingsModInitializer implements EditCharactersSubscriber, OnStartBattleSubscriber, EditCardsSubscriber, EditStringsSubscriber{

    private static final String MODNAME = "Darklings";
    private static final String AUTHOR = "Reina";
    private static final String DESCRIPTION = "Adds Darklings as a playable character.";

    private static final float BUTTON_ENABLE_X = 350.0f;
    private static final float BUTTON_ENABLE_Y = 750.0f;
    private static final Color BLACK = CardHelper.getColor(255.0f, 255.0f, 255.0f);
    private static final String ATTACK_BLACK = "DarklingsImgs/cardBG/bg_attack_black.png";
    private static final String SKILL_BLACK = "DarklingsImgs/cardBG/bg_skill_black.png";
    private static final String POWER_BLACK = "DarklingsImgs/cardBG/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK = "DarklingsImgs/cardBG/card_black_orb.png";

    private static final String ATTACK_BLACK_PORTRAIT = "DarklingsImgs/cardBGStronk/bg_attack_black.png";
    private static final String SKILL_BLACK_PORTRAIT = "DarklingsImgs/cardBGStronk/bg_skill_black.png";
    private static final String POWER_BLACK_PORTRAIT = "DarklingsImgs/cardBGStronk/bg_power_black.png";
    private static final String ENERGY_ORB_BLACK_PORTRAIT = "DarklingsImgs/cardBGStronk/card_black_orb.png";
    private static final String Darklings_Portrait = "DarklingsImgs/charstuff/DarklingsBG.jpg";
    private static final String Darklings_Button = "DarklingsImgs/charstuff/DarklingsButton.png";

    public static final Logger logger = LogManager.getLogger(DarklingsModInitializer.class.getName());

    public DarklingsModInitializer() {
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.DARKLINGS_BLACK,
                BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK,
                ATTACK_BLACK, SKILL_BLACK, POWER_BLACK, ENERGY_ORB_BLACK,
                ATTACK_BLACK_PORTRAIT, SKILL_BLACK_PORTRAIT, POWER_BLACK_PORTRAIT,
                ENERGY_ORB_BLACK_PORTRAIT);
    }

    public static void initialize() {
        DarklingsModInitializer mod = new DarklingsModInitializer();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Darklings("the Blobs", DarklingsEnum.DARKLING),
                Darklings_Button,
                Darklings_Portrait,
                DarklingsEnum.DARKLING);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof Darklings) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
            player.addMinion(Darklings.Anthony);
            player.addMinion(Darklings.Casey);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new RegrowPower(AbstractDungeon.player)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player.getMinions().getMonster(Anthony.ID), AbstractDungeon.player,
                    new RegrowPower(player.getMinions().getMonster(Anthony.ID))));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player.getMinions().getMonster(Casey.ID), AbstractDungeon.player,
                    new RegrowPower(player.getMinions().getMonster(Casey.ID))));
        }
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/DarklingsPowerStrings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/DarklingsCardStrings.json");
    }

    @Override
    public void receiveEditCards() {
        // Darkling Cards
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Chomp());
        BaseMod.addCard(new Nip());
        BaseMod.addCard(new Harden());

        // Beyond Ditto Cards
        BaseMod.addCard(new AttackProtocol());
        BaseMod.addCard(new CircleOfPower());
        BaseMod.addCard(new Confusion());
        BaseMod.addCard(new Constrict());
        BaseMod.addCard(new Countdown());
        BaseMod.addCard(new DarkEcho());
        BaseMod.addCard(new Drool());
        BaseMod.addCard(new Explode());
        BaseMod.addCard(new Fading());
        BaseMod.addCard(new Glare());
        BaseMod.addCard(new Intrigible());
        BaseMod.addCard(new Laser());
        BaseMod.addCard(new NomNomNom());
        BaseMod.addCard(new Rebirth());
        BaseMod.addCard(new Repulse());
        BaseMod.addCard(new Reverberate());
        BaseMod.addCard(new Shifting());
        BaseMod.addCard(new SnakeStrike());
        BaseMod.addCard(new Spines());
        BaseMod.addCard(new SquareOfProtection());
        BaseMod.addCard(new SuicideAttack());
        BaseMod.addCard(new TentacleSmash());
        BaseMod.addCard(new TimeWarp());
        BaseMod.addCard(new Triburn());

        // City Ditto Cards
        BaseMod.addCard(new Agonize());
        BaseMod.addCard(new Airborne());
        BaseMod.addCard(new BigStab());
        BaseMod.addCard(new Debilitate());
        BaseMod.addCard(new DefensiveStance());
        BaseMod.addCard(new FlameTackle());
        BaseMod.addCard(new Forge());
        BaseMod.addCard(new GrabEm());
        BaseMod.addCard(new HardenHit());
        BaseMod.addCard(new Hardifact());
        BaseMod.addCard(new Heal());
        BaseMod.addCard(new Hex());
        BaseMod.addCard(new HyperBeam());
        BaseMod.addCard(new MalleableSkin());
        BaseMod.addCard(new MegaDebuff());
        BaseMod.addCard(new Peck());
        BaseMod.addCard(new PointySpecial());
        BaseMod.addCard(new RainBlows());
        BaseMod.addCard(new ShellPlating());
        BaseMod.addCard(new StabbyStab());
        BaseMod.addCard(new Stasis());
        BaseMod.addCard(new StrongGuard());
        BaseMod.addCard(new Suck());
        BaseMod.addCard(new TailWhip());
        BaseMod.addCard(new Whip());

        // Exordium Cards
        BaseMod.addCard(new AcidLick());
        BaseMod.addCard(new AngryClaw());
        BaseMod.addCard(new BeamWave());
        BaseMod.addCard(new Bellow());
        BaseMod.addCard(new Corrosive());
        BaseMod.addCard(new CurlUp());
        BaseMod.addCard(new DarkStrike());
        BaseMod.addCard(new DefensiveMode());
        BaseMod.addCard(new Disintegrate());
        BaseMod.addCard(new Entangle());
        BaseMod.addCard(new EscapeDefense());
        BaseMod.addCard(new GoopSpray());
        BaseMod.addCard(new GreatBellow());
        BaseMod.addCard(new Grow());
        BaseMod.addCard(new GrowFuzz());
        BaseMod.addCard(new Incantation());
        BaseMod.addCard(new Inferno());
        BaseMod.addCard(new Inflame());
        BaseMod.addCard(new Lick());
        BaseMod.addCard(new Mug());
        BaseMod.addCard(new Nap());
        BaseMod.addCard(new Preparing());
        BaseMod.addCard(new Protect());
        BaseMod.addCard(new Puncture());
        BaseMod.addCard(new Rake());
        BaseMod.addCard(new ShellShock());
        BaseMod.addCard(new SkullBash());
        BaseMod.addCard(new Smash());
        BaseMod.addCard(new SporeCloud());
        BaseMod.addCard(new Tackle());
        BaseMod.addCard(new Thrash());
        BaseMod.addCard(new TinyTackle());
        BaseMod.addCard(new TwinSlam());
        BaseMod.addCard(new UltimateBlast());
        BaseMod.addCard(new Wound());
    }
}
