package nikoandcs.cards.uncommon;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.cards.special.SmartDefault;
import nikoandcs.cards.special.Bet;
import nikoandcs.cards.special.DynamicTactics;
import nikoandcs.cards.special.ABgo;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

import java.util.ArrayList;

public class TemporaryCommand extends BaseCard {
    public static final String ID = makeID(TemporaryCommand.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2 // 初始 2 能量
    );

    public TemporaryCommand() {
        super(ID, info);
        MultiCardPreview.add(this,
                new ABgo(),
                new SmartDefault(),
                new Bet(),
                new DynamicTactics()
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 准备随机池
        ArrayList<AbstractCard> pool = new ArrayList<>();
        pool.add(new ABgo());
        pool.add(new SmartDefault());
        pool.add(new Bet());
        pool.add(new DynamicTactics());

        // 2. 随机抽取一张并置入手牌
        AbstractCard c = pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1)).makeCopy();

        // 按照惯例，如果这张牌升级了，生成的牌不强制升级（保持与赌点逻辑一致）
        addToBot(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1); // 升级后变为 1 能量
            initializeDescription();
        }
    }
}