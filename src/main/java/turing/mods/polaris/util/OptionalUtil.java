package turing.mods.polaris.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OptionalUtil {
    public static Optional<JsonElement> jsonGetAsOptional(JsonObject json, String element) {
        return json.has(element) ? Optional.of(json.get(element)) : Optional.empty();
    }

    @Nullable
    public static <T> T getElseNull(Optional<T> optional) {
        return optional.orElse(null);
    }
}
