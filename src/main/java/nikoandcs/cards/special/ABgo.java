package nikoandcs.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class ABgo extends BaseCard {
    public static final String ID = makeID(ABgo.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public ABgo() {
        super(ID, info);
        this.exhaust = true;   // 消耗
        this.isEthereal = true; // 新增：虚无
        setMagic(4, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获得 1 点能量
        addToBot(new GainEnergyAction(1));

        // 2. 抽牌
        addToBot(new DrawCardAction(p, magicNumber));

        // 3. 临时敏捷降低 4
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -4), -4));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, -4), -4));
    }
}