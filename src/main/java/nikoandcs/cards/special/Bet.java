package nikoandcs.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Bet extends BaseCard {
    public static final String ID = makeID(Bet.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public Bet() {
        super(ID, info);
        setMagic(4, 2); // 敏捷：基础 4，升级后 +2 = 6
        setCustomVar("strLoss", 4);
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获得临时敏捷 (4 -> 升级后 6)
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber), magicNumber));


        // 3. 随机生成结果 (强制生成基础版本，不进行 upgrade 判定)
        AbstractCard result;
        if (AbstractDungeon.cardRandomRng.randomBoolean()) {
            result = new SuccessfulBet();
        } else {
            result = new FailedBet();
        }

        // --- 核心改动：删除了 if(upgraded) { result.upgrade(); } ---

        addToBot(new MakeTempCardInHandAction(result, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2); // 仅增加敏捷
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}