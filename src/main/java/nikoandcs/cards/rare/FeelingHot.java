package nikoandcs.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.powers.FeelingHotPower;
import nikoandcs.util.CardStats;

public class FeelingHot extends BaseCard {
    public static final String ID = makeID(FeelingHot.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public FeelingHot() {
        super(ID, info);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 传入 1，代表获得 1 层。如果是升级版想给多层，可以改这里。
        addToBot(new ApplyPowerAction(p, p, new FeelingHotPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1); // 升级后 1 费
            initializeDescription();
        }
    }
}