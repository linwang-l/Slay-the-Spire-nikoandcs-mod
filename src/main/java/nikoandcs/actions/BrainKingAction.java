package nikoandcs.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import nikoandcs.cards.special.ABgo;
import nikoandcs.cards.special.Bet;
import nikoandcs.cards.special.DynamicTactics;
import nikoandcs.cards.special.SmartDefault;

public class BrainKingAction extends AbstractGameAction {
    private boolean retrieveCard = false;

    public BrainKingAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // 1. 创建一个临时的卡组用于显示
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToBottom(new ABgo());
            group.addToBottom(new SmartDefault());
            group.addToBottom(new Bet());
            group.addToBottom(new DynamicTactics());

            // 2. 打开选择界面 (参数: 卡组, 选择数量, 提示文字, 是否可以跳过)
            AbstractDungeon.gridSelectScreen.open(group, 1, "选择一张战术牌", false);
            tickDuration();
            return;
        }

        // 3. 检查玩家是否已经选好了牌
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                // 将选中的牌加入手牌
                addToTop(new MakeTempCardInHandAction(c.makeCopy(), 1));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.isDone = true;
    }
}