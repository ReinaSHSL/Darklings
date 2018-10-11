package DarklingsMod;

import DarklingsMod.character.Darklings;
import DarklingsMod.enums.AbstractCardEnum;
import DarklingsMod.enums.DarklingsEnum;
import DarklingsMod.monsters.DarklingsBuddy;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;


@SpireInitializer
public class DarklingsModInitializer implements EditCharactersSubscriber, OnStartBattleSubscriber{

    private static final String MODNAME = "Darklings!";
    private static final String AUTHOR = "Reina";
    private static final String DESCRIPTION = "Adds Darklings as a playable character.";

    private static final float BUTTON_ENABLE_X = 350.0f;
    private static final float BUTTON_ENABLE_Y = 750.0f;
    private static final Color BLACK = CardHelper.getColor(131.0f, 156.0f, 165.0f);
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
        BaseMod.addCharacter(Darklings.class, "The Blob", "BLOB",
                AbstractCardEnum.DARKLINGS_BLACK, "Darklings",
                Darklings_Button, Darklings_Portrait,
                DarklingsEnum.DARKLING);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof Darklings) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
            player.addMinion(new DarklingsBuddy(-75, true));
            player.addMinion(new DarklingsBuddy(-130, false));
        }
    }
}
