package onehitdungeon.entities.items;

import onehitdungeon.interfaces.ArmorItem;

public class ArmorItemImpl extends BaseItem implements ArmorItem {
    public ArmorItemImpl(int battlePower, double priceForUpgrade) {
        super(battlePower, priceForUpgrade);
    }
}
