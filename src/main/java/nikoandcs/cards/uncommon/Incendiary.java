package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Incendiary extends BaseCard {
    public static final String ID = makeID(Incendiary.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON, // 提升稀有度为罕见
            CardTarget.ENEMY,
            1
    );

    public Incendiary() {
        super(ID, info);
        setMagic(9, 4);      // 基础 9 层中毒，升级增加 4 层（共 13 层）
        this.exhaust = true; // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 施加中毒，并触发火焰视觉效果
        addToBot(new ApplyPowerAction(
                (AbstractCreature)m,
                (AbstractCreature)p,
                new PoisonPower(m, p, this.magicNumber),
                this.magicNumber,
                AbstractGameAction.AttackEffect.FIRE // 火焰效果
        ));
    }
}