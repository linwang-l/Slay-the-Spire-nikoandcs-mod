package nikoandcs.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class BodyFlickPower extends AbstractPower {
    public static final String POWER_ID = "nikoandcs:BodyFlickPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public BodyFlickPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount; // 每层力量/敏捷提供的格挡值
        this.type = PowerType.BUFF;
        // 使用“残影”或“敏捷”相关的图标
        this.loadRegion("blur");
        this.updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        // 如果是玩家自己获得了力量或敏捷
        if (target == this.owner && (power.ID.equals(StrengthPower.POWER_ID) || power.ID.equals(DexterityPower.POWER_ID))) {
            // 注意：如果一张牌同时给力量和敏捷，这里会触发两次
            this.flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
}