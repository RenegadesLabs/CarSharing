package com.cardee.util.ui.input_strategy


class StrategyFactory {

    companion object {

        fun newPriceStrategy(currencySymbol: String = "$"): InputStrategy {
            return PriceStrategy(currencySymbol)
        }

        fun newNumberStrategy(): InputStrategy {
            return NumberStrategy()
        }
    }

}