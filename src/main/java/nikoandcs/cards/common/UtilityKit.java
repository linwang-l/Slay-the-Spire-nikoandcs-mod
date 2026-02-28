package nikoandcs.cards.common;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview; // 需要导入 StSLib
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nikoandcs.cards.BaseCard;
import nikoandcs.cards.uncommon.Flashbang;
import nikoandcs.cards.uncommon.Incendiary;
import nikoandcs.cards.uncommon.Grenade;
import nikoandcs.character.MyCharacter;
import nikoandcs.util.CardStats;

import java.util.ArrayList;

public class UtilityKit extends BaseCard {
    public static final String ID = makeID(UtilityKit.class.getSimpleName());

    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1 // 修改 1：卡牌改为 1 费
    );

    public UtilityKit() {
        super(ID, info);

        // 修改 2：预览三个道具 (使用 StSLib 的 MultiCardPreview)
        // 注意：这需要在你的项目依赖中包含 StSLib
        MultiCardPreview.add(this, new Grenade(), new Flashbang(), new Incendiary());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Grenade());
        list.add(new Flashbang());
        list.add(new Incendiary());

        AbstractCard c = list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1)).makeCopy();

        // 修改 3：让生成的道具变为 0 费
        // setCostForTurn 会将卡牌在本回合的耗能设为 0
        c.setCostForTurn(0);

        if (this.upgraded) {
            c.upgrade();
            // 升级后也要确保它是 0 费（防止 upgrade 方法里改了费用）
            c.setCostForTurn(0);
        }

        addToBot(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // 修改预览中的卡牌为升级版，这样鼠标悬停时看到的就是绿色的 +1 版
            MultiCardPreview.clear(this);
            AbstractCard g = new Grenade(); g.upgrade();
            AbstractCard f = new Flashbang(); f.upgrade();
            AbstractCard i = new Incendiary(); i.upgrade();
            MultiCardPreview.add(this, g, f, i);

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}