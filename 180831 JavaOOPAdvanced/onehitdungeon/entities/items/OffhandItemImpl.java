package onehitdungeon.entities.items;

import onehitdungeon.interfaces.OffhandItem;

public class OffhandItemImpl extends BaseItem implements OffhandItem {
    public OffhandItemImpl(int battlePower, double priceForUpgrade) {
        super(battlePower, priceForUpgrade);
    }
}
