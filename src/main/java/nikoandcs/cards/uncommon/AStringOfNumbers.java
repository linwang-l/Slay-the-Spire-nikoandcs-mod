package nikoandcs.cards.uncommon;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.cards.special.Badjoke; // 1. 确保导入了 Badjoke 类
import nikoandcs.character.MyCharacter;
import nikoandcs.powers.AStringOfNumbersPower;
import nikoandcs.util.CardStats;

public class AStringOfNumbers extends BaseCard {
    public static final String ID = makeID(AStringOfNumbers.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public AStringOfNumbers() {
        super(ID, info);
        // 2. 在构造函数中设置预览卡牌
        this.cardsToPreview = new Badjoke();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AStringOfNumbersPower(p, 1, upgraded), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // 3. 重点：当这张牌升级时，预览的 Badjoke 也显示为升级版
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}