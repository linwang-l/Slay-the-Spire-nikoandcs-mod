package nikoandcs.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DoubleStatsAction extends AbstractGameAction {
    private AbstractPlayer p;

    public DoubleStatsAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.POWER;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        // 1. 处理力量翻倍
        AbstractPower str = p.getPower(StrengthPower.POWER_ID);
        if (str != null && str.amount > 0) {
            // 如果有力量且为正数，则再加一份等额的力量，实现翻倍
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, str.amount), str.amount));
        }

        // 2. 处理敏捷翻倍
        AbstractPower dex = p.getPower(DexterityPower.POWER_ID);
        if (dex != null && dex.amount > 0) {
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, dex.amount), dex.amount));
        }

        this.isDone = true;
    }
}