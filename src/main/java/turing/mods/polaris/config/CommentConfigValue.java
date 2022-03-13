package turing.mods.polaris.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommentConfigValue implements IConfigValue<String> {
    protected final String name;
    protected final IConfigCategory category;
    protected final String value;

    public CommentConfigValue(IConfigCategory category, String comment) {
        List<CommentConfigValue> comments = Arrays.stream(category.getValues()).filter(v -> (v instanceof CommentConfigValue)).map(v -> ((CommentConfigValue) v)).collect(Collectors.toList());
        this.category = category;
        this.name = comments.size() <= 1 ? "comment" : "comment#" + (comments.size() - 1);
        this.value = comment;
    }

    @Override
    @Nullable
    public String get() {
        return value;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public IConfigCategory getCategory() {
        return category;
    }
}
