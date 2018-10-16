package onehitdungeon.entities.items;

import onehitdungeon.interfaces.Item;

public abstract class BaseItem implements Item {
    private int battlePower;
    private double priceForUpgrade;

    public BaseItem(int battlePower, double priceForUpgrade) {
        this.battlePower = battlePower;
        this.priceForUpgrade = priceForUpgrade;
    }

    @Override
    public Integer getBattlePower() {
        return this.battlePower;
    }

    @Override
    public Double getPriceForUpgrade() {
        return this.priceForUpgrade;
    }
}
