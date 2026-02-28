package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class EcoRound extends BaseCard {
    public static final String ID = makeID(EcoRound.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1 // 1 能量消耗
    );

    public EcoRound() {
        super(ID, info);
        // magicNumber 控制抽牌数：基础 1，升级后 +1 = 2
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 下回合开始时获得 2 点能量
        addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, 2), 2));

        // 2. 下回合额外抽牌 (使用 magicNumber)
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber), magicNumber));
    }
}