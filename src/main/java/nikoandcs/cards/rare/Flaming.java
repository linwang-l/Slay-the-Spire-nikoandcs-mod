package nikoandcs.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.actions.DoubleStatsAction;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Flaming extends BaseCard {
    public static final String ID = makeID(Flaming.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE, // 金卡
            CardTarget.SELF,
            2 // 2 能量
    );

    public Flaming() {
        super(ID, info);
        this.exhaust = true; // 基础版带 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 调用翻倍动作
        addToBot(new DoubleStatsAction());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false; // 升级后去掉消耗
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
