package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class LookingBack extends BaseCard {
    public static final String ID = makeID(LookingBack.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,      // 技能牌
            CardRarity.COMMON,
            CardTarget.NONE,
            1 // 基础 1 能量
    );

    public LookingBack() {
        super(ID, info);
        // 设置选择数量为 1
        setMagic(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 调用原版动作：从弃牌堆选择 X 张牌放入手牌
        addToBot(new BetterDiscardPileToHandAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0); // 升级后变为 0 费
            initializeDescription();
        }
    }
}