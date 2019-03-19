package com.zakrodionov.protovary.app.ui.product.pager

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.argument
import com.zakrodionov.protovary.app.ext.instanceOf
import com.zakrodionov.protovary.app.ext.parseHtml
import com.zakrodionov.protovary.app.ui.product.pager.DescriptionFragment.DescriptionType.*
import com.zakrodionov.protovary.domain.entity.Product
import kotlinx.android.synthetic.main.view_description.view.*
import java.io.Serializable


class DescriptionFragment : Fragment() {

    val model: Model by argument("model")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_description, container, false)

        when (model.type) {
            DESCRIPTION -> view.tvDescription.text = getDescription(model)
            PROPERTIES -> view.tvDescription.text = getProperties(model)
            INDICATORS -> view.tvDescription.text = getIndicators(model)
            MANUFACTURER -> view.tvDescription.text = getManufacturer(model)
        }

        return view
    }

    //region Конструткторы текстовки
    private fun getManufacturer(model: Model) = model.product.producer?.parseHtml()

    private fun getIndicators(model: Model): SpannableStringBuilder {
        val text = SpannableStringBuilder()

        model.product.indicators?.forEach {
            text.bold { append(it?.name ?: "н/д") }
                .append("\n")
                .append(it?.value ?: "н/д")
                .append("\n")
                .append("\n")
        }

        return text
    }

    private fun getProperties(model: Model): SpannableStringBuilder {
        val text = SpannableStringBuilder()

        model.product.properties?.forEach {
            text.bold { append(it?.name ?: "н/д") }
                .append("\n")
                .append(it?.value?.parseHtml() ?: "н/д")
                .append("\n")
                .append("\n")
        }

        return text
    }

    private fun getDescription(model: Model): SpannableStringBuilder {
        val text = SpannableStringBuilder()
        var pros = SpannableStringBuilder()

        if (!model.product.pros.isNullOrEmpty()) {
            val pros_ = StringBuilder("")

            pros_.append("Достоинства")
                .append("\n")

            model.product.pros.forEach {
                pros_.append("\u25CF $it")
                    .append("\n")
            }

            pros.append("\n")

            pros = SpannableStringBuilder(pros_).apply {
                setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.blue_light)),
                    0,
                    pros_.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

        }

        var cons = SpannableStringBuilder()

        if (!model.product.cons.isNullOrEmpty()) {
            val cons_ = StringBuilder("")

            cons_.append("Недостатки")
                .append("\n")

            model.product.cons.forEach {
                cons_.append("\u25CF $it ")
                    .append("\n")
            }

            cons = SpannableStringBuilder(cons_).apply {
                setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.red_light)),
                    0,
                    cons_.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            cons.append("\n")

        }


        text.append(pros)
            .append(cons)
            .append("\n")
            .append(model.product.researchResults?.parseHtml())

        return text
    }

    //endregion

    data class Model(val product: Product, val type: DescriptionType) : Serializable

    enum class DescriptionType {
        DESCRIPTION,
        PROPERTIES,
        INDICATORS,
        MANUFACTURER
    }

    companion object {
        fun newInstance(model: Model): DescriptionFragment = instanceOf("model" to model)
    }
}