package nikoandcs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import nikoandcs.cards.BaseCard;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

public class Hide extends BaseCard {
    public static final String ID = makeID(Hide.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Hide() {
        super(ID, info);
        setBlock(21, 7); // 基础 21, 升级 +7 = 28
        setMagic(3, -1); // 虚弱 3 层, 升级 -1 = 2 层
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 增加一个沉重的防御音效，参考 Conclude 的 SFXAction 逻辑
        addToBot(new SFXAction("BLOCK_GAIN_HEAVY"));

        // 1. 获得大量格挡
        addToBot(new GainBlockAction(p, p, block));

        // 2. 给自己施加虚弱 (false 表示是由自己造成的，非怪物的负面状态)
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, magicNumber, false), magicNumber));

        // 3. 参考 Conclude 源代码第 40 行：立即结束回合
        addToBot(new PressEndTurnButtonAction());
    }
}