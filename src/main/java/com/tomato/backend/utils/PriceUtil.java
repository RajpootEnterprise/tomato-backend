package com.tomato.backend.utils;

import com.tomato.backend.entity.CartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Small helper for consistent money math (avoids floating point drift
 * when summing cart / order totals).
 */
public class PriceUtil {

    private PriceUtil() {
    }

    public static double calculateCartTotal(List<CartItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            BigDecimal lineTotal = BigDecimal.valueOf(item.getPrice())
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(lineTotal);
        }
        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
