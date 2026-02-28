package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class AmmoSatiety extends BaseCard {
    public static final String ID = makeID(AmmoSatiety.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    public AmmoSatiety() {
        super(ID, info);
        // 设置选择的数量：基础 2 张，升级后 +1 = 3 张
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 计算抽牌堆中实际有多少张攻击牌
        int attackCount = 0;
        // 修正点：使用 drawPile 而非 draw_pile
        for (AbstractCard c : p.drawPile.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                attackCount++;
            }
        }

        // 2. 取实际数量和 magicNumber 的最小值
        int finalAmount = Math.min(attackCount, this.magicNumber);

        if (finalAmount > 0) {
            addToBot(new AttackFromDeckToHandAction(finalAmount));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        // 检查摸牌堆里是否有攻击牌
        boolean hasAttack = false;
        for (AbstractCard c : p.drawPile.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                hasAttack = true;
                break;
            }
        }

        if (!hasAttack) {
            // 如果没攻击牌，提示玩家（对应 JSON 里的 EXTENDED_DESCRIPTION）
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            canUse = false;
        }
        return canUse;
    }
}