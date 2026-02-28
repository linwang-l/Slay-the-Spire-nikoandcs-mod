package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Reload extends BaseCard {
    public static final String ID = makeID(Reload.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1 // 1 能量
    );

    public Reload() {
        super(ID, info);
        setMagic(3, 1); // 摸牌数：3 -> 升级后 4
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 执行抽牌动作
        addToBot(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // 抽牌数 +1
            initializeDescription();
        }
    }
}