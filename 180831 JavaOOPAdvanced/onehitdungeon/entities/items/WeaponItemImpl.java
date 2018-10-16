package onehitdungeon.entities.items;

import onehitdungeon.interfaces.WeaponItem;

public class WeaponItemImpl extends BaseItem implements WeaponItem {
    public WeaponItemImpl(int battlePower, double priceForUpgrade) {
        super(battlePower, priceForUpgrade);
    }
}
