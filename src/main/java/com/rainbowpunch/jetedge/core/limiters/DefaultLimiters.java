package com.rainbowpunch.jetedge.core.limiters;

import com.rainbowpunch.jetedge.core.PojoAttributes;
import com.rainbowpunch.jetedge.core.limiters.collections.ArrayLimiter;
import com.rainbowpunch.jetedge.core.limiters.collections.ListLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.BooleanLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.ByteLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.CharacterLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.DoubleLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.FloatLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.IntegerLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.LongLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.ShortLimiter;
import com.rainbowpunch.jetedge.core.limiters.primitive.StringLimiter;
import com.rainbowpunch.jetedge.core.reflection.ClassAttributes;

import java.util.List;

public class DefaultLimiters {
    private static final Limiter<Integer> INTEGER_LIMITER = new IntegerLimiter();
    private static final Limiter<Short> SHORT_LIMITER = new ShortLimiter();
    private static final Limiter<Long> LONG_LIMITER = new LongLimiter();
    private static final Limiter<Boolean> BOOLEAN_LIMITER = new BooleanLimiter();
    private static final Limiter<Float> FLOAT_LIMITER = new FloatLimiter();
    private static final Limiter<Double> DOUBLE_LIMITER = new DoubleLimiter();
    private static final Limiter<Character> CHARACTER_LIMITER = new CharacterLimiter();
    private static final Limiter<String> STRING_LIMITER = new StringLimiter();
    private static final Limiter<Byte> BYTE_LIMITER = new ByteLimiter();

    @SuppressWarnings("unchecked")
    public static Limiter<?> getDefaultLimiter(ClassAttributes classAttributes, PojoAttributes pojoAttributes) {

        if (classAttributes.isSubclassOf(List.class) || classAttributes.isArray()) {
            Limiter limiter = getDefaultLimiter(classAttributes.getElementType().orElseThrow(RuntimeException::new), pojoAttributes);

            if (classAttributes.isSubclassOf(List.class)) return ListLimiter.createListLimiter(limiter);
            else if (classAttributes.isArray()) return ArrayLimiter.createArrayLimiter(limiter);
        }

        else if (classAttributes.is(Integer.class)) return INTEGER_LIMITER;
        else if (classAttributes.is(Short.class)) return SHORT_LIMITER;
        else if (classAttributes.is(Long.class)) return LONG_LIMITER;
        else if (classAttributes.is(Boolean.class)) return BOOLEAN_LIMITER;
        else if (classAttributes.is(Float.class)) return FLOAT_LIMITER;
        else if (classAttributes.is(Double.class)) return DOUBLE_LIMITER;
        else if (classAttributes.is(Character.class)) return CHARACTER_LIMITER;
        else if (classAttributes.is(Byte.class)) return BYTE_LIMITER;
        else if (classAttributes.is(String.class)) return STRING_LIMITER;
        else if (classAttributes.isEnum()) return EnumLimiter.createEnumLimiter(classAttributes.getClazz());

        return new DefaultPojoLimiter<>(classAttributes.getClazz(), pojoAttributes);
    }
}