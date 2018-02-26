package com.cardee.util.ui.input_strategy


class StrategyFactory {

    companion object {

        fun newPriceStrategy(decimals: Int = 2, callback: (String) -> Unit = {}): InputStrategy {
            return DecimalStrategy(decimals, callback)
        }

        fun newNumberStrategy(callback: (String) -> Unit = {}): InputStrategy {
            return NumberStrategy(callback)
        }
    }

}