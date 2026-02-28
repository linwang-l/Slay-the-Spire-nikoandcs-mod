package nikoandcs.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower; // 借用观者的下回合死亡代码
import nikoandcs.cards.BaseCard;
import nikoandcs.powers.ZeroCostPower; // 你之前写的那个扫描手牌变0费的能力
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class ButLookAt extends BaseCard {
    public static final String ID = makeID(ButLookAt.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            3 // 3 能量
    );

    public ButLookAt() {
        super(ID, info);
        setMagic(4, 3);      // 抽牌数：基础 4，升级增加 3（共 7 张）
        setCustomVar("str", 6); // 力量固定 6
        this.isEthereal = true; // 虚无
        this.exhaust = true;    // 消耗
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 1. 获得 6 点力量
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, customVar("str")), customVar("str")));

        // 2. 赋予“绝地反击”能力（所有牌变为 0 费，且持续扫描新加入的牌）
        addToBot(new ApplyPowerAction(p, p, new ZeroCostPower(p)));

        // 3. 参考 Battle Trance：抽牌
        addToBot(new DrawCardAction(p, magicNumber));

        // 4. 参考 Battle Trance：本回合无法再抽牌
        addToBot(new ApplyPowerAction(p, p, new NoDrawPower(p)));

        // 5. 下回合死亡（使用游戏自带的 EndTurnDeathPower）
        addToBot(new ApplyPowerAction(p, p, new EndTurnDeathPower(p)));
    }
}