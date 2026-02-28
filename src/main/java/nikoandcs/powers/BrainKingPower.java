package nikoandcs.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import nikoandcs.actions.BrainKingAction; // 引入刚才写的 Action

public class BrainKingPower extends AbstractPower {
    public static final String POWER_ID = "nikoandcs:BrainKingPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public BrainKingPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.type = PowerType.BUFF;
        this.loadRegion("ai");
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            // 根据能力层数，添加对应次数的选择动作
            for (int i = 0; i < this.amount; i++) {
                addToBot(new BrainKingAction());
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + " 次" + powerStrings.DESCRIPTIONS[2];
        } else {
            this.description = powerStrings.DESCRIPTIONS[0] + "从四张战术牌中选择 1 张" + powerStrings.DESCRIPTIONS[1];
        }
    }
}