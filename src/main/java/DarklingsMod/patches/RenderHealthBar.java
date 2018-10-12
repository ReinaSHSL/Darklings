package DarklingsMod.patches;

import DarklingsMod.character.Darklings;
import DarklingsMod.monsters.Anthony;
import DarklingsMod.monsters.Casey;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;

@SpirePatch(
        cls="com.megacrit.cardcrawl.core.AbstractCreature",
        method="renderHealth"
)
public class RenderHealthBar
{
    private static float HEALTH_BAR_HEIGHT = -1;
    private static float HEALTH_BAR_OFFSET_Y = -1;

    @SpireInsertPatch(
            rloc=18,
            localvars={"x", "y"}
    )
    public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y)
    {
        if (HEALTH_BAR_HEIGHT == -1) {
            HEALTH_BAR_HEIGHT = 20.0f * Settings.scale;
            HEALTH_BAR_OFFSET_Y = -28.0f * Settings.scale;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.H)) {
            if (__instance instanceof Darklings && __instance.hbAlpha > 0
                    || __instance instanceof Anthony && __instance.hbAlpha > 0
                    || __instance instanceof Casey && __instance.hbAlpha > 0) {
                renderDarklingHPOutline(__instance, sb, x, y);
            }
        }
    }

    private static void renderDarklingHPOutline(AbstractCreature creature, SpriteBatch sb, float x, float y)
    {
        if (creature instanceof Darklings) {
            sb.setColor(Settings.GOLD_COLOR);
        }
        else if (creature instanceof Anthony) {
            sb.setColor(Settings.GREEN_TEXT_COLOR);
        } else {
            sb.setColor(Settings.BLUE_TEXT_COLOR);
        }

        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        sb.draw(ImageMaster.BLOCK_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);

        sb.draw(ImageMaster.BLOCK_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, creature.hb.width, HEALTH_BAR_HEIGHT);

        sb.draw(ImageMaster.BLOCK_BAR_R, x + creature.hb.width, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }
}
