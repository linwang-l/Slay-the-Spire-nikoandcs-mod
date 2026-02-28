package nikoandcs.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class FlamingAction extends AbstractGameAction {
    private AbstractPlayer p;

    public FlamingAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST; // 设置一个基础时长
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // 1. 先用一个临时列表保存所有非攻击牌，避免直接遍历手牌组产生的冲突
            ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
            for (AbstractCard c : p.hand.group) {
                if (c.type != AbstractCard.CardType.ATTACK) {
                    cardsToExhaust.add(c);
                }
            }

            // 2. 针对每一张牌，分别添加动作
            for (AbstractCard c : cardsToExhaust) {
                // 使用 addToTop 确保这些动作紧跟在当前 Action 之后立即执行
                // 注意：addToTop 是后进先出，所以添加顺序要注意
                addToTop(new DrawCardAction(p, 1));
                addToTop(new GainEnergyAction(1));
                addToTop(new ExhaustSpecificCardAction(c, p.hand));
            }

            this.isDone = true;
        }
        tickDuration();
    }
}