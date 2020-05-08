package com.zakrodionov.protovary.app.ui.product.pager

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.argument
import com.zakrodionov.protovary.app.ext.instanceOf
import com.zakrodionov.protovary.app.ui.product.pager.DescriptionFragment.DescriptionType.*
import com.zakrodionov.protovary.data.entity.CommonNameValueData
import com.zakrodionov.protovary.data.entity.ProductDetail
import kotlinx.android.synthetic.main.fragment_description.view.*
import java.io.Serializable

class DescriptionFragment : Fragment() {

    val model: Model by argument("model")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)

        when (model.type) {
            DESCRIPTION -> view.tvDescription.text = getDescription(model)
            PROPERTIES -> view.tvDescription.text = getProperties(model)
            INDICATORS -> view.tvDescription.text = getIndicators(model)
            MANUFACTURER -> view.tvDescription.text = getManufacturer(model)
        }

        return view
    }

    //region Строим текстовки
    private fun getManufacturer(model: Model): Spanned? {

        if (model.product.producer.isNullOrEmpty() || model.product.producer == "false") {
            return SpannableStringBuilder(getString(R.string.n_a))
        }

        return model.product.producer.parseAsHtml()
    }

    private fun getIndicators(model: Model) = getNameValueStrings(model.product.indicators)

    private fun getProperties(model: Model) = getNameValueStrings(model.product.properties)

    private fun getNameValueStrings(data: List<CommonNameValueData>?): SpannableStringBuilder {
        val text = SpannableStringBuilder()

        if (data.isNullOrEmpty()) {
            return SpannableStringBuilder(getString(R.string.n_a))
        }

        data.forEach {
            text.bold { append(it.name?.parseAsHtml() ?: getString(R.string.n_a)) }
                .append(
                    getString(
                        R.string.name_value_template,
                        it.value?.parseAsHtml() ?: getString(R.string.n_a)
                    )
                )
        }

        return text
    }

    private fun getDescription(model: Model): SpannableStringBuilder {
        val pros = getColoredSpan(model.product.pros, R.string.advantages, R.color.blue_light)
        val cons = getColoredSpan(model.product.cons, R.string.disadvantages, R.color.red_light)
        val text = SpannableStringBuilder()

        text.append(pros)
            .append(cons)
            .append("\n")
            .append(model.product.researchResults?.parseAsHtml())

        return text
    }

    private fun getColoredSpan(
        strings: List<String>?,
        title: Int,
        color: Int
    ): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()

        if (!strings.isNullOrEmpty()) {

            val prosText = strings.joinToString(
                prefix = "${getString(title)}\n",
                separator = ""
            ) { getString(R.string.dot_value_template, it) }

            ssb.append(prosText).apply {
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireActivity(), color)),
                    0,
                    prosText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return ssb
    }
    //endregion

    companion object {
        fun newInstance(model: Model): DescriptionFragment = instanceOf("model" to model)
    }

    data class Model(val product: ProductDetail, val type: DescriptionType) : Serializable

    enum class DescriptionType {
        DESCRIPTION,
        PROPERTIES,
        INDICATORS,
        MANUFACTURER
    }
}
