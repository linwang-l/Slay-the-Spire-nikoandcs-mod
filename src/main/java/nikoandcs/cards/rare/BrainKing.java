package nikoandcs.cards.rare;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.cards.special.ABgo;
import nikoandcs.cards.special.Bet;
import nikoandcs.cards.special.DynamicTactics;
import nikoandcs.cards.special.SmartDefault;
import nikoandcs.character.MyCharacter;
import nikoandcs.powers.BrainKingPower;
import nikoandcs.util.CardStats;

public class BrainKing extends BaseCard {
    public static final String ID = makeID(BrainKing.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3 // 基础 3 费
    );

    public BrainKing() {
        super(ID, info);
        // 建议在构造函数里加一个预览，虽然池子有4张，预览一张最有代表性的
        MultiCardPreview.add(this,
                new ABgo(),
                new SmartDefault(),
                new Bet(),
                new DynamicTactics()
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 施加 1 层能力
        addToBot(new ApplyPowerAction(p, p, new BrainKingPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2); // 升级后 2 费
            initializeDescription();
        }
    }
}