package nikoandcs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class DeskSlam extends BaseCard {
    public static final String ID = makeID(DeskSlam.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY, // 修改 1：目标改为全体敌人
            2
    );

    public DeskSlam() {
        super(ID, info);
        setDamage(4);          // 每段 4 伤害
        setMagic(1, 1);        // 易伤层数：1 -> 升级后 2
        this.isMultiDamage = true; // 声明为群体伤害
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 处理多段伤害逻辑
        int hits = upgraded ? 3 : 2; // 基础 2 次，升级后 3 次
        for (int i = 0; i < hits; i++) {
            // 使用 BLUNT_HEAVY 模拟连续砸桌子的震动感
            addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        // 2. 施加群体易伤
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false), magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // 注意：此处不需要 upgradeDamage，因为伤害数值没变，变的是次数
            upgradeMagicNumber(1); // 易伤 1 -> 2
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}