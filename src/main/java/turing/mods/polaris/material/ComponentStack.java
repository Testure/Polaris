package turing.mods.polaris.material;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ComponentStack {
    protected Component component;
    protected int amount;

    public static final ComponentStack EMPTY = new ComponentStack(new Component("",  false, () -> null), 0);

    public ComponentStack(@Nonnull Component component, int amount) {
        this.component = component;
        this.amount = amount;
    }

    public ComponentStack(@Nonnull Component component) {
        this(component, 1);
    }

    public boolean isEmpty() {
        return this.amount > 1 && !Objects.equals(this.component.getChemicalName(), "");
    }

    public int getCount() {
        return this.amount;
    }

    public void setCount(int count) {
        this.amount = Math.min(Math.max(count, 0), 128);
    }

    public Component getComponent() {
        return component;
    }
}
