package nikoandcs.cards.special;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class SmartDefault extends BaseCard {
    public static final String ID = makeID(SmartDefault.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK, // 互换：改为攻击
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public SmartDefault() {
        super(ID, info);
        setDamage(10, 4); // 伤害 10 -> 14
        setBlock(10, 4);  // 格挡 10 -> 14
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获得格挡
        addToBot(new GainBlockAction(p, p, block));

        // 2. 造成伤害
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        // 3. 摸牌逻辑：抽 1 张
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeBlock(4);
            initializeDescription();
        }
    }
}