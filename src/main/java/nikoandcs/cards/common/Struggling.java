package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Struggling extends BaseCard {
    public static final String ID = makeID(Struggling.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1 // 消耗 1 能量
    );

    public Struggling() {
        super(ID, info);
        setBlock(8, 3); // 基础 8 点格挡，升级增加 3 点（共 11 点）
        setMagic(1);    // 摸 1 张牌
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 获得格挡
        addToBot(new GainBlockAction(p, p, block));
        // 摸牌
        addToBot(new DrawCardAction(p, magicNumber));
    }
}