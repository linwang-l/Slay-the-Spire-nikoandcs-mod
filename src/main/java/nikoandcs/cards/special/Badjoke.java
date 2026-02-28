package nikoandcs.cards.special;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Badjoke extends BaseCard {
    public static final String ID = makeID(Badjoke.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0 // 0 费
    );

    public Badjoke() {
        super(ID, info);
        setMagic(1, 1); // 摸牌数：基础 1，升级后 +1 = 2
        this.exhaust = true; // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 只保留摸牌逻辑
        addToBot(new DrawCardAction(p, magicNumber));
    }

    // 注意：如果你的父类 BaseCard 没有自动处理描述刷新，
    // 建议在这里加上 upgrade() 方法来刷新描述。
}