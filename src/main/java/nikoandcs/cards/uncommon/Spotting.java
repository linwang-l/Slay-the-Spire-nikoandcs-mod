package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Spotting extends BaseCard {
    public static final String ID = makeID(Spotting.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY, // 目标为全部敌人
            1 // 1 能量
    );

    public Spotting() {
        super(ID, info);
        // 设置易伤层数：基础 3 层，升级增加 2 层（共 5 层）
        setMagic(3, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 遍历当前房间的所有怪物
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                // 施加易伤能力
                addToBot(new ApplyPowerAction(mo, p,
                        new VulnerablePower(mo, magicNumber, false), magicNumber,
                        true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}